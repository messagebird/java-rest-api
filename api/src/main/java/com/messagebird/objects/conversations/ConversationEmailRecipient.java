package com.messagebird.objects.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class ConversationEmailRecipient {
    private String address;
    private String name;
    private Map<String, String> variables;

}
