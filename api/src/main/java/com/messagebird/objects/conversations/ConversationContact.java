package com.messagebird.objects.conversations;

import java.util.Date;
import java.util.Map;

/**
 * Response object for the ConversationContact type. Represents an end-user you
 * are communicating with.
 */
public class ConversationContact {

    private String id;
    private String href;
    private String msisdn;
    private String firstName;
    private String lastName;
    private Map<String, Object> customDetails;
    private Date createdDatetime;
    private Date updatedDatetime;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(final String href) {
        this.href = href;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(final String msisdn) {
        this.msisdn = msisdn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Map<String, Object> getCustomDetails() {
        return customDetails;
    }

    public void setCustomDetails(final Map<String, Object> customDetails) {
        this.customDetails = customDetails;
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
        return "ConversationContact{" +
                "id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", customDetails=" + customDetails +
                ", createdDatetime=" + createdDatetime +
                ", updatedDatetime=" + updatedDatetime +
                '}';
    }
}
