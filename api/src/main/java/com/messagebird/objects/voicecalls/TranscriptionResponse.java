package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TranscriptionResponse implements Serializable {

    private static final long serialVersionUID = -25064223639161201L;
    private List<Transcription> data;

    @JsonProperty("_links")
    private Map<String, String> links;
    private Pagination pagination;

    public TranscriptionResponse() {}

    public TranscriptionResponse(List<Transcription> data, Map<String, String> links, Pagination pagination) {
        this.data = data;
        this.links = links;
        this.pagination = pagination;
    }

    public List<Transcription> getData() {
        return data;
    }

    public void setData(List<Transcription> data) {
        this.data = data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
        return "TranscriptionResponse{" +
                "data=" + data +
                ", links=" + links +
                ", pagination=" + pagination +
                '}';
    }
}
