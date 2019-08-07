package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/*
 * Used for registering the response returned by a VoiceCallFlow POST method
 * @TODO add any missing information
 */ 
public class VoiceCallFlowResponse implements Serializable {

    private List<VoiceCallFlow> data;

    @JsonProperty("_links")
    private Map<String, String> links;

    @JsonCreator
    public VoiceCallFlowResponse(@JsonProperty("data") List<VoiceCallFlow> data) {
        this.data = data;
    }

    public List<VoiceCallFlow> getData() {
        return data;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }
}
