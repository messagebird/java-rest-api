package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Indicates whether a conversation webhook is <strong>enabled</strong> or <strong>disabled</strong>.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ConversationWebhookStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    @Getter private final String status;

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

    @Override
    public String toString() {
        return getStatus();
    }
}
