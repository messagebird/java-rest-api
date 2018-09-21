package com.messagebird.objects;

import java.util.Date;

/**
 * Complete Group object. To create and update groups, use GroupRequest.
 */
public class Group {

    private String id;
    private String href;
    private String name;
    private ContactReference contacts;
    private Date createdDatetime;
    private Date updatedDatetime;

    public String getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }

    public ContactReference getContacts() {
        return contacts;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", name='" + name + '\'' +
                ", contacts=" + contacts +
                ", createdDatetime=" + createdDatetime +
                ", updatedDatetime=" + updatedDatetime +
                '}';
    }
}
