package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class WebhookResponseData implements Serializable {

    private static final long serialVersionUID = 7840085698939378254L;
    private List<WebhookResponse> data;
    @JsonProperty("_links")
    private Map<String, String> links;

}
