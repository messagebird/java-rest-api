package com.messagebird.objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by faizan on 09/12/15.
 */
public class Verify implements Serializable {

    private String id;
    private String href;
    private String recipient;
    private Messages messages;
    private String status;
    private Date createdDatetime;
    private Date validUntilDatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getValidUntilDatetime() {
        return validUntilDatetime;
    }

    public void setValidUntilDatetime(Date validUntilDatetime) {
        this.validUntilDatetime = validUntilDatetime;
    }

    public String toString() {
        return "Verify {" + " " +
                "id=" + this.id + " " +
                "href=" + this.href + " " +
                "recipient=" + this.recipient + " " +
                "messages=" + this.messages + " " +
                "status=" + this.status + " " +
                "createdDatetime=" + this.createdDatetime + " " +
                "validUntilDatetime=" + this.validUntilDatetime+ " " +
                "}";
    }
}
