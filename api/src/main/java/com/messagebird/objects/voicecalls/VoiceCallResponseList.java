package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class VoiceCallResponseList implements Serializable {

    private static final long serialVersionUID = -3429781513863789117L;

    private List<VoiceCallData> data;
    @JsonProperty("_links")
    private Map<String, String> links;
    private Pagination pagination;
}