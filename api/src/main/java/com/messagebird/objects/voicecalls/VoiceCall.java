package com.messagebird.objects.voicecalls;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.messagebird.objects.MessageBase;

import java.io.Serializable;
import java.util.Date;

public class VoiceCall implements MessageBase, Serializable {

    private static final long serialVersionUID = -3429781513863789117L;

    private String source;
    private String destination;
    private VoiceCallFlow callFlow;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public VoiceCallFlow getCallFlow() {
        return callFlow;
    }

    public void setCallFlow(VoiceCallFlow callFlow) {
        this.callFlow = callFlow;
    }

    public Webhook getWebhook() {
        return webhook;
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

    @Override
    public String toString() {
        return "VoiceCall{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", callFlow=" + callFlow +
                ", webhook=" + webhook +
                '}';
    }
}
