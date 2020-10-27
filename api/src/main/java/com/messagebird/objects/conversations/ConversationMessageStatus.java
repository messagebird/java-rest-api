package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Response object for a message's status, e.g. delivered or failed.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ConversationMessageStatus {

    DELETED("deleted"),
    DELIVERED("delivered"),
    FAILED("failed"),
    PENDING("pending"),
    READ("read"),
    RECEIVED("received"),
    SENT("sent"),
    UNSUPPORTED("unsupported");

    @Getter
    private final String status;

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
