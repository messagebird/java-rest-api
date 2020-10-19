package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class Recording implements Serializable {

    private static final long serialVersionUID = 3456436421232378964L;
    private String id;
    private String format;
    private String type;
    private String legId;
    private String state;
    private int duration;
    private Date createdAt;
    private Date updatedAt;
    @JsonProperty("_links")
    private Map<String, String> links;

}
