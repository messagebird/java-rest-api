package com.messagebird.objects.conversations;

/**
 * Language options used for localization. Code can be in language or
 * language_locale formats (e.g. "en" and "en_US"). The policy indicates
 * whether the message is delivered exactly in the locale requested
 * (deterministic). If the fallback policy is used, WhatsApp will attempt to
 * deliver the HSM in the user's device language. If that is not found, the
 * fallback is used.
 */
public class ConversationHsmLanguage {

    private String code;
    private ConversationHsmLanguagePolicy policy;

    public ConversationHsmLanguage(final String code, final ConversationHsmLanguagePolicy policy) {
        this.code = code;
        this.policy = policy;
    }

    public ConversationHsmLanguage() {
        //
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public ConversationHsmLanguagePolicy getPolicy() {
        return policy;
    }

    public void setPolicy(final ConversationHsmLanguagePolicy policy) {
        this.policy = policy;
    }

    @Override
    public String toString() {
        return "ConversationHsmLanguage{" +
                "code='" + code + '\'' +
                ", policy=" + policy +
                '}';
    }
}
