package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of content that the message holds. Indicates what field is set on a
 * ConversationContent object.
 */
public enum ConversationContentType {

    AUDIO("audio"),
    FILE("file"),
    HSM("hsm"),
    IMAGE("image"),
    LOCATION("location"),
    TEXT("text"),
    VIDEO("video");

    private final String type;

    ConversationContentType(final String type) {
        this.type = type;
    }

    @JsonCreator
    public static ConversationContentType forValue(String value) {
        for (ConversationContentType conversationContentType : ConversationContentType.values()) {
            if (conversationContentType.getType().equals(value)) {
                return conversationContentType;
            }
        }

        return null;
    }

    @JsonValue
    public String toJson() {
        return getType();
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return getType();
    }
}
