package com.messagebird;

import com.messagebird.objects.*;
import java.util.Collections;

class TestUtil {

    private TestUtil() {
    }

    static VoiceCall createVoiceCall(String destination) {
        final VoiceCall voiceCall = new VoiceCall();
        voiceCall.setSource("31644556677");
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

    static VoiceCallResponse createVoiceCallResponse() {
        final VoiceCallResponse voiceCallResponse = new VoiceCallResponse();
        VoiceCallData voiceCallData = new VoiceCallData();
        voiceCallData.setId("ANY_ID");
        voiceCallData.setStatus(VoiceCallStatus.ended);
        voiceCallData.setDestination("31633612867");
        voiceCallData.setSource("31644556677");
        voiceCallResponse.setData(Collections.singletonList(voiceCallData));
        return voiceCallResponse;
    }

    static VoiceCallResponseList createVoiceCallResponseList() {
        final VoiceCallResponseList voiceCallResponseList = new VoiceCallResponseList();
        voiceCallResponseList.setItems(Collections.singletonList(createVoiceCallResponse()));
        return voiceCallResponseList;
    }
}
