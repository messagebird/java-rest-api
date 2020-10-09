package com.messagebird.objects.conversations;

public class ConversationEmailInlineImage {
    private String id;
    private String name;
    private String type;
    private String URL;
    private int length;
    private String contentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "ConversationEmailInlineImage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", URL='" + URL + '\'' +
                ", length=" + length +
                ", contentId='" + contentId + '\'' +
                '}';
    }
}
