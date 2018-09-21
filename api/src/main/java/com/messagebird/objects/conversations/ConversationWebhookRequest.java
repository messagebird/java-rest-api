package com.messagebird.objects.conversations;

import java.util.List;

/**
 * Request object used to create webhooks.
 */
public class ConversationWebhookRequest {

    private String channelId;
    private String url;
    private List<ConversationWebhookEvent> events;

    public ConversationWebhookRequest(
            final String channelId,
            final String url,
            final List<ConversationWebhookEvent> events
    ) {
        this.channelId = channelId;
        this.url = url;
        this.events = events;
    }

    public ConversationWebhookRequest() {
        //
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ConversationWebhookEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ConversationWebhookEvent> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "ConversationWebhookRequest{" +
                "channelId='" + channelId + '\'' +
                ", url='" + url + '\'' +
                ", events=" + events +
                '}';
    }
}
