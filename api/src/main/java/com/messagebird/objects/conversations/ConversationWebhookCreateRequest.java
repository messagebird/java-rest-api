package com.messagebird.objects.conversations;

import java.util.List;

/**
 * Request object used to create webhooks.
 */
public class ConversationWebhookCreateRequest extends ConversationWebhookBaseRequest{

    private String channelId;

    public ConversationWebhookCreateRequest(
            final String channelId,
            final String url,
            final List<ConversationWebhookEvent> events
    ) {
        this.channelId = channelId;
        this.url = url;
        this.events = events;
    }

    @Override
    protected String getRequestName() {
        return "ConversationWebhookCreateRequest";
    }

    @Override
    protected String getStringRepresentationOfExtraParameters() {
        return "channelId='" + channelId;
    }

    public String getChannelId() {
        return channelId;
    }
}
