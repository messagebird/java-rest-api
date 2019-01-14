package com.messagebird.objects.voicecalls;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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
    private Map<String, String> _links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLegId() {
        return legId;
    }

    public void setLegId(String legId) {
        this.legId = legId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        return "Recording{" +
                "id='" + id + '\'' +
                ", format='" + format + '\'' +
                ", type='" + type + '\'' +
                ", legId='" + legId + '\'' +
                ", state='" + state + '\'' +
                ", duration=" + duration +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", _links=" + _links +
                '}';
    }
}
