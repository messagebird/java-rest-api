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

    private Integer offset;
    private Integer limit;
    private Integer totalCount;

    @JsonProperty("_links")
    private Map<String, String> links;

    @JsonProperty("pagination")
    private Map<String, String> pagination;

    private List<VoiceCallFlow> items;

    @JsonCreator
    public VoiceCallFlowList(@JsonProperty("data") List<VoiceCallFlow> data) {
        this.items = data;
    }

    @Override
    public String toString() {
        return "ListBase{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", totalCount=" + totalCount +
                ", items=" + items +
                '}';
    }


    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public List<VoiceCallFlow> getItems() {
        return items;
    }

    public void setItems(List<VoiceCallFlow> items) {
        this.items = items;
    }
}


