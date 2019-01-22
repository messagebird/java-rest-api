package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VoiceCallLegResponse {

    private List<VoiceCallLeg> data;

    @JsonCreator
    public VoiceCallLegResponse(@JsonProperty("data") List<VoiceCallLeg> data) {
        this.data = data;
    }

    public List<VoiceCallLeg> getData() {
        return data;
    }
}
