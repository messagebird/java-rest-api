package com.messagebird.objects.voicecalls;

import java.util.List;
import java.util.Date;
import com.messagebird.objects.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains writable values for VoiceCallFlow objects.
 */
public class VoiceCallFlowRequest {

 
    private String id;
    private String title;
    private boolean record;
    private List<VoiceStep> steps;
    
    @JsonProperty("default")
    private boolean defaultCall;
    private boolean defaultWebRtc;
    private Date createdAt;
    private Date updatedAt;

    public VoiceCallFlowRequest(String id)
    {
        this.id = id;
    }

    public VoiceCallFlowRequest() 
    {
    }

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

    public boolean isDefaultWebRtc() {
        return defaultWebRtc;
    }

    public void setDefaultWebRtc(boolean defaultWebRtc) {
        this.defaultWebRtc = defaultWebRtc;
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
                "title='" + title + '\'' +
                ", record=" + record +
                ", steps=" + steps +
                ", default=" + defaultCall +
                ", defaultWebRtc=" + defaultWebRtc +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
