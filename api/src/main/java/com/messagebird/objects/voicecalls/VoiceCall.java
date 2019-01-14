package com.messagebird.objects.voicecalls;

import com.messagebird.objects.MessageBase;

import java.io.Serializable;
import java.util.Date;

public class VoiceCall implements MessageBase, Serializable {

    private static final long serialVersionUID = -3429781513863789117L;

    private String source;
    private String destination;
    private VoiceCallFlow callFlow;

    private String webhookUrl;
    private String webhookToken;

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

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public String getWebhookToken() {
        return webhookToken;
    }

    public void setWebhookToken(String webhookToken) {
        this.webhookToken = webhookToken;
    }

    @Override
    public String toString() {
        return "VoiceCall{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", callFlow=" + callFlow +
                ", webhookUrl='" + webhookUrl + '\'' +
                ", webhookToken='" + webhookToken + '\'' +
                '}';
    }
}
