package com.messagebird.objects.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConversationEmailAttachment {
    private String id;
    private String name;
    private String type;
    private String URL;
    private String length;

}
