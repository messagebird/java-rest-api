package com.messagebird.objects.voicecalls;

import com.messagebird.objects.VoiceStep;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VoiceCallFlow implements Serializable {

    private static final long serialVersionUID = -6885592947212493454L;

    private String id;
    private String title;
    private boolean record;
    private List<VoiceStep> steps;

    /*
     * default is reserved name in JAVA so we use alternate name
     */
    @JsonProperty("default")
    private boolean defaultCall;

    @JsonProperty("maxDuration")
    private Integer maxDuration;

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

    @Deprecated
    public String getTitle() {
        return title;
    }

    @Deprecated
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

    public Integer getMaxDuration() { return maxDuration; }

    public void setMaxDuration(Integer maxDuration) { this.maxDuration = maxDuration; }

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
        return "VoiceCallFlow{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", record=" + record +
                ", steps=" + steps +
                ", default=" + defaultCall +
                ", maxDuration=" + maxDuration +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
