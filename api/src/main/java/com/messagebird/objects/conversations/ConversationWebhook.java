package com.messagebird.objects.conversations;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Webhooks enable real-time notifications of conversation events to be
 * delivered to endpoints on your own server. A webhook can be configured with
 * a URL and a list of events that should be subscribed to for notifications.
 * It's possible to create multiple webhooks with different URLs to listen to
 * one or more events each.
 */
public class ConversationWebhook {

    private String id;
    private String channelId;
    private String url;
    private ConversationWebhookStatus status;
    private List<ConversationWebhookEvent> events;
    private Date createdDatetime;
    private Date updatedDatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ConversationWebhookStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationWebhookStatus status) {
        this.status = status;
    }

    public List<ConversationWebhookEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ConversationWebhookEvent> events) {
        this.events = events;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(Date updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    @Override
    public String toString() {
        return "ConversationWebhook{" +
                "id='" + id + '\'' +
                ", channelId='" + channelId + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status  + '\'' +
                ", events=" + events.stream().map(ConversationWebhookEvent::toString).collect(Collectors.joining(",")) +
                ", createdDatetime=" + createdDatetime +
                ", updatedDatetime=" + updatedDatetime +
                '}';
    }


}
