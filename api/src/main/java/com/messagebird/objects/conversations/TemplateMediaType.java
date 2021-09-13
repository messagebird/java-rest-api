package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TemplateMediaType {

    IMAGE("image"),
    DOCUMENT("document"),
    TEXT("text"),
    CURRENCY("currency"),
    DATETIME("date_time"),
    PAYLOAD("payload");

    private final String type;

    TemplateMediaType(final String type) {
        this.type = type;
    }

    @JsonCreator
    public static TemplateMediaType forValue(String value) {
        for (TemplateMediaType  templateMediaType: TemplateMediaType.values()) {
            if (templateMediaType.getType().equals(value)) {
                return templateMediaType;
            }
        }

        return null;
    }

    @JsonValue
    public String toJson() {
        return getType();
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return getType();
    }

}