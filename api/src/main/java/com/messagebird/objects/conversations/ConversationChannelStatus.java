package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Status for a Conversation channel. Only ACTIVE channels can be used for
 * messaging.
 */
public enum ConversationChannelStatus {

    ACTIVE("active"),
    DELETED("deleted"),
    INACTIVE("inactive"),
    PENDING("pending"),
    ACTIVATION_REQUIRED("activation_required"),
    ACTIVATION_CODE_REQUIRED("activation_code_required"),
    ACTIVATING("activating");

    private final String status;

    ConversationChannelStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

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
