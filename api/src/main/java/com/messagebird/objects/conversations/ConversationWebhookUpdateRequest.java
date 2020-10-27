package com.messagebird.objects.conversations;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Request object used to update webhook.
 */

public class ConversationWebhookUpdateRequest extends ConversationWebhookBaseRequest {
    @Getter
    @Setter
    private ConversationWebhookStatus status;

    @Builder
    public ConversationWebhookUpdateRequest(
            final ConversationWebhookStatus status,
            final String url,
            final List<ConversationWebhookEvent> events
    ) {
        super(url, events);
        this.status = status;
    }

    @Override
    protected String getRequestName() {
        return "ConversationWebhookUpdateRequest";
    }

    @Override
    protected String getStringRepresentationOfExtraParameters() {
        return "status='" + status;
    }
}
