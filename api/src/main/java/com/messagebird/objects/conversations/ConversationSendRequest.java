package com.messagebird.objects.conversations;

public class ConversationSendRequest {
    private String to;
    private ConversationContentType type;
    private ConversationContent content;
    private String from;
    private String reportUrl;
    private ConversationFallbackOption fallback;

    public ConversationSendRequest(String to, ConversationContentType type, ConversationContent content, String from, String reportUrl, ConversationFallbackOption fallback) {
        this.to = to;
        this.type = type;
        this.content = content;
        this.from = from;
        this.reportUrl = reportUrl;
        this.fallback = fallback;
    }

    public ConversationSendRequest() {
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ConversationContentType getType() {
        return type;
    }

    public void setType(ConversationContentType type) {
        this.type = type;
    }

    public ConversationContent getContent() {
        return content;
    }

    public void setContent(ConversationContent content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public ConversationFallbackOption getFallback() {
        return fallback;
    }

    public void setFallback(ConversationFallbackOption fallback) {
        this.fallback = fallback;
    }

    @Override
    public String toString() {
        return "ConversationSendRequest{" +
                "to='" + to + '\'' +
                ", type=" + type +
                ", content=" + content +
                ", from='" + from + '\'' +
                ", reportUrl='" + reportUrl + '\'' +
                ", fallback=" + fallback +
                '}';
    }
}


