package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RecordingResponse implements Serializable {

    private static final long serialVersionUID = -3645909436942694671L;

    private List<Recording> data;
    @JsonProperty("_links")
    private Map<String, String> links;
    private Pagination pagination;

    private RecordingResponse(){

    }

    public RecordingResponse(List<Recording> data, Map<String, String> _links, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
        this.links = _links;
    }

    public List<Recording> getData() {
        return data;
    }

    public void setData(List<Recording> data) {
        this.data = data;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "RecordingResponse{" +
                "data=" + data +
                ", links=" + links +
                ", pagination=" + pagination +
                '}';
    }

    public void calculate(int a1){
        System.out.println(a1);
    }
}
