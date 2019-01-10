package com.messagebird;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class RequestSignerTest {

    /**
     * Helper to get the bytes the provided UTF-8 encoded string represents.
     */
    private static byte[] getBytes(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    @Test
    public void testIsMatchEmptyQueryParamsAndEmptyData() {
        RequestSigner requestSigner = new RequestSigner(getBytes("secret"));
        String expectedSignature = "LISw4Je7n0/MkYDgVSzTJm8dW6BkytKTXMZZk1IElMs=";
        Request request = new Request("1544544948", "", getBytes(""));

        assertTrue(requestSigner.isMatch(expectedSignature, request));
    }

    @Test
    public void testIsMatchWithData() {
        RequestSigner requestSigner = new RequestSigner(getBytes("secret"));
        String expectedSignature = "p2e20OtAg39DEmz1ORHpjQ556U4o1ZaH4NWbM9Q8Qjk=";
        Request request = new Request("1544544948", "", getBytes("{\"a key\":\"some value\"}"));

        assertTrue(requestSigner.isMatch(expectedSignature, request));
    }

    @Test
    public void testIsMatchWithQueryParams() {
        RequestSigner requestSigner = new RequestSigner(getBytes("secret"));
        String expectedSignature = "Tfn+nRUBsn6lQgf6IpxBMS1j9lm7XsGjt5xh47M3jCk=";
        Request request = new Request("1544544948", "abc=foo&def=bar", getBytes(""));

        assertTrue(requestSigner.isMatch(expectedSignature, request));
    }

    @Test
    public void testIsMatchWithShuffledQueryParams() {
        RequestSigner requestSigner = new RequestSigner(getBytes("secret"));
        String expectedSignature = "Tfn+nRUBsn6lQgf6IpxBMS1j9lm7XsGjt5xh47M3jCk=";
        Request request = new Request("1544544948", "def=bar&abc=foo", getBytes(""));

        assertTrue(requestSigner.isMatch(expectedSignature, request));
    }

    @Test
    public void testIsMatchWithDataAndQueryParams() {
        RequestSigner requestSigner = new RequestSigner(getBytes("other-secret"));
        String expectedSignature = "orb0adPhRCYND1WCAvPBr+qjm4STGtyvNDIDNBZ4Ir4=";
        Request request = new Request("1544544948", "abc=foo&def=bar", getBytes("{\"a key\":\"some value\"}"));

        assertTrue(requestSigner.isMatch(expectedSignature, request));
    }

    @Test
    public void testIsNotMatch() {
        RequestSigner requestSigner = new RequestSigner(getBytes("secret"));
        String expectedSignature = "";
        Request request = new Request("1544544948", "abc=foo&def=bar", getBytes("{\"a key\":\"some value\"}"));

        assertFalse(requestSigner.isMatch(expectedSignature, request));
    }

    @Test
    public void testWithRealSignature() {
        /*
         * Here we use real signature from MessageBird webhook call
         */

        RequestSigner requestSigner = new RequestSigner(getBytes("Wb3N9gKeFf8ZoCzlOb5lJSic7bHLUcSu"));
        String requestSignature = "5Jha9Yyhwgc1nTsgJ9WyzeHilsuUumydICdf4LuIZE8=";
        String requestTimestamp = "1547036603";
        String requestParams = "id=57db52e04e2f4001b555f79813a0f503&mccmnc=20409&ported=0&recipient=31667788880&reference=curl&status=delivered&statusDatetime=2019-01-09T12%3A23%3A23%2B00%3A00";
        byte[] requestBody = new byte[0];

        String spoiledSignature = "5Jha9Yyhwgc1nTsgJ9WyzeHilsuUumydICdf4LUIZE8=";
        String spoiledTimestamp = "1547036605";
        String spoiledParams = "id=57db52e04e2f4001b555f79813a0f503&mccmnc=20409&ported=0&recipient=31667788880&reference=curvy&status=delivered&statusDatetime=2019-01-09T12%3A23%3A23%2B00%3A00";
        byte[] spoiledBody = getBytes("get shit spoiled");

        assertTrue(
            "Definitely valid signature is threaten as invalid",
            requestSigner.isMatch(requestSignature, new Request(requestTimestamp, requestParams, requestBody))
        );
        assertFalse(
            "Invalid signature is threaten as invalid",
            requestSigner.isMatch(spoiledSignature, new Request(requestTimestamp, requestParams, requestBody))
        );
        assertFalse(
            "Signature is still valid with replaced timestamp",
            requestSigner.isMatch(requestSignature, new Request(spoiledTimestamp, requestParams, requestBody))
        );
        assertFalse(
            "Signature is still valid with replaced params",
            requestSigner.isMatch(requestSignature, new Request(requestTimestamp, spoiledParams, requestBody))
        );
        assertFalse(
            "Signature is still valid with replaced body",
            requestSigner.isMatch(requestSignature, new Request(requestTimestamp, requestParams, spoiledBody))
        );
    }
}
