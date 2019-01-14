package com.messagebird.objects.voicecalls;

import java.io.Serializable;

public class Webhook implements Serializable {

    private static final long serialVersionUID = 727746356185518354L;

    private String title;
    private String url;
    private String token;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Webhook{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
