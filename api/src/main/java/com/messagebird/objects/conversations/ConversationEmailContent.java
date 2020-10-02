package com.messagebird.objects.conversations;

public class ConversationEmailContent {
    private String html;
    private String text;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ConversationEmailContent{" +
                "html='" + html + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
