package com.messagebird.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class MessageReference {
    @Setter
    private String href;

    @Setter
    private int totalCount;

    private String lastMessageId;

}
