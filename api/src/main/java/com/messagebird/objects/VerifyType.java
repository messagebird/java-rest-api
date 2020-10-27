package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Determines which type the message will be.
 */

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum VerifyType {

    FLASH("flash"),
    SMS("sms"),
    TTS("tts");

    final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
