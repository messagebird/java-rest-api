package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.messagebird.objects.MessageBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
@ToString
public class VoiceCall implements MessageBase, Serializable {

    private static final long serialVersionUID = -3429781513863789117L;

    @Getter
    @Setter
    private String source;

    @Getter
    @Setter
    private String destination;

    @Getter
    @Setter
    private VoiceCallFlow callFlow;

    @Getter
    private Webhook webhook = new Webhook();

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public String getRecipients() {
        return null;
    }

    @Override
    public String getReference() {
        return null;
    }

    @Override
    public Date getScheduledDatetime() {
        return null;
    }

    public void setWebhook(String url) {
        this.setWebhook(url, null);
    }

    public void setWebhook(String url, String token) {
        this.webhook.setUrl(url);
        this.webhook.setToken(token);
    }

    @JsonIgnore
    @Deprecated
    public String getWebhookUrl() {
        return webhook.getUrl();
    }

    @JsonIgnore
    @Deprecated
    public void setWebhookUrl(String webhookUrl) {
        this.webhook.setUrl(webhookUrl);
    }

    @JsonIgnore
    @Deprecated
    public String getWebhookToken() {
        return webhook.getToken();
    }

    @JsonIgnore
    @Deprecated
    public void setWebhookToken(String webhookToken) {
        this.webhook.setToken(webhookToken);
    }

}
