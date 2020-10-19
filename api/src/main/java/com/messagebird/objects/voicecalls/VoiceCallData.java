package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class VoiceCallData implements Serializable {

    private static final long serialVersionUID = -7586467741622154429L;

    private String id;
    private VoiceCallStatus status;
    private String source;
    private String destination;
    private Date createdAt;
    private Date updatedAt;
    private Date endedAt;
    @JsonProperty("_links")
    private Map<String,String> links;
}
