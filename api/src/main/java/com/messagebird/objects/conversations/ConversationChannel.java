package com.messagebird.objects.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Response object for the Channel type. It represents a platform that messages
 * can be sent and received on. A conversation can take place over multiple
 * channels simultaneously.
 */
@Getter
@Setter
@ToString
public class ConversationChannel {

    private String id;
    private String name;
    // See: ConversationPlatformConstants
    private String platformId;
    private ConversationChannelStatus status;
    private Date createdDatetime;
    private Date updatedDatetime;

}
