package com.messagebird.objects;

public class AccessKey {
    private String id;
    private String key;
    private String mod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    @Override
    public String toString() {
        return "AccessKey{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", mod='" + mod + '\'' +
                '}';
    }
}
