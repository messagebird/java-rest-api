package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * These allow tagging a message based on Facebook tags.
 * For more information visit: https://developers.facebook.com/docs/messenger-platform/send-messages/message-tags/
 */

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ConversationMessageTag {
    EventUpdate("event.update"),
    PurchaseUpdate("purchase.update"),
    AccountUpdate("account.update"),
    HumanAgent("human_agent");

    @Getter
    @JsonValue
    private final String tag;

    @JsonCreator
    public static ConversationMessageTag forValue(final String value) {
        for (ConversationMessageTag tag : ConversationMessageTag.values()) {
            if (tag.getTag().equals(value)) {
                return tag;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getTag();
    }
}
