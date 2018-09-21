package com.messagebird.objects.conversations;

/**
 * ConversationContent wraps actual content. The field that should be set here
 * is indicated by ConversationContentType.
 */
public class ConversationContent {

    private ConversationContentMedia audio;
    private ConversationContentMedia file;
    private ConversationContentHsm hsm;
    private ConversationContentMedia image;
    private ConversationContentLocation location;
    private String text;
    private ConversationContentMedia video;

    public ConversationContentMedia getAudio() {
        return audio;
    }

    public void setAudio(ConversationContentMedia audio) {
        this.audio = audio;
    }

    public ConversationContentMedia getFile() {
        return file;
    }

    public void setFile(ConversationContentMedia file) {
        this.file = file;
    }

    public ConversationContentHsm getHsm() {
        return hsm;
    }

    public void setHsm(ConversationContentHsm hsm) {
        this.hsm = hsm;
    }

    public ConversationContentMedia getImage() {
        return image;
    }

    public void setImage(ConversationContentMedia image) {
        this.image = image;
    }

    public ConversationContentLocation getLocation() {
        return location;
    }

    public void setLocation(ConversationContentLocation location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ConversationContentMedia getVideo() {
        return video;
    }

    public void setVideo(ConversationContentMedia video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "ConversationContent{" +
                "audio=" + audio +
                ", file=" + file +
                ", hsm=" + hsm +
                ", image=" + image +
                ", location=" + location +
                ", text='" + text + '\'' +
                ", video=" + video +
                '}';
    }
}
