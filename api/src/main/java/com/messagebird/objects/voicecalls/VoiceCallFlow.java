package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.messagebird.objects.VoiceStep;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class VoiceCallFlow implements Serializable {

    private static final long serialVersionUID = -6885592947212493454L;

    private String id;
    private String title;
    private boolean record;
    private List<VoiceStep> steps;

    /*
     * default is reserved name in JAVA so we use alternate name
     */
    @JsonProperty("default")
    private boolean defaultCall;

    private Date createdAt;
    private Date updatedAt;

    @JsonProperty("_links")
    private Map<String, String> links;

}
