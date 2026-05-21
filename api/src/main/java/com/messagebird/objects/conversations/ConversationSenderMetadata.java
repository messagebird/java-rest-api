package com.messagebird.objects.conversations;

/**
 * Metadata about the sender of a WhatsApp message. {@code userId} always
 * contains the BSUID (e.g. "US.13491208655302741918") when Meta supplies one.
 * When both a phone number and a BSUID are available, the phone number appears
 * in the parent message's {@code from} field — not here.
 */
public class ConversationSenderMetadata {

    private String userId;
    private String username;
    private String displayName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
