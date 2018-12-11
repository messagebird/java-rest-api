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
    public void testIsMatch() {
        testIsMatchEmptyQueryParamsAndEmptyData();
        testIsMatchWithData();
        testIsMatchWithQueryParams();
        testIsMatchWithDataAndQueryParams();
        testIsNotMatch();
    }

    private void testIsMatchEmptyQueryParamsAndEmptyData() {
        RequestSigner requestSigner = new RequestSigner(getBytes("secret"));
        String expectedSignature = "LISw4Je7n0/MkYDgVSzTJm8dW6BkytKTXMZZk1IElMs=";
        Request request = new Request("1544544948", "", getBytes(""));

        assertTrue(requestSigner.isMatch(expectedSignature, request));
    }

    private void testIsMatchWithData() {
        RequestSigner requestSigner = new RequestSigner(getBytes("secret"));
        String expectedSignature = "p2e20OtAg39DEmz1ORHpjQ556U4o1ZaH4NWbM9Q8Qjk=";
        Request request = new Request("1544544948", "", getBytes("{\"a key\":\"some value\"}"));

        assertTrue(requestSigner.isMatch(expectedSignature, request));
    }

    private void testIsMatchWithQueryParams() {
        RequestSigner requestSigner = new RequestSigner(getBytes("secret"));
        String expectedSignature = "Tfn+nRUBsn6lQgf6IpxBMS1j9lm7XsGjt5xh47M3jCk=";
        Request request = new Request("1544544948", "abc=foo&def=bar", getBytes(""));

        assertTrue(requestSigner.isMatch(expectedSignature, request));
    }

    private void testIsMatchWithDataAndQueryParams() {
        RequestSigner requestSigner = new RequestSigner(getBytes("other-secret"));
        String expectedSignature = "orb0adPhRCYND1WCAvPBr+qjm4STGtyvNDIDNBZ4Ir4=";
        Request request = new Request("1544544948", "abc=foo&def=bar", getBytes("{\"a key\":\"some value\"}"));

        assertTrue(requestSigner.isMatch(expectedSignature, request));
    }

    private void testIsNotMatch() {
        RequestSigner requestSigner = new RequestSigner(getBytes("secret"));
        String expectedSignature = "";
        Request request = new Request("1544544948", "abc=foo&def=bar", getBytes("{\"a key\":\"some value\"}"));

        assertFalse(requestSigner.isMatch(expectedSignature, request));
    }
}