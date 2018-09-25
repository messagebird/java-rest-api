package com.messagebird.objects;

import java.util.Date;

/**
 * Complete Contact object. To create and update contacts, use ContactRequest.
 */
public class Contact {

    private String id;
    private String href;
    private String msisdn;
    private String firstName;
    private String lastName;
    private GroupReference groups;
    private MessageReference messages;
    private Date createdDatetime;
    private Date updatedDatetime;
    private CustomDetails customDetails;

    public String getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CustomDetails getCustomDetails() {
        return customDetails;
    }

    public GroupReference getGroups() {
        return groups;
    }

    public MessageReference getMessages() {
        return messages;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCustomDetails(CustomDetails customDetails) {
        this.customDetails = customDetails;
    }

    public void setGroups(GroupReference groups) {
        this.groups = groups;
    }

    public void setMessages(MessageReference messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", customDetails=" + customDetails +
                ", groups=" + groups +
                ", messages=" + messages +
                ", createdDatetime=" + createdDatetime +
                ", updatedDatetime=" + updatedDatetime +
                '}';
    }
}
