package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.Verify;
import com.messagebird.objects.VerifyRequest;
import com.messagebird.objects.VerifyType;

import static org.junit.Assert.*;

import org.junit.Test;

public class VerifyTest {

    private static final String VERIFY_SMS_RESPONSE = "{\"id\": \"verify-id-sms\",\"href\": \"https://rest.messagebird.com/verify/verify-id-sms\",\"recipient\": 31612345678,\"reference\": null,\"messages\": {\"href\": \"https://rest.messagebird.com/messages/5958c0d5e2df41de8154e5e88bfeb5bc\"},\"status\": \"sent\",\"createdDatetime\": \"2018-09-25T14:38:12+00:00\",\"validUntilDatetime\": \"2018-09-25T14:38:42+00:00\"}";
    private static final String VERIFY_TTS_RESPONSE = "{\"id\": \"verify-id-tts\",\"href\": \"https://rest.messagebird.com/verify/verify-id-tts\",\"recipient\": 31612345678,\"reference\": null,\"messages\": {\"href\": \"https://rest.messagebird.com/voicemessages/c043ab473f8e4f2590ab9a16d25f2899\"},\"status\": \"sent\",\"createdDatetime\": \"2018-09-25T14:35:10+00:00\",\"validUntilDatetime\": \"2018-09-25T14:35:40+00:00\"}";

    @Test
    public void testSendVerifyTokenSms() throws GeneralException, UnauthorizedException {
        VerifyRequest verifyRequest = new VerifyRequest("31612345678");
        verifyRequest.setType(VerifyType.SMS);

        MessageBirdService messageBirdService = SpyService
                .expects("POST", "verify", verifyRequest)
                .withRestAPIBaseURL()
                .andReturns(new APIResponse(VERIFY_SMS_RESPONSE, 200));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        Verify verify = messageBirdClient.sendVerifyToken(verifyRequest);

        assertEquals("verify-id-sms", verify.getId());
    }

    @Test
    public void testSendVerifyTokenTts() throws GeneralException, UnauthorizedException {
        VerifyRequest verifyRequest = new VerifyRequest("31612345678");
        verifyRequest.setType(VerifyType.TTS);

        MessageBirdService messageBirdService = SpyService
                .expects("POST", "verify", verifyRequest)
                .withRestAPIBaseURL()
                .andReturns(new APIResponse(VERIFY_TTS_RESPONSE, 200));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        Verify verify = messageBirdClient.sendVerifyToken(verifyRequest);

        assertEquals("verify-id-tts", verify.getId());
    }

    @Test
    public void testVerifyTypeValue() {
        // Important for generating proper JSON payloads...
        assertEquals("flash", VerifyType.FLASH.getValue());
        assertEquals("sms", VerifyType.SMS.getValue());
        assertEquals("tts", VerifyType.TTS.getValue());
    }
}
