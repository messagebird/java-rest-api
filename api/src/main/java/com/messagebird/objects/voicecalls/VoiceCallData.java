package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VoiceCallStatus getStatus() {
        return status;
    }

    public void setStatus(VoiceCallStatus status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Date endedAt) {
        this.endedAt = endedAt;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "VoiceCallData{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", endedAt=" + endedAt +
                ", links=" + links +
                '}';
    }
}
