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
    private String status;
    private String legId;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLegId() {
        return legId;
    }

    public void setLegId(String legId) {
        this.legId = legId;
    }

    @Override
    public String toString() {
        return "Transcription{" +
                "id='" + id + '\'' +
                ", recordingId='" + recordingId + '\'' +
                ", error='" + error + '\'' +
                ", status='" + status + '\'' +
                ", legId='" + legId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", links=" + links +
                '}';
    }
}
