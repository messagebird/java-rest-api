package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * These allow tagging a message based on Facebook tags.
 * For more information visit: https://developers.facebook.com/docs/messenger-platform/send-messages/message-tags/
 */
public enum ConversationMessageTag {
    EventUpdate("event.update"),
    PurchaseUpdate("purchase.update"),
    AccountUpdate("account.update"),
    HumanAgent("human_agent");

    @JsonValue
    private final String tag;

    ConversationMessageTag(String tag) {
        this.tag = tag;
    }

    @JsonCreator
    public static ConversationMessageTag forValue(final String value) {
        for (ConversationMessageTag tag : ConversationMessageTag.values()) {
            if (tag.getTag().equals(value)) {
                return tag;
            }
        }
        return null;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
