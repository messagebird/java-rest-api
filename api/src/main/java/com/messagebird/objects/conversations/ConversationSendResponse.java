package com.messagebird.objects.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConversationSendResponse {
    private String id;  //messageID
    private String status;
    private FallbackOptionResponse fallback;

    @Getter
    @Setter
    @ToString
    public static class FallbackOptionResponse{
         private String id;
     }
}
