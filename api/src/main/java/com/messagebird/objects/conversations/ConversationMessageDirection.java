package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Indicates the direction of a message.
 */
public enum ConversationMessageDirection {

    /**
     * Received is an in bound message received from a customer.
     */
    RECEIVED("received"),

    /**
     * Sent is an outbound message sent through the API.
     */
    SENT("sent");

    private final String direction;

    ConversationMessageDirection(final String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    @JsonCreator
    public static ConversationMessageDirection forValue(String value) {
        if ("received".equals(value)) {
            return ConversationMessageDirection.RECEIVED;
        } else if ("sent".equals(value)) {
            return ConversationMessageDirection.SENT;
        }

        return null;
    }

    @JsonValue
    public String toJson() {
        return getDirection();
    }

    @Override
    public String toString() {
        return getDirection();
    }
}
