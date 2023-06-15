package com.messagebird.objects.integrations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HSMOTPButtonType {
    ONE_TAP("ONE_TAP"),
    COPY_CODE("COPY_CODE");

    private final String type;

    HSMOTPButtonType(String type) {
        this.type = type;
    }
    @JsonCreator
    public static HSMOTPButtonType forValue(String value) {
        for (HSMOTPButtonType OTPButtonType : HSMOTPButtonType.values()) {
            if (OTPButtonType.getType().equals(value)) {
                return OTPButtonType;
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
