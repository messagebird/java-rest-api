package com.messagebird.objects.voicecalls;

import com.messagebird.objects.VoiceStep;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VoiceCallFlow implements Serializable {

    private static final long serialVersionUID = -6885592947212493454L;

    private String id;
    private String title;
    private boolean record;
    private List<VoiceStep> steps;
    private boolean defaultCall;
    private Date createdAt;
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRecord() {
        return record;
    }

    public void setRecord(boolean record) {
        this.record = record;
    }

    public List<VoiceStep> getSteps() {
        return steps;
    }

    public void setSteps(List<VoiceStep> steps) {
        this.steps = steps;
    }

    public boolean isDefaultCall() {
        return defaultCall;
    }

    public void setDefaultCall(boolean defaultCall) {
        this.defaultCall = defaultCall;
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

    @Override
    public String toString() {
        return "VoiceCallFlow{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", record=" + record +
                ", steps=" + steps +
                ", defaultCall=" + defaultCall +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
