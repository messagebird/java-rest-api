package com.messagebird.objects.conversations;

import lombok.*;

import java.util.Map;

/**
 * Request object used for starting a conversation.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class ConversationStartRequest {

    private String to;
    private ConversationContentType type;
    private ConversationContent content;
    private Map<String, Object> source;
    private ConversationMessageTag tag;
    private String channelId;
    private String reportUrl;

}
