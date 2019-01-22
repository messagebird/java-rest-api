package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Transcription implements Serializable {

    private static final long serialVersionUID = 2741082047875421624L;
    private String id;
    private String recordingId;
    private String error;
    private Date createdAt;
    private Date updatedAt;
    @JsonProperty("_links")
    private Map<String, String> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordingId() {
        return recordingId;
    }

    public void setRecordingId(String recordingId) {
        this.recordingId = recordingId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Transcription{" +
                "id='" + id + '\'' +
                ", recordingId='" + recordingId + '\'' +
                ", error='" + error + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", links='" + links + '\'' +
                '}';
    }
}
