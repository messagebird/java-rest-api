package com.messagebird.objects.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public enum TemplateMediaType {

    IMAGE("image"),
    DOCUMENT("document"),
    VIDEO("video"),
    TEXT("text"),
    CURRENCY("currency"),
    DATETIME("date_time"),
    PAYLOAD("payload"),
    EXPIRATION_TIME("expiration_time");

    private static final Map<String, TemplateMediaType> TYPE_MAP;

    static {
        Map<String, TemplateMediaType> map = new HashMap<>();
        for (TemplateMediaType templateMediaType : TemplateMediaType.values()) {
            map.put(templateMediaType.getType().toLowerCase(), templateMediaType);
        }
        TYPE_MAP = Collections.unmodifiableMap(map);
    }


    private final String type;

    TemplateMediaType(final String type) {
        this.type = type;
    }

    @JsonCreator
    public static TemplateMediaType forValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        return TYPE_MAP.get(value.toLowerCase());
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