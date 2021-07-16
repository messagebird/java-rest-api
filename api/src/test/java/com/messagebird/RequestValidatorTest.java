package com.messagebird;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.time.OffsetDateTime;
import java.util.Date;

import com.auth0.jwt.interfaces.Clock;
import com.messagebird.exceptions.RequestValidationException;

import org.junit.Test;

public class RequestValidatorTest {

    private static final String TEST_SIGNATURE_KEY = "hunter2";

    private static final String TEST_BASE_URL = "https://example.com";

    @Test
    public void testValidWithNoParamsBody() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ3OTIwMCwiZXhwIjoxNjI1NDc5MjYwLCJqdGkiOiI1OWEyNDRkYy1lOWFkLTRlMjMtOTc3OC0zNzFmYWEyMzhmNzIiLCJ1cmxfaGFzaCI6IjBmMTE1ZGIwNjJiN2MwZGQwMzBiMTY4NzhjOTlkZWE1YzM1NGI0OWRjMzdiMzhlYjg4NDYxNzljNzc4M2U5ZDcifQ.SrhlKJ-ES4Dg8BBXKtop3u92Z_k4L4VjHKsyHWpweGE";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "";
        String requestPayload = "";

        runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload);
    }

    @Test
    public void testValidWithParamsAndWithoutBody() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ3OTIwMCwiZXhwIjoxNjI1NDc5MjYwLCJqdGkiOiJjOTQ2YWY3Ny1lMTgyLTRlYWEtYjJmZi0xYTU0NWI1ZTk5MWEiLCJ1cmxfaGFzaCI6IjQxZjA1ZjBkZGQwYTIyYWIyMDlhYzQ2ZjQ3YzQ1NzJkOWNlZmEyNTdlZDc0YjI0MDA0YmFlNzUzZWNlNmMyNjAifQ.wUeGukU50HcPIr8d-zcCpttlGnPE-W57ujVb36AbAYw";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "/path?bar=1&foo=2";
        String requestPayload = "";

        runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload);
    }

    @Test
    public void testValidWithParamsAndBody() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ3OTIwMCwiZXhwIjoxNjI1NDc5MjYwLCJqdGkiOiI5M2U1NTAwNi1hMGU4LTQ1MjYtYTE5MC1mYTVmZjAwZWExMTYiLCJ1cmxfaGFzaCI6IjQxZjA1ZjBkZGQwYTIyYWIyMDlhYzQ2ZjQ3YzQ1NzJkOWNlZmEyNTdlZDc0YjI0MDA0YmFlNzUzZWNlNmMyNjAiLCJwYXlsb2FkX2hhc2giOiJkZmZkNjAyMWJiMmJkNWIwYWY2NzYyOTA4MDllYzNhNTMxOTFkZDgxYzdmNzBhNGIyODY4OGEzNjIxODI5ODZmIn0.K6HyLDRdYgQBKN2tBcu0dOSxsfb_lOLaWby3un4rxIc";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "/path?bar=1&foo=2";
        String requestPayload = "Hello, World!";

        runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload);
    }

    @Test
    public void testInvalidTokenReceivedBeforeIssued() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ4MjgwMCwiZXhwIjoxNjI1NDgyODYwLCJqdGkiOiJmOWY4YzM4Mi0yNDQ5LTQzMTEtYjcyYi0xZGY3MTY4NzkzMWUiLCJ1cmxfaGFzaCI6IjBmMTE1ZGIwNjJiN2MwZGQwMzBiMTY4NzhjOTlkZWE1YzM1NGI0OWRjMzdiMzhlYjg4NDYxNzljNzc4M2U5ZDcifQ._59NNTg0j5YVXCRHgyeJAj8n6rTg1gwTh_I_coe7RDQ";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "";
        String requestPayload = "";

        RequestValidationException e = assertThrows(RequestValidationException.class,
                () -> runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload));
        assertTrue(e.getMessage().contains("The Token can't be used before"));
    }

    @Test
    public void testInvalidTokenReceivedAfterExpired() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ3NTYwMCwiZXhwIjoxNjI1NDc1NjYwLCJqdGkiOiI1ZjAyZjUyMi02MDMwLTQ2YzgtYjVhMy0wMTI0NjQ3OGQ4YmMiLCJ1cmxfaGFzaCI6IjBmMTE1ZGIwNjJiN2MwZGQwMzBiMTY4NzhjOTlkZWE1YzM1NGI0OWRjMzdiMzhlYjg4NDYxNzljNzc4M2U5ZDcifQ.iGUCLsYVQG4iYWe2MkRoLQBBMzq7p_bLy4u0mhC3Jfc";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "";
        String requestPayload = "";

        RequestValidationException e = assertThrows(RequestValidationException.class,
                () -> runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload));
        assertTrue(e.getMessage().contains("The Token has expired"));
    }

    @Test
    public void testInvalidTokenReceivedOnDifferentURL() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ3OTIwMCwiZXhwIjoxNjI1NDc5MjYwLCJqdGkiOiJhNzVjOTA5Ni1lODIzLTQ0MmItODVmMi03ZDNjOWQ5YjcyNmIiLCJ1cmxfaGFzaCI6IjlmZGExZmNkYzc0YjEwMzUzNjhlNWY2NjhmNTdjOTFlOTk0MTJmZjU5Y2YwM2E0NmNlYjk1YWVhNWU2YjU4ZmQifQ.G4lpxrDOxZs75G1vIJ6J1jVbYS19tx2yq-lkIE-oETY";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "";
        String requestPayload = "";

        RequestValidationException e = assertThrows(RequestValidationException.class,
                () -> runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload));
        assertEquals("The Claim 'url_hash' value doesn't match the required one.", e.getMessage());
    }

    @Test
    public void testInvalidPayloadNotMatch() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ3OTIwMCwiZXhwIjoxNjI1NDc5MjYwLCJqdGkiOiIxNDUwMTUzMi05NmYyLTQ2ODQtOTgzMi02OGYwOTUxYWUzNDIiLCJ1cmxfaGFzaCI6IjBmMTE1ZGIwNjJiN2MwZGQwMzBiMTY4NzhjOTlkZWE1YzM1NGI0OWRjMzdiMzhlYjg4NDYxNzljNzc4M2U5ZDciLCJwYXlsb2FkX2hhc2giOiIzMjRjYzA2N2IyNTdlZGEwYmNiZDljOGQ4MTgwNzdhMDlhOTU2OGMwZDRjYTA2MDM4ZGVkOGZhZGRmODEzZmQ2In0.rQqiANogDOMafgg_B6p362PuhInAro9lMm2j_vruBA0";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "";
        String requestPayload = "Hello, World!";

        RequestValidationException e = assertThrows(RequestValidationException.class,
                () -> runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload));
        assertEquals("The Claim 'payload_hash' value doesn't match the required one.", e.getMessage());
    }

    @Test
    public void testInvalidSignatureKey() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ3OTIwMCwiZXhwIjoxNjI1NDc5MjYwLCJqdGkiOiIyNDNjMjdhZS0yZjAyLTQ2YTAtODg1Mi1jNjZmMzdlYTlmNDYiLCJ1cmxfaGFzaCI6IjBmMTE1ZGIwNjJiN2MwZGQwMzBiMTY4NzhjOTlkZWE1YzM1NGI0OWRjMzdiMzhlYjg4NDYxNzljNzc4M2U5ZDcifQ._Uwf4HMtfAT6jvbBbh85Q9TunX0QlsXoaLGKX0I4VDg";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "";
        String requestPayload = "Hello, World!";

        RequestValidationException e = assertThrows(RequestValidationException.class,
                () -> runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload));

        assertEquals("Signature is invalid.", e.getMessage());
    }

    @Test
    public void testInvalidMissingPayload() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ3OTIwMCwiZXhwIjoxNjI1NDc5MjYwLCJqdGkiOiIxNDUwMTUzMi05NmYyLTQ2ODQtOTgzMi02OGYwOTUxYWUzNDIiLCJ1cmxfaGFzaCI6IjBmMTE1ZGIwNjJiN2MwZGQwMzBiMTY4NzhjOTlkZWE1YzM1NGI0OWRjMzdiMzhlYjg4NDYxNzljNzc4M2U5ZDciLCJwYXlsb2FkX2hhc2giOiIzMjRjYzA2N2IyNTdlZGEwYmNiZDljOGQ4MTgwNzdhMDlhOTU2OGMwZDRjYTA2MDM4ZGVkOGZhZGRmODEzZmQ2In0.rQqiANogDOMafgg_B6p362PuhInAro9lMm2j_vruBA0";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "";
        String requestPayload = "";

        RequestValidationException e = assertThrows(RequestValidationException.class,
                () -> runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload));
        assertEquals("The Claim 'payload_hash' was set but no payload value.", e.getMessage());
    }

    @Test
    public void testInvalidUnexpectedPayload() {
        String signature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZXNzYWdlQmlyZCIsImlhdCI6MTYyNTQ3OTIwMCwiZXhwIjoxNjI1NDc5MjYwLCJqdGkiOiI1OWEyNDRkYy1lOWFkLTRlMjMtOTc3OC0zNzFmYWEyMzhmNzIiLCJ1cmxfaGFzaCI6IjBmMTE1ZGIwNjJiN2MwZGQwMzBiMTY4NzhjOTlkZWE1YzM1NGI0OWRjMzdiMzhlYjg4NDYxNzljNzc4M2U5ZDcifQ.SrhlKJ-ES4Dg8BBXKtop3u92Z_k4L4VjHKsyHWpweGE";
        String signatureKey = TEST_SIGNATURE_KEY;
        String receivedAt = "2021-07-05T12:00:00+02:00";
        String requestParams = "";
        String requestPayload = "Hello, World!";

        RequestValidationException e = assertThrows(RequestValidationException.class,
                () -> runTestValidateSignature(signature, signatureKey, receivedAt, requestParams, requestPayload));
        assertEquals("The Claim 'payload_hash' value doesn't match the required one.", e.getMessage());
    }

    private void runTestValidateSignature(String signature, String signatureKey, String receivedAt,
            String requestParams, String requestPayload) {
        String reqUrl = TEST_BASE_URL + requestParams;
        if (requestParams == "") {
            reqUrl += "/";
        }

        RequestValidator validator = new RequestValidator(signatureKey);

        Clock clock = mock(Clock.class);
        Date clockDate = spy(Date.from(OffsetDateTime.parse(receivedAt).toInstant()));
        when(clock.getToday()).thenReturn(clockDate);

        validator.validateSignature(clock, signature, reqUrl, requestPayload.getBytes(Charset.forName("UTF-8")));
    }
}
