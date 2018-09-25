package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Determines which type the message will be.
 */
public enum VerifyType {

    FLASH("flash"),
    SMS("sms"),
    TTS("tts");

    final String value;

    VerifyType(String type) {
        this.value = type;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "VerifyType{" +
                "value='" + value + '\'' +
                '}';
    }
}
