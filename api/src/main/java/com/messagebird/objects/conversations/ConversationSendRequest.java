package com.messagebird.objects.conversations;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConversationSendRequest {
    private String to;
    private ConversationContentType type;
    private ConversationContent content;
    private String from;
    private String reportUrl;
    private ConversationFallbackOption fallback;
    private Map<String, Object> source;
    private ConversationMessageTag tag;

}


