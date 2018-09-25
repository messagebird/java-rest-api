package com.messagebird.objects.conversations;

import java.util.Date;

/**
 * Response object that represents a conversation's message. Messages can be
 * sent to and received from a customer and are automatically threaded in a
 * conversation.
 */
public class ConversationMessage {

    private String id;
    private String conversationId;
    private String channelId;
    private ConversationMessageDirection direction;
    private ConversationMessageStatus status;
    private ConversationContentType type;
    private ConversationContent content;
    private Date createdDatetime;
    private Date updatedDatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public ConversationMessageDirection getDirection() {
        return direction;
    }

    public void setDirection(ConversationMessageDirection direction) {
        this.direction = direction;
    }

    public ConversationMessageStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationMessageStatus status) {
        this.status = status;
    }

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

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(Date updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    @Override
    public String toString() {
        return "ConversationMessage{" +
                "id='" + id + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", direction=" + direction +
                ", status=" + status +
                ", type=" + type +
                ", content=" + content +
                ", createdDatetime=" + createdDatetime +
                ", updatedDatetime=" + updatedDatetime +
                '}';
    }
}
