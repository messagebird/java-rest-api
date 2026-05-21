package com.messagebird.objects.conversations;

import java.util.Date;

/**
 * Inner metadata attached to a conversation message. Present on both incoming
 * messages and status webhook payloads. {@code sender.userId} always contains
 * the BSUID when Meta provides one. When both identifiers exist, the phone
 * number appears in the parent {@code from} field, not in this object.
 */
public class ConversationMessageMetadata {

    private ConversationSenderMetadata sender;
    private Date receivedAt;

    public ConversationSenderMetadata getSender() {
        return sender;
    }

    public void setSender(ConversationSenderMetadata sender) {
        this.sender = sender;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    @Override
    public String toString() {
        return "ConversationMessageMetadata{" +
                "sender=" + sender +
                ", receivedAt=" + receivedAt +
                '}';
    }
}
