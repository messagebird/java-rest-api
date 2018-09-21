package com.messagebird.objects.conversations;

import java.util.Date;

/**
 * Response object for the Channel type. It represents a platform that messages
 * can be sent and received on. A conversation can take place over multiple
 * channels simultaneously.
 */
public class ConversationChannel {

    private String id;
    private String name;
    private ConversationChannelStatus status;
    private Date createdDatetime;
    private Date updatedDatetime;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ConversationChannelStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationChannelStatus status) {
        this.status = status;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(final Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(final Date updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    @Override
    public String toString() {
        return "ConversationChannel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createdDatetime=" + createdDatetime +
                ", updatedDatetime=" + updatedDatetime +
                '}';
    }
}
