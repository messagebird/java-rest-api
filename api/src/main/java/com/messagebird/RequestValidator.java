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
 * RequestValidator
 */
public class RequestValidator {

    public static final String SIGNATURE_HEADER = "MessageBird-Signature-JWT";
    private static final String ALGORITHM_SHA256 = "SHA-256";
    public static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    private String signatureKey;

    public RequestValidator(String signatureKey) {
        this.signatureKey = signatureKey;
    }

    DecodedJWT validateSignature(Clock clock, String signature, String url, byte[] requestBody)
            throws RequestValidationException {
        Algorithm algorithmHS = Algorithm.HMAC256(this.signatureKey);
        DecodedJWT jwt = JWT.decode(signature);
        BaseVerification builder = (BaseVerification) JWT.require(algorithmHS).withIssuer("MessageBird").acceptLeeway(1)
                .withClaim("url_hash", calculateSha256(url.getBytes()));

        if (requestBody != null && requestBody.length > 0) {
            builder.withClaim("payload_hash", calculateSha256(requestBody));
        } else if (!jwt.getClaim("payload_hash").isNull()) {
            throw new RequestValidationException("The Claim 'payload_hash' was set but no payload value.");
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
            throw new RequestValidationException(e.getMessage());
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