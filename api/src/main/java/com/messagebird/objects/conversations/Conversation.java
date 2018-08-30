package com.messagebird.objects.conversations;

import com.messagebird.objects.MessageReference;

import java.util.Date;
import java.util.List;

/**
 * Response object for the Conversation type.
 */
public class Conversation {

    private String id;
    private String contactId;
    private ConversationContact contact;
    private List<ConversationChannel> channels;
    private MessageReference messages;
    private ConversationStatus status;
    private Date createdDatetime;
    private Date updatedDatetime;
    private Date lastReceivedDatetime;
    private String lastUsedChannelId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public ConversationContact getContact() {
        return contact;
    }

    public void setContact(ConversationContact contact) {
        this.contact = contact;
    }

    public List<ConversationChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<ConversationChannel> channels) {
        this.channels = channels;
    }

    public MessageReference getMessages() {
        return messages;
    }

    public void setMessages(MessageReference messages) {
        this.messages = messages;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
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

    public Date getLastReceivedDatetime() {
        return lastReceivedDatetime;
    }

    public void setLastReceivedDatetime(Date lastReceivedDatetime) {
        this.lastReceivedDatetime = lastReceivedDatetime;
    }

    public String getLastUsedChannelId() {
        return lastUsedChannelId;
    }

    public void setLastUsedChannelId(String lastUsedChannelId) {
        this.lastUsedChannelId = lastUsedChannelId;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + id + '\'' +
                ", contactId='" + contactId + '\'' +
                ", contact=" + contact +
                ", channels=" + channels +
                ", messages=" + messages +
                ", status=" + status +
                ", createdDatetime=" + createdDatetime +
                ", updatedDatetime=" + updatedDatetime +
                ", lastReceivedDatetime=" + lastReceivedDatetime +
                ", lastUsedChannelId='" + lastUsedChannelId + '\'' +
                '}';
    }
}
