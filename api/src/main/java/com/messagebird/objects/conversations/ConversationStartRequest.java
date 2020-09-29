package com.messagebird.objects.conversations;

import java.util.Map;

/**
 * Request object used for starting a conversation.
 */
public class ConversationStartRequest {

    private String to;
    private ConversationContentType type;
    private ConversationContent content;
    private Map<String, Object> source;
    private ConversationMessageTag tag;
    private String channelId;
    private String reportUrl;

    public ConversationStartRequest(
            final String to,
            final ConversationContentType type,
            final ConversationContent content,
            final String channelId,
            final Map<String, Object> source,
            final ConversationMessageTag tag
    ) {
        this.to = to;
        this.type = type;
        this.content = content;
        this.channelId = channelId;
        this.source = source;
        this.tag = tag;
    }

    public ConversationStartRequest() {
        //
    }

    public String getTo() {
        return to;
    }

    public void setTo(final String to) {
        this.to = to;
    }

    public ConversationContentType getType() {
        return type;
    }

    public void setType(final ConversationContentType type) {
        this.type = type;
    }

    public ConversationContent getContent() {
        return content;
    }

    public void setContent(final ConversationContent content) {
        this.content = content;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(final String channelId) {
        this.channelId = channelId;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(final String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }

    public ConversationMessageTag getTag() {
        return tag;
    }

    public void setTag(ConversationMessageTag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ConversationStartRequest{" +
                "to='" + to + '\'' +
                ", type=" + type +
                ", content=" + content +
                ", source=" + source +
                ", tag=" + tag +
                ", channelId='" + channelId + '\'' +
                ", reportUrl='" + reportUrl + '\'' +
                '}';
    }
}
