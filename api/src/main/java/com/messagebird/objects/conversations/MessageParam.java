package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public class MessageParam {

    private TemplateMediaType type;
    private String text;
    private String payload;
    private HSMCurrency currency;
    private String dateTime;
    private Media document;
    private Media image;
    private Media video;
    @JsonProperty("expiration_time")
    private String expirationTime;

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
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
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
        if (StringUtils.isBlank(dateTime)) {
            throw new IllegalArgumentException("dateTime cannot be null or empty");
        }
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

    public Media getVideo() { return video; }

    public void setVideo(Media video) { this.video = video; }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        if (StringUtils.isBlank(expirationTime)) {
            throw new IllegalArgumentException("expirationTime cannot be null or empty");
        }
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MessageParam{");
        sb.append("type=").append(type)
                .append(", text='").append(text).append('\'')
                .append(", payload='").append(payload).append('\'')
                .append(", currency=").append(currency)
                .append(", dateTime='").append(dateTime).append('\'')
                .append(", document=").append(document)
                .append(", image=").append(image)
                .append(", video=").append(video)
                .append(", expirationTime='").append(expirationTime).append('\'')
                .append('}');
        return sb.toString();
    }
}
