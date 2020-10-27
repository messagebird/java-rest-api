package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * The type of content that the message holds. Indicates what field is set on a
 * ConversationContent object.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ConversationContentType {

    AUDIO("audio"),
    FILE("file"),
    HSM("hsm"),
    IMAGE("image"),
    LOCATION("location"),
    TEXT("text"),
    VIDEO("video"),
    EMAIL("email");

    @Getter
    private final String type;

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

    @Override
    public String toString() {
        return getType();
    }
}
