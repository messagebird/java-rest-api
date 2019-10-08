package com.messagebird.objects.voicecalls;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a listing of VoiceCallFlow objects, along with pagination details.
 * @TODO needs a little polishing (reorganise methods, rename properties, add
 * missing properties)
 */
public class VoiceCallFlowList implements Serializable {

    @JsonProperty("_links")
    private Map<String, String> links;

    private Pagination pagination;

    private List<VoiceCallFlow> items;

    @JsonCreator
    public VoiceCallFlowList(@JsonProperty("data") List<VoiceCallFlow> data) {
        this.items = data;
    }

    @Override
    public String toString() {
        return pagination.toString();
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Integer getTotalCount() {
        return this.pagination.getTotalCount();
    }

    public Integer getPageCount() {
        return this.pagination.getPageCount();
    }

    public Integer getCurrentPage() {
        return this.pagination.getCurrentPage();
    }

    public Integer getPerPage() {
        return this.pagination.getPerPage();
    }


    public List<VoiceCallFlow> getItems() {
        return items;
    }

    public void setItems(List<VoiceCallFlow> items) {
        this.items = items;
    }
}


