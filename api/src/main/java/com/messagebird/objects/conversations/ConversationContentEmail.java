package com.messagebird.objects.conversations;

import com.messagebird.objects.MessageResponse;

import java.util.List;
import java.util.Map;

public class ConversationContentEmail {
    private String id;
    private ConversationEmailRecipient from;
    private List<ConversationEmailRecipient> to;
    private String subject;
    private ConversationEmailContent content;
    private String replyTo;
    private String  returnPath;
    private Map<String, String> headers;
    private ConversationEmailTracking tracking;
    private boolean performSubstitutions;
    private List<ConversationEmailAttachment> attachments;
    private List<ConversationEmailInlineImage> inlineImages;
}
