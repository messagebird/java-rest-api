package com.messagebird.objects.conversations;

/**
 * Request object that is used to send new messages over a channel.
 */
public class ConversationMessageRequest {

    private ConversationContentType type;
    private ConversationContent content;
    private String channelId;

    public ConversationContentType getType() {
        return type;
    }

    public void setType(ConversationContentType type) {
        this.type = type;
    }

    public ConversationContent getContent() {
        return content;
    }

    public void setContent(ConversationContent content) {
        this.content = content;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "ConversationMessageRequest{" +
                "type=" + type +
                ", content=" + content +
                ", channelId='" + channelId + '\'' +
                '}';
    }
}
