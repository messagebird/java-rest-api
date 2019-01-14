package com.messagebird.objects.voicecalls;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class WebhookResponse implements Serializable {

    private static final long serialVersionUID = -2160700071636853317L;

    private String id;
    private String url;
    private String token;
    private Date createdAt;
    private Date updatedAt;
    private Map<String, String> _links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        return "WebhookResponse{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", token='" + token + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", _links=" + _links +
                '}';
    }
}
