package com.messagebird;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier.BaseVerification;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Clock;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.messagebird.exceptions.RequestValidationException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * RequestValidator validates request signature signed by MessageBird services.
 *
 * @see <a href="https://developers.messagebird.com/docs/verify-http-requests">Verify HTTP Requests</a>
 */
public class RequestValidator {

    /**
     * Signature of signed request is set with header name 'MessageBird-Signature-JWT'
     */
    public static final String SIGNATURE_HEADER = "MessageBird-Signature-JWT";
    private static final String ALGORITHM_SHA256 = "SHA-256";
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    private final Algorithm HMAC256, HMAC384, HMAC512;

    /**
     * This field instructs Validator to not validate url_hash claim.
     * It is recommended to not skip URL validation to ensure high security.
     * but the ability to skip URL validation is necessary in some cases, e.g.
     * your service is behind proxy or when you want to validate it yourself.
     * Note that when true, no query parameters should be trusted.
     * Defaults to false.
     */
    private final boolean skipURLValidation;

    /**
     * RequestValidator validates request signature with a customer signature key.
     *
     * @param signatureKey customer signature key. Can be retrieved through
     *                     <a href="https://dashboard.messagebird.com/developers/settings">Developer Settings</a>.
     *                     This is NOT your API key.
     * @see <a href="https://developers.messagebird.com/docs/verify-http-requests">Verify HTTP Requests</a>
     */
    public RequestValidator(String signatureKey) {
        this(signatureKey, false);
    }

    /**
     * RequestValidator validates webhook signature with a customer signature key.
     *
     * @param signatureKey      customer signature key. Can be retrieved through
     *                          <a href="https://dashboard.messagebird.com/developers/settings">Developer Settings</a>.
     *                          This is NOT your API key.
     * @param skipURLValidation whether url_hash claim validation should be skipped.
     *                          Note that when true, no query parameters should be trusted.
     * @see <a href="https://developers.messagebird.com/docs/verify-http-requests">Verify HTTP Requests</a>
     */
    public RequestValidator(String signatureKey, boolean skipURLValidation) {
        this.HMAC256 = Algorithm.HMAC256(signatureKey);
        this.HMAC384 = Algorithm.HMAC384(signatureKey);
        this.HMAC512 = Algorithm.HMAC512(signatureKey);
        this.skipURLValidation = skipURLValidation;
    }

    /**
     * Returns raw signature payload after validating a signature successfully,
     * otherwise throws {@code RequestValidationException}.
     * <p>
     * This JWT is signed with a MessageBird account unique secret key, ensuring the request is from MessageBird and
     * a specific account.
     * The JWT contains the following claims:
     * </p>
     * <ul>
     *   <li>"url_hash" - the raw URL hashed with SHA256 ensuring the URL wasn't altered.</li>
     *   <li> "payload_hash" - the raw payload hashed with SHA256 ensuring the payload wasn't altered.</li>
     *   <li> "jti" - a unique token ID to implement an optional non-replay check (NOT validated by default).</li>
     *   <li> "nbf" - the not before timestamp.</li>
     *   <li> "exp" - the expiration timestamp is ensuring that a request isn't captured and used at a later time.</li>
     *   <li> "iss" - the issuer name, always MessageBird.</li>
     * </ul>
     *
     * @param clock       custom {@link Clock} instance to validate timestamp claims.
     * @param signature   the actual signature.
     * @param url         the raw url including the protocol, hostname and query string,
     *                    {@code https://example.com/?example=42}.
     * @param requestBody the raw request body.
     * @return raw signature payload as {@link DecodedJWT} object.
     * @throws RequestValidationException when the signature is invalid.
     * @see <a href="https://developers.messagebird.com/docs/verify-http-requests">Verify HTTP Requests</a>
     */
    public DecodedJWT validateSignature(Clock clock, String signature, String url, byte[] requestBody)
            throws RequestValidationException {
        if (signature == null || signature.length() == 0)
            throw new RequestValidationException("The signature can not be empty.");

        if (!skipURLValidation && (url == null || url.length() == 0))
            throw new RequestValidationException("The url can not be empty.");

        DecodedJWT jwt = JWT.decode(signature);

        Algorithm algorithm;
        switch (jwt.getAlgorithm()) {
            case "HS256":
                algorithm = HMAC256;
                break;
            case "HS384":
                algorithm = HMAC384;
                break;
            case "HS512":
                algorithm = HMAC512;
                break;
            default:
                throw new RequestValidationException(String.format("The signing method '%s' is invalid.", jwt.getAlgorithm()));
        }

        BaseVerification builder = (BaseVerification) JWT.require(algorithm)
                .withIssuer("MessageBird")
                .ignoreIssuedAt()
                .acceptLeeway(1);

        if (!skipURLValidation)
            builder.withClaim("url_hash", calculateSha256(url.getBytes()));

        boolean payloadHashClaimExist = !jwt.getClaim("payload_hash").isNull();
        if (requestBody != null && requestBody.length > 0) {
            if (!payloadHashClaimExist) {
                throw new RequestValidationException("The Claim 'payload_hash' is not set but payload is present.");
            }
            builder.withClaim("payload_hash", calculateSha256(requestBody));
        } else if (payloadHashClaimExist) {
            throw new RequestValidationException("The Claim 'payload_hash' is set but actual payload is missing.");
        }

        JWTVerifier verifier = clock == null ? builder.build() : builder.build(clock);

        try {
            return verifier.verify(jwt);
        } catch (SignatureVerificationException e) {
            throw new RequestValidationException("Signature is invalid.", e);
        } catch (JWTVerificationException e) {
            throw new RequestValidationException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Returns raw signature payload after validating a signature successfully,
     * otherwise throws {@code RequestValidationException}.
     *
     * @param signature   the actual signature.
     * @param url         the raw url including the protocol, hostname and query string,
     *                    {@code https://example.com/?example=42}.
     * @param requestBody the raw request body.
     * @return raw signature payload as {@link DecodedJWT} object.
     * @throws RequestValidationException when the signature is invalid.
     * @see RequestValidator#validateSignature(Clock, String, String, byte[])
     */
    public DecodedJWT validateSignature(String signature, String url, byte[] requestBody)
            throws RequestValidationException {
        return validateSignature(null, signature, url, requestBody);
    }

    /**
     * Validates request signature with URL validation disabled.
     * Note that no query parameters should be trusted and this only works if {@code RequestValidator} is constructed
     * with {@code skipURLValidation} set to true.
     *
     * @param signature   the actual signature.
     * @param requestBody the raw request body.
     * @return raw signature payload as {@link DecodedJWT} object.
     * @throws RequestValidationException when the signature is invalid.
     * @see RequestValidator#validateSignature(String, String, byte[])
     */
    public DecodedJWT validateSignature(String signature, byte[] requestBody)
            throws RequestValidationException {
        return validateSignature(null, signature, null, requestBody);
    }

    private static String calculateSha256(byte[] bytes) {
        try {
            return encodeHex(MessageDigest.getInstance(ALGORITHM_SHA256).digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RequestValidationException(e);
        }
    }

    private static String encodeHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = HEX_DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = HEX_DIGITS[0x0F & data[i]];
        }
        return new String(out);
    }
}
