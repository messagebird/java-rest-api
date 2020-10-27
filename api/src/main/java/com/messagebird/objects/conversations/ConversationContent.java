package com.messagebird.objects.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ConversationContent wraps actual content. The field that should be set here
 * is indicated by ConversationContentType.
 */
@Getter
@Setter
@ToString
public class ConversationContent {

    private ConversationContentMedia audio;
    private ConversationContentMedia file;
    private ConversationContentHsm hsm;
    private ConversationContentMedia image;
    private ConversationContentLocation location;
    private ConversationContentEmail email;
    private String text;
    private ConversationContentMedia video;

}
