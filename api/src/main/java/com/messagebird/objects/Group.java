package com.messagebird.objects;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Complete Group object. To create and update groups, use GroupRequest.
 */
@Getter
@ToString
public class Group {

    private String id;
    private String href;
    private String name;
    private ContactReference contacts;
    private Date createdDatetime;
    private Date updatedDatetime;

}
