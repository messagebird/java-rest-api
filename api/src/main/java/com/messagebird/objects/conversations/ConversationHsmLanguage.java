package com.messagebird.objects.conversations;

import lombok.*;

/**
 * Language options used for localization. Code can be in language or
 * language_locale formats (e.g. "en" and "en_US"). The policy indicates
 * whether the message is delivered exactly in the locale requested
 * (deterministic). If the fallback policy is used, WhatsApp will attempt to
 * deliver the HSM in the user's device language. If that is not found, the
 * fallback is used.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConversationHsmLanguage {

    private String code;
    private ConversationHsmLanguagePolicy policy;

}
