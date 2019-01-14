package com.messagebird;

import com.messagebird.objects.*;
import com.messagebird.objects.voicecalls.*;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

class TestUtil {

    private TestUtil() {
    }

    static VoiceCall createVoiceCall(String destination) {
        final VoiceCall voiceCall = new VoiceCall();
        voiceCall.setSource("ANY_SOURCE");
        voiceCall.setDestination(destination);

        final VoiceCallFlow voiceCallFlow = new VoiceCallFlow();
        voiceCallFlow.setTitle("Test title");
        VoiceStep voiceStep = new VoiceStep();
        voiceStep.setAction("say");

        final VoiceStepOption voiceStepOption = new VoiceStepOption();
        voiceStepOption.setPayload("This is a journey into sound. Good bye!");
        voiceStepOption.setVoice(VoiceType.male.getValue());
        voiceStepOption.setLanguage("en-US");
        voiceStep.setOptions(voiceStepOption);

        voiceCallFlow.setSteps(Collections.singletonList(voiceStep));
        voiceCall.setCallFlow(voiceCallFlow);
        return voiceCall;
    }

    private static VoiceCallData createVoiceCallData() {
        final VoiceCallData voiceCallData = new VoiceCallData();
        voiceCallData.setId("ANY_ID");
        voiceCallData.setDestination("ANY_DESTINATION");
        voiceCallData.setSource("ANY_SOURCE");
        voiceCallData.setStatus(VoiceCallStatus.ended);
        return voiceCallData;
    }

    static VoiceCallResponse createVoiceCallResponse() {
        final VoiceCallResponse voiceCallResponse = new VoiceCallResponse();
        final VoiceCallData voiceCallData = new VoiceCallData();
        voiceCallData.setId("ANY_ID");
        voiceCallData.setStatus(VoiceCallStatus.ended);
        voiceCallData.setDestination("ANY_DESTINATION");
        voiceCallData.setSource("ANY_SOURCE");
        voiceCallResponse.setData(Collections.singletonList(voiceCallData));
        return voiceCallResponse;
    }

    static VoiceCallResponseList createVoiceCallResponseList() {
        final VoiceCallResponseList voiceCallResponseList = new VoiceCallResponseList();
        voiceCallResponseList.setData(Collections.singletonList(createVoiceCallData()));
        return voiceCallResponseList;
    }

    static Recording createRecording() {
        final Recording recording = new Recording();
        recording.setId("ANY_ID");
        recording.setFormat("wav");
        recording.setType("ivr");
        recording.setLegId("ANY_LEG_ID");
        recording.setState("done");
        recording.setDuration(42);
        recording.setCreatedAt(new Date());
        recording.setUpdatedAt(new Date());

        Map<String, String> links = new LinkedHashMap<>();
        links.put("self", "ANY_SELF");
        links.put("file", "ANY_FILE");
        recording.set_links(links);
        return recording;
    }

    static TranscriptionResponse createTranscriptionResponse() {
        final TranscriptionResponse transcriptionResponse = new TranscriptionResponse();
        final Transcription transcription = new Transcription();
        transcription.setId("ANY_ID");
        transcription.setRecordingId("ANY_ID");
        transcription.setCreatedAt(new Date());
        transcriptionResponse.setData(Collections.singletonList(transcription));
        return transcriptionResponse;
    }

    static Webhook createWebHook() {
        final Webhook webhook = new Webhook();
        webhook.setTitle("ANY_TITLE");
        webhook.setUrl("ANY_URL");
        return webhook;
    }

    private static WebhookResponse createWebhookResponse() {
        final WebhookResponse webhookResponse = new WebhookResponse();
        webhookResponse.setId("ANY_ID");
        webhookResponse.setUrl("ANY_URL");
        webhookResponse.setToken("ANY_TOKEN");
        return webhookResponse;
    }

    static WebhookResponseData createWebhookResponseData() {
        final WebhookResponseData webhookResponseData = new WebhookResponseData();
        webhookResponseData.setData(Collections.singletonList(createWebhookResponse()));
        return webhookResponseData;
    }
}
