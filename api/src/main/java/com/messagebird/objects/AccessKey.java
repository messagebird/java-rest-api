package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AccessKey {
    private String id;
    @JsonProperty("access_key")
    private String accessKey;
    private String mod;
    private String description;
    private int core_user_id;
    private int user_id;
    private int external_id;
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

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCore_user_id() {
        return core_user_id;
    }

    public void setCore_user_id(int core_user_id) {
        this.core_user_id = core_user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getExternal_id() {
        return external_id;
    }

    public void setExternal_id(int external_id) {
        this.external_id = external_id;
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
                ", mod='" + mod + '\'' +
                ", description='" + description + '\'' +
                ", core_user_id=" + core_user_id +
                ", user_id=" + user_id +
                ", external_id=" + external_id +
                ", roles=" + roles +
                '}';
    }
}
