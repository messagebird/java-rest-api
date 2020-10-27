package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Status for a Conversation channel. Only ACTIVE channels can be used for
 * messaging.
 */

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ConversationChannelStatus {

    ACTIVATING("activating"),
    ACTIVATION_CODE_REQUIRED("activation_code_required"),
    ACTIVATION_REQUIRED("activation_required"),
    ACTIVE("active"),
    DELETED("deleted"),
    INACTIVE("inactive"),
    PENDING("pending");

    @Getter
    private final String status;

    @JsonCreator
    public static ConversationChannelStatus forValue(final String value) {
        for (ConversationChannelStatus status : ConversationChannelStatus.values()) {
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
