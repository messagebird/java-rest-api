package com.messagebird.objects.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * Response object for the ConversationContact type. Represents an end-user you
 * are communicating with.
 */
@Getter
@Setter
@ToString
public class ConversationContact {

    private String id;
    private String href;
    private String msisdn;
    private String firstName;
    private String lastName;
    private Map<String, Object> customDetails;
    private Date createdDatetime;
    private Date updatedDatetime;

}
