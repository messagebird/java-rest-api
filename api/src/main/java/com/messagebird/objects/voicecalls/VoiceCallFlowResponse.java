package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class VoiceCallFlowResponse implements Serializable {

    private static final long serialVersionUID = -3429781513863789117L;

    private List<VoiceCallFlow> data;
    @JsonProperty("_links")
    private Map<String, String> links;

    public List<VoiceCallFlow> getData() {
        return data;
    }

    public void setData(List<VoiceCallFlow> data) {
        this.data = data;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "VoiceCallResponse{" +
                "data=" + data +
                ", links=" + links +
                '}';
    }
}
