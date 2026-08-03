package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Metadata about the sender of a WhatsApp message. {@code userId} always
 * contains the BSUID (e.g. "US.13491208655302741918") when Meta supplies one.
 * When both a phone number and a BSUID are available, the phone number appears
 * in the parent message's {@code from} field — not here.
 *
 * <p>{@code parentUserId} carries the sender's parent business-scoped user ID
 * (e.g. "US.ENT.11815799212886844830"), which identifies the enterprise that
 * owns the business portfolio the {@code userId} was scoped against. It is only
 * present for accounts enrolled in Meta's parent-BSUID rollout; for everyone
 * else it stays {@code null}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationSenderMetadata {

    private String userId;
    private String parentUserId;
    private String username;
    private String displayName;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "ConversationSenderMetadata{" +
                "userId='" + userId + '\'' +
                ", parentUserId='" + parentUserId + '\'' +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
