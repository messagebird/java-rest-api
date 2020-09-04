package com.messagebird.objects.conversations;

public class ConversationFallbackOption {
    private String from;
    private String after;

    public ConversationFallbackOption() {
    }

    public ConversationFallbackOption(String from, String after) {
        this.from = from;
        this.after = after;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    @Override
    public String toString() {
        return "ConversationFallbackOption{" +
                "from='" + from + '\'' +
                ", after='" + after + '\'' +
                '}';
    }
}
