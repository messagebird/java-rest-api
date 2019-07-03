package com.messagebird.objects.conversations;

import java.util.List;

/**
 * Request object used to create webhooks.
 */
public class ConversationWebhookRequest extends ConversationWebhookBaseRequest{

    private String channelId;

    public ConversationWebhookRequest(
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
        return "ConversationWebhookRequest";
    }

    @Override
    protected String getStringRepresentationOfExtraParameters() {
        return "channelId='" + channelId;
    }
}
