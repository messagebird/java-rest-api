package com.messagebird.objects.conversations;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains common fields for webhook requests.
 */
public abstract class ConversationWebhookBaseRequest {
    protected String url;
    protected List<ConversationWebhookEvent> events;

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

    protected abstract String getRequestName();

    protected abstract String getStringRepresentationOfExtraParameters();

    @Override
    public String toString() {
        return getRequestName() + "{" +
                getStringRepresentationOfExtraParameters() + '\'' +
                ", url='" + url + '\'' +
                ", events=" + events.stream().map(ConversationWebhookEvent::toString).collect(Collectors.joining(",")) +
                '}';
    }
}
