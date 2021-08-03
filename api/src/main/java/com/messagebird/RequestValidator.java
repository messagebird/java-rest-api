package com.messagebird;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier.BaseVerification;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Clock;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.messagebird.exceptions.RequestValidationException;

/**
 * RequestValidator validates webhook signature signed by MessageBird services.
 */
public class RequestValidator {

    public static final String SIGNATURE_HEADER = "MessageBird-Signature-JWT";

    private static final String ALGORITHM_SHA256 = "SHA-256";

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    private final String signatureKey;

    /**
     * RequestValidator validates webhook signature with a customer signature key.
     * 
     * @param signatureKey customer signature key
     */
    public RequestValidator(String signatureKey) {
        this.signatureKey = signatureKey;
    }

    public DecodedJWT validateSignature(Clock clock, String signature, String url, byte[] requestBody)
            throws RequestValidationException {
        DecodedJWT jwt = JWT.decode(signature);

        Algorithm algorithm;
        switch (jwt.getAlgorithm()) {
            case "HS256":
                algorithm = Algorithm.HMAC256(this.signatureKey);
                break;
            case "HS384":
                algorithm = Algorithm.HMAC384(this.signatureKey);
                break;
            case "HS512":
                algorithm = Algorithm.HMAC512(this.signatureKey);
                break;
            default:
                throw new RequestValidationException("The signing method is invalid.");
        }

        BaseVerification builder = (BaseVerification) JWT.require(algorithm)
                .withIssuer("MessageBird")
                .ignoreIssuedAt()
                .acceptLeeway(1)
                .withClaim("url_hash", calculateSha256(url.getBytes()));

        boolean payloadHashClaimExist = !jwt.getClaim("payload_hash").isNull();

        if (requestBody != null && requestBody.length > 0) {
            if (!payloadHashClaimExist) {
                throw new RequestValidationException("The Claim 'payload_hash' is not set but payload is present.");
            }
            builder.withClaim("payload_hash", calculateSha256(requestBody));
        } else if (payloadHashClaimExist) {
            throw new RequestValidationException("The Claim 'payload_hash' is set but actual payload is missing.");
        }

        JWTVerifier verifier;
        if (clock == null) {
            verifier = builder.build();
        } else {
            verifier = builder.build(clock);
        }

        try {
            return verifier.verify(jwt);
        } catch (SignatureVerificationException e) {
            throw new RequestValidationException("Signature is invalid.", e);
        } catch (JWTVerificationException e) {
            throw new RequestValidationException(e.getMessage(), e.getCause());
        }
    }

    public DecodedJWT validateSignature(String signature, String url, byte[] requestBody)
            throws RequestValidationException {
        return validateSignature(null, signature, url, requestBody);
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