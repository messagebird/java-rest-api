package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AccessKey {
    private String id;
    @JsonProperty("access_key")
    private String accessKey;
    private String mode;
    private String description;
    @JsonProperty("core_user_id")
    private int coreUserId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("external_id")
    private int externalId;
    private List roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoreUserId() {
        return coreUserId;
    }

    public void setCoreUserId(int coreUserId) {
        this.coreUserId = coreUserId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AccessKey{" +
                "id='" + id + '\'' +
                ", access_key='" + accessKey + '\'' +
                ", mod='" + mode + '\'' +
                ", description='" + description + '\'' +
                ", core_user_id=" + coreUserId +
                ", user_id=" + userId +
                ", external_id=" + externalId +
                ", roles=" + roles +
                '}';
    }
}
