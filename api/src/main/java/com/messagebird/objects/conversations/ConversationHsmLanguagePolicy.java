package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Determines the lanuage policy for a HSM.
 */
public enum ConversationHsmLanguagePolicy {

    /**
     * Messages are delivered in exactly the language/locale requested.
     */
    DETERMINISTIC("deterministic"),

    /**
     * Messages are delivered in the user's device language. If that is not
     * found, the requested locale is used.
     */
    FALLBACK("fallback");

    private final String policy;

    ConversationHsmLanguagePolicy(final String policy) {
        this.policy = policy;
    }

    @JsonCreator
    public static ConversationHsmLanguagePolicy forValue(String value) {
        if ("deterministic".equals(value)) {
            return ConversationHsmLanguagePolicy.DETERMINISTIC;
        } else if ("fallback".equals(value)) {
            return ConversationHsmLanguagePolicy.FALLBACK;
        }

        return null;
    }

    @JsonValue
    public String toJson() {
        return policy;
    }

    public String getPolicy() {
        return policy;
    }

    @Override
    public String toString() {
        return getPolicy();
    }
}
