package com.messagebird.objects.conversations;

import java.util.List;
import java.util.Map;

public class ConversationContentEmail {
    private String id;
    private ConversationEmailRecipient from;
    private List<ConversationEmailRecipient> to;
    private String subject;
    private ConversationEmailContent content;
    private String replyTo;
    private String returnPath;
    private Map<String, String> headers;
    private ConversationEmailTracking tracking;
    private boolean performSubstitutions;
    private List<ConversationEmailAttachment> attachments;
    private List<ConversationEmailInlineImage> inlineImages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConversationEmailRecipient getFrom() {
        return from;
    }

    public void setFrom(ConversationEmailRecipient from) {
        this.from = from;
    }

    public List<ConversationEmailRecipient> getTo() {
        return to;
    }

    public void setTo(List<ConversationEmailRecipient> to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ConversationEmailContent getContent() {
        return content;
    }

    public void setContent(ConversationEmailContent content) {
        this.content = content;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getReturnPath() {
        return returnPath;
    }

    public void setReturnPath(String returnPath) {
        this.returnPath = returnPath;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public ConversationEmailTracking getTracking() {
        return tracking;
    }

    public void setTracking(ConversationEmailTracking tracking) {
        this.tracking = tracking;
    }

    public boolean isPerformSubstitutions() {
        return performSubstitutions;
    }

    public void setPerformSubstitutions(boolean performSubstitutions) {
        this.performSubstitutions = performSubstitutions;
    }

    public List<ConversationEmailAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ConversationEmailAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<ConversationEmailInlineImage> getInlineImages() {
        return inlineImages;
    }

    public void setInlineImages(List<ConversationEmailInlineImage> inlineImages) {
        this.inlineImages = inlineImages;
    }

    @Override
    public String toString() {
        return "ConversationContentEmail{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", subject='" + subject + '\'' +
                ", content=" + content +
                ", replyTo='" + replyTo + '\'' +
                ", returnPath='" + returnPath + '\'' +
                ", headers=" + headers +
                ", tracking=" + tracking +
                ", performSubstitutions=" + performSubstitutions +
                ", attachments=" + attachments +
                ", inlineImages=" + inlineImages +
                '}';
    }
}
