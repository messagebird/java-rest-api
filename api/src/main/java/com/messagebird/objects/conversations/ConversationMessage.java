package com.messagebird.objects.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * Response object that represents a conversation's message. Messages can be
 * sent to and received from a customer and are automatically threaded in a
 * conversation.
 */
@Getter
@Setter
@ToString
public class ConversationMessage {

    private String id;
    private String conversationId;
    private String channelId;
    private ConversationMessageDirection direction;
    private ConversationMessageStatus status;
    private ConversationContentType type;
    private ConversationContent content;
    private Date createdDatetime;
    private Date updatedDatetime;
    private Map<String, Object> source;
    private ConversationMessageTag tag;
    /**
     * See: {@link ConversationPlatformConstants}
     */
    private String platform;
}
