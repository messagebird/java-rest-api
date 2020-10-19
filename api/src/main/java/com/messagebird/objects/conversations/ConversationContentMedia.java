package com.messagebird.objects.conversations;

import lombok.*;

/**
 * Contains media referenced by a URL. Supported types are audio, file, image
 * and video.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConversationContentMedia {

    private String url;
    private String caption;

    public ConversationContentMedia(final String url) {
        this.url = url;
    }

}
