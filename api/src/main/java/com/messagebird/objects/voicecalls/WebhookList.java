package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class WebhookList implements Serializable {

    private static final long serialVersionUID = -5524142916135114801L;

    private List<WebhookResponse> data;
    @JsonProperty("_list")
    private Map<String, String> links;
    private Pagination pagination;

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

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "WebhookList{" +
                "data=" + data +
                ", links=" + links +
                ", pagination=" + pagination +
                '}';
    }
}
