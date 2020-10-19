package com.messagebird.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Complete Contact object. To create and update contacts, use ContactRequest.
 */
@Getter
@Setter
@ToString
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

}
