package com.messagebird.objects.conversations;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Highly structured message, only for WhatsApp channels. This is a
 * pre-approved, reusable message template required when messaging over
 * WhatsApp. It allows you to just send the required parameter values instead
 * of the full message. It also allows for localization of the message and
 * decreases the possibility of being blocked on the first contact as the
 * message is pre-approved by WhatsApp.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationContentHsm {

    private String namespace;
    private String templateName;
    private ConversationHsmLanguage language;
    private List<ConversationHsmLocalizableParameter> params;

    public void addParams(final ConversationHsmLocalizableParameter... params) {
        if (this.params == null) {
            this.params = new ArrayList<>(params.length);
        }

        Collections.addAll(this.params, params);
    }
}
