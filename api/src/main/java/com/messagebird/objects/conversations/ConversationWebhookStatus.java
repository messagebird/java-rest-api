package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Indicates whether a conversation webhook is <strong>enabled</strong> or <strong>disabled</strong>.
 */
public enum ConversationWebhookStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    private final String status;

    ConversationWebhookStatus(final String status) {
        this.status = status;
    }

    @JsonCreator
    public static ConversationWebhookStatus forValue(final String value) {
        for (ConversationWebhookStatus conversationWebhookStatus : ConversationWebhookStatus.values()) {
            if (conversationWebhookStatus.getStatus().equals(value)) {
                return conversationWebhookStatus;
            }
        }

        return null;
    }

    @JsonValue
    public String toJson() {
        return getStatus();
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return getStatus();
    }
}
