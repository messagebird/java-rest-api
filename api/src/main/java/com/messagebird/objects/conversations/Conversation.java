package com.messagebird.objects.conversations;

import com.messagebird.objects.MessageReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Response object for the Conversation type.
 */
@Getter
@Setter
@ToString
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

}
