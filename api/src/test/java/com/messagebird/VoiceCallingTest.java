package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.VoiceCall;
import org.junit.Test;

public class VoiceCallingTest {

    private static final String JSON_GROUP = "{\"id\": \"group-id\",\"href\": \"https://voice.messagebird.com/calls\",\"name\": \"Friends\",\"contacts\": {\"totalCount\": 3,\"href\": \"https://voice.messagebird.com/calls\"},\"createdDatetime\": \"2018-07-25T12:16:10+00:00\",\"updatedDatetime\": \"2018-07-25T12:16:23+00:00\"}";

    @Test
    public void testSendVoiceCall() throws GeneralException, UnauthorizedException {
        VoiceCall voiceCallRequest = TestUtil.createVoiceCall("ANY_DESTINATION");

        MessageBirdService messageBirdService = SpyService
                .expects("POST", "calls", voiceCallRequest)
                .withVoiceCallAPIBaseURL()
                .andReturns(new APIResponse(JSON_GROUP));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        messageBirdClient.sendVoiceCall(voiceCallRequest);
    }

}
