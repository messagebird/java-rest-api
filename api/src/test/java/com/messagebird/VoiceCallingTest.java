package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.VoiceCall;
import com.messagebird.objects.voice.VoiceCallLeg;
import com.messagebird.objects.voice.VoiceCallLegResponse;
import com.messagebird.util.Resources;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;

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


    @Test
    public void testListLegs() throws IOException, GeneralException, UnauthorizedException {
        String responseFixture = Resources.readResourceText("/fixtures/call_legs_list.json");

        MessageBirdService messageBirdService =
                SpyService
                        .expects("GET", "calls/" + CallIdFixture + "/legs?")
                        .withVoiceCallAPIBaseURL()
                        .andReturns(new APIResponse(responseFixture));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        VoiceCallLegResponse legsList = messageBirdClient.viewCallLegsByCallId(CallIdFixture, 0, 50);

        assertEquals(1, legsList.getData().size());
        assertEquals(legFixture, legsList.getData().get(0));
    }


    @Test
    public void testGetLeg() throws IOException, GeneralException, UnauthorizedException, NotFoundException {
        String responseFixture = Resources.readResourceText("/fixtures/call_legs_get.json");

        MessageBirdService messageBirdService =
            SpyService
                .expects("GET", "calls/" + CallIdFixture + "/legs/" + LegIdFixture)
                .withVoiceCallAPIBaseURL()
                .andReturns(new APIResponse(responseFixture));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        VoiceCallLeg actualLeg = messageBirdClient.viewCallLegByCallIdAndLegId(CallIdFixture, LegIdFixture);

        assertEquals(legFixture, actualLeg);
    }


    private static String CallIdFixture = "unforgiven-call";
    private static String LegIdFixture = "first-leg-of-unforgiven-call";

    private static VoiceCallLeg legFixture =
        new VoiceCallLeg(
            LegIdFixture, CallIdFixture, "Heaven", "31612345678",
            VoiceCallLeg.LegStatus.Hangup, VoiceCallLeg.LegDirection.Outgoing,
            new BigDecimal("0.001519"), "EUR", 7,
            "2019-01-10T16:12:54Z", "2019-01-10T16:13:54Z", "2019-01-10T16:13:24Z", "2019-01-10T16:13:30Z"
        );
}
