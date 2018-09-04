package com.messagebird.objects.conversations;

/**
 * Request object used for starting a conversation.
 */
public class ConversationStartRequest {

    private String to;
    private ConversationContentType type;
    private ConversationContent content;
    private String channelId;

    public ConversationStartRequest(
            final String to,
            final ConversationContentType type,
            final ConversationContent content,
            final String channelId
    ) {
        this.to = to;
        this.type = type;
        this.content = content;
        this.channelId = channelId;
    }

    public ConversationStartRequest() {
        //
    }

    public String getTo() {
        return to;
    }

    public void setTo(final String to) {
        this.to = to;
    }

    public ConversationContentType getType() {
        return type;
    }

    public void setType(final ConversationContentType type) {
        this.type = type;
    }

    public ConversationContent getContent() {
        return content;
    }

    public void setContent(final ConversationContent content) {
        this.content = content;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(final String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "ConversationStartRequest{" +
                "to='" + to + '\'' +
                ", type=" + type +
                ", content=" + content +
                ", channelId='" + channelId + '\'' +
                '}';
    }
}
