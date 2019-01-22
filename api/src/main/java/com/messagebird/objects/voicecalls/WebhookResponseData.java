package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class WebhookResponseData implements Serializable {

    private static final long serialVersionUID = 7840085698939378254L;
    private List<WebhookResponse> data;
    @JsonProperty("_links")
    private Map<String, String> links;

    public List<WebhookResponse> getData() {
        return data;
    }

    public void setData(List<WebhookResponse> data) {
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
        return "WebhookResponseData{" +
                "data=" + data +
                ", links=" + links +
                '}';
    }
}
