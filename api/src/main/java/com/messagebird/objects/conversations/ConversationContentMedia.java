package com.messagebird.objects.conversations;

/**
 * Contains media referenced by a URL. Supported types are audio, file, image
 * and video.
 */
public class ConversationContentMedia {

    private String url;
    private String caption;

    public ConversationContentMedia(final String url, final String caption) {
        this.url = url;
        this.caption = caption;
    }

    public ConversationContentMedia(final String url) {
        this.url = url;
    }

    public ConversationContentMedia() {
        //
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return "ConversationContentMedia{" +
                "url='" + url + '\'' +
                ", caption='" + caption + '\'' +
                '}';
    }
}
