package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Response object for a message's status, e.g. delivered or failed.
 */
public enum ConversationMessageStatus {

    DELETED("deleted"),
    DELIVERED("delivered"),
    FAILED("failed"),
    PENDING("pending"),
    READ("read"),
    RECEIVED("received"),
    SENT("sent"),
    UNSUPPORTED("unsupported");

    private final String status;

    ConversationMessageStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static ConversationMessageStatus forValue(final String value) {
        for (ConversationMessageStatus status : ConversationMessageStatus.values()) {
            if (status.getStatus().equals(value)) {
                return status;
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
