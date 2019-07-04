package com.messagebird.objects.conversations;

import java.util.List;

/**
 * Request object used to update webhook.
 */
public class ConversationWebhookUpdateRequest extends ConversationWebhookBaseRequest {
    private ConversationWebhookStatus status;

    public ConversationWebhookUpdateRequest(
            final ConversationWebhookStatus status,
            final String url,
            final List<ConversationWebhookEvent> events
    ) {
        this.status = status;
        this.url = url;
        this.events = events;
    }

    public ConversationWebhookStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationWebhookStatus status) {
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
