package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Determines the lanuage policy for a HSM.
 */

@AllArgsConstructor(access = AccessLevel.PACKAGE)
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

    @Getter
    private final String policy;

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

    @Override
    public String toString() {
        return policy;
    }
}
