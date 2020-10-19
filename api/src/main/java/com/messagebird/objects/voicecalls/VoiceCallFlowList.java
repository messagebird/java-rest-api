package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Represents a listing of VoiceCallFlow objects, along with pagination details.
 * @TODO needs a little polishing (reorganise methods, rename properties, add
 * missing properties)
 */
public class VoiceCallFlowList implements Serializable {

    @Setter
    private Pagination pagination;

    @Getter
    @Setter
    private List<VoiceCallFlow> items;

    @JsonProperty("_links")
    private Map<String, String> links;

    @JsonCreator
    public VoiceCallFlowList(@JsonProperty("data") List<VoiceCallFlow> data) {
        this.items = data;
    }

    @Override
    public String toString() {
        return pagination.toString();
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

}


