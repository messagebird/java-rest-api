package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class Transcription implements Serializable {

    private static final long serialVersionUID = 2741082047875421624L;
    private String id;
    private String recordingId;
    private String error;
    private String status;
    private String legId;
    private Date createdAt;
    private Date updatedAt;
    @JsonProperty("_links")
    private Map<String, String> links;
}
