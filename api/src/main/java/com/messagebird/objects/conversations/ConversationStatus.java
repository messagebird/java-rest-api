package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Indicates whether a conversation is active or if it has been archived.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ConversationStatus {

    ACTIVE("active"),
    ARCHIVED("archived");

    @Getter private final String status;

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


    @Override
    public String toString() {
        return getStatus();
    }
}
