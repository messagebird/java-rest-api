package com.messagebird.objects.conversations;

/**
 * Contains media referenced by a URL. Supported types are audio, file, image
 * and video.
 */
public class ConversationContentMedia {

    private String url;

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

    @Override
    public String toString() {
        return "ConversationContentMedia{" +
                "url='" + url + '\'' +
                '}';
    }
}
