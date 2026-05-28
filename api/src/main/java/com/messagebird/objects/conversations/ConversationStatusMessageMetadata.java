package com.messagebird.objects.conversations;

/**
 * The {@code messageMetadata} block delivered inside status webhook payloads
 * (e.g. {@code statusSent}, {@code statusDelivered}). Reflects the original
 * message that triggered the status update.
 *
 * <p>This class is not produced by any SDK request — it is a standalone POJO
 * intended for consumers who deserialize incoming webhook payloads in their
 * own HTTP handlers. Use it via {@code ObjectMapper.readValue(body, ...)}.
 *
 * <p>Both {@code from} and {@code to} accept either a phone number or a
 * WhatsApp Business-Scoped User ID (BSUID, e.g. "US.13491208655302741918").
 * The BSUID is also available via {@code metadata.sender.userId}.
 */
public class ConversationStatusMessageMetadata {

    private String id;
    private String from;
    private String to;
    private String type;
    private ConversationContent content;
    private ConversationMessageMetadata metadata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ConversationContent getContent() {
        return content;
    }

    public void setContent(ConversationContent content) {
        this.content = content;
    }

    public ConversationMessageMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ConversationMessageMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "ConversationStatusMessageMetadata{" +
                "id='" + id + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type='" + type + '\'' +
                ", content=" + content +
                ", metadata=" + metadata +
                '}';
    }
}
