package com.messagebird.objects.conversations;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConversationFallbackOption {
    private String from;
    private String after;
}
