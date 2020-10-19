package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class WebhookList implements Serializable {

    private static final long serialVersionUID = -5524142916135114801L;

    private List<WebhookResponse> data;
    @JsonProperty("_list")
    private Map<String, String> links;
    private Pagination pagination;

}
