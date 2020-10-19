package com.messagebird.objects.conversations;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Request object used to create webhooks.
 */
public class ConversationWebhookCreateRequest extends ConversationWebhookBaseRequest{

    @Getter private String channelId;

    @Builder
    public ConversationWebhookCreateRequest(
            final String channelId,
            final String url,
            final List<ConversationWebhookEvent> events
    ) {
        super(url, events);
        this.channelId = channelId;
    }

    @Override
    protected String getRequestName() {
        return "ConversationWebhookCreateRequest";
    }

    @Override
    protected String getStringRepresentationOfExtraParameters() {
        return "channelId='" + channelId;
    }
}
