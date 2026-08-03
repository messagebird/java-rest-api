package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Identifies the recipient of an outbound WhatsApp message, as reported back on
 * status webhook payloads under {@code status.metadata.recipient}. Mirrors
 * {@link ConversationSenderMetadata} on the inbound side.
 *
 * <p>{@code userId} is the recipient's BSUID (e.g. "US.13491208655302741918");
 * {@code parentUserId} is the parent business-scoped user ID of the enterprise
 * that owns the business portfolio it was scoped against (e.g.
 * "US.ENT.11815799212886844830").
 *
 * <p>Either field may be {@code null}: Meta only supplies them for accounts
 * enrolled in the BSUID rollout, and the enclosing {@code recipient} object is
 * omitted entirely when neither is present. This is the only place a status
 * payload carries the recipient's own identity — {@code messageMetadata.to} is
 * an echo of the address the message was addressed to.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationRecipientMetadata {

    private String userId;
    private String parentUserId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }

    @Override
    public String toString() {
        return "ConversationRecipientMetadata{" +
                "userId='" + userId + '\'' +
                ", parentUserId='" + parentUserId + '\'' +
                '}';
    }
}
