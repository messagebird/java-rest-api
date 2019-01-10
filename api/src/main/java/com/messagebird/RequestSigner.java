package com.messagebird;

import com.messagebird.exceptions.RequestSigningException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * RequestSigner is used to verify HTTP requests and is an implementation of:
 * https://developers.messagebird.com/docs/verify-http-requests. Retrieve your
 * signing key at https://dashboard.messagebird.com/developers/settings.
 */
public class RequestSigner {

    private static final String ALGORITHM_SHA256 = "SHA-256";
    private static final String ALGORITHM_HMAC_SHA256 = "HmacSHA256";
    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private SecretKeySpec secret;

    /**
     * Constructs a new RequestSigner instance.
     *
     * @param key Signing key. Can be retrieved through
     *            https://dashboard.messagebird.com/developers/settings. This
     *            is NOT your API key.
     */
    public RequestSigner(byte[] key) {
        this.secret = new SecretKeySpec(key, ALGORITHM_HMAC_SHA256);
    }

    /**
     * Computes the signature for the provided request and determines whether
     * it matches the expected signature (from the raw MessageBird-Signature header).
     *
     * @param expectedSignature Signature from the MessageBird-Signature
     *                          header in its original base64 encoded state.
     * @param request Request containing the values from the incoming webhook.
     * @return True if the computed signature matches the expected signature.
     */
    public boolean isMatch(String expectedSignature, Request request) {
        try {
            return isMatch(Base64.decode(expectedSignature), request);
        } catch (IOException e) {
            throw new RequestSigningException(e);
        }
    }

    /**
     * Computes the signature for the provided request and determines whether
     * it matches the expected signature
     *
     * @param expectedSignature Decoded (with base64) signature
     *                          from the MessageBird-Signature header
     * @param request Request containing the values from the incoming webhook.
     * @return True if the computed signature matches the expected signature.
     */
    public boolean isMatch(byte[] expectedSignature, Request request) {
        return Arrays.equals(computeSignature(request), expectedSignature);
    }

    /**
     * Computes the signature for a request instance.
     *
     * @param request Request to compute signature for.
     * @return HMAC-SHA2556 signature for the provided request.
     */
    private byte[] computeSignature(Request request) {
        String timestampAndQuery = request.getTimestamp() + '\n' +
                request.getSortedQueryParameters() + '\n';

        byte[] timestampAndQueryBytes = timestampAndQuery.getBytes(CHARSET_UTF8);
        byte[] bodyHashBytes = getSha256Hash(request.getData());

        return getHmacSha256Signature(appendArrays(timestampAndQueryBytes, bodyHashBytes));
    }

    private byte[] getSha256Hash(byte[] bytes) {
        try {
            return MessageDigest.getInstance(ALGORITHM_SHA256).digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RequestSigningException(e);
        }
    }

    /**
     * Stitches the two arrays together and returns a new one.
     *
     * @param first Start of the new array.
     * @param second End of the new array.
     * @return New array based on first and second.
     */
    private byte[] appendArrays(byte[] first, byte[] second) {
        byte[] result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }

    private byte[] getHmacSha256Signature(byte[] bytes) {
        try {
            Mac mac = Mac.getInstance(ALGORITHM_HMAC_SHA256);
            mac.init(secret);

            return mac.doFinal(bytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RequestSigningException(e);
        }
    }
}
