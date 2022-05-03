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
    UNSUPPORTED("unsupported"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    UNKNOWN("unknown"),
    //WA specific statuses
    TRANSMITTED("transmitted"),
    //SMS specific statuses
    DELIVERY_FAILED("delivery_failed"),
    BUFFERED("buffered"),
    EXPIRED("expired"),
    //Email specific statuses
    CLICKED("clicked"),
    OPENED("opened"),
    BOUNCE("bounce"),
    SPAM_COMPLAINT("spam_complaint"),
    OUT_OF_BOUNDED("out_of_bounded"),
    DELAYED("delayed"),
    LIST_UNSUBSCRIBE("list_unsubscribe"),
    DISPATCHED("dispatched");

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
