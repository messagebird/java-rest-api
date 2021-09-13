package com.messagebird.objects.conversations;

public class MessageParam {

    private TemplateMediaType type;
    private String text;
    private String payload;
    private HSMCurrency currency;
    private String dateTime;
    private Media document;
    private Media image;

    public TemplateMediaType getType() {
        return type;
    }

    public void setType(TemplateMediaType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public HSMCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(HSMCurrency currency) {
        this.currency = currency;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Media getDocument() {
        return document;
    }

    public void setDocument(Media document) {
        this.document = document;
    }

    public Media getImage() {
        return image;
    }

    public void setImage(Media image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "MessageParam{" +
                "type=" + type +
                ", text='" + text + '\'' +
                ", payload='" + payload + '\'' +
                ", currency=" + currency +
                ", dateTime='" + dateTime + '\'' +
                ", document=" + document +
                ", image=" + image +
                '}';
    }
}
