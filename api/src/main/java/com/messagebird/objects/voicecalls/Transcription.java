package com.messagebird.objects.voicecalls;

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
    private Map<String, String> _links;

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

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> _links) {
        this._links = _links;
    }

    @Override
    public String toString() {
        return "Transcription{" +
                "id='" + id + '\'' +
                ", recordingId='" + recordingId + '\'' +
                ", error='" + error + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", _links='" + _links + '\'' +
                '}';
    }
}
