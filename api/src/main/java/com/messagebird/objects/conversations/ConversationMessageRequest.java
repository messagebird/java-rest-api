package com.messagebird.objects.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * Request object that is used to send new messages over a channel.
 */
@Getter
@Setter
@ToString
public class ConversationMessageRequest {

    private ConversationContentType type;
    private ConversationContent content;
    private String channelId;
    private String reportUrl;
    private Map<String, Object> source;

}
