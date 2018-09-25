package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Indicates whether a conversation is active or if it has been archived.
 */
public enum ConversationStatus {

    ACTIVE("active"),
    ARCHIVED("archived");

    private final String status;

    ConversationStatus(final String status) {
        this.status = status;
    }

    @JsonCreator
    public static ConversationStatus forValue(final String value) {
        for (ConversationStatus conversationStatus : ConversationStatus.values()) {
            if (conversationStatus.getStatus().equals(value)) {
                return conversationStatus;
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
