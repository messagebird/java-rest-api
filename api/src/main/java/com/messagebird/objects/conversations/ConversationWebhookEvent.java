package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents events that webhooks can subscribe to.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ConversationWebhookEvent {

    /**
     * New conversation has been created.
     */
    CONVERSATION_CREATED("conversation.created"),

    /**
     * Conversation has been updated with a new status.
     */
    CONVERSATION_UPDATED("conversation.updated"),

    /**
     * New message has been created. Triggered for both sent and received
     * messages.
     */
    MESSAGE_CREATED("message.created"),

    /**
     * Message has been updated with a new status.
     */
    MESSAGE_UPDATED("message.updated");

    @Getter private final String event;

    @JsonCreator
    public static ConversationWebhookEvent forValue(final String value) {
        for (ConversationWebhookEvent event : ConversationWebhookEvent.values()) {
            if (event.getEvent().equals(value)) {
                return event;
            }
        }

        return null;
    }

    @JsonValue
    public String toJson() {
        return getEvent();
    }

    @Override
    public String toString() {
        return getEvent();
    }
}
