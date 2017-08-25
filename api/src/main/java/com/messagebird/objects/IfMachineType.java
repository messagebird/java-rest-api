package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * IfMachine class for Voice Messages
 * Created by rvt on 1/7/15.
 */
public enum IfMachineType {
    cont("continue"),
    delay("delay"),
    hangup("hangup");

    final String value;

    IfMachineType(String type) {
        this.value = type;
    }

    @Override
    public String toString() {
        return "IfMachine{" +
                "value='" + value + '\'' +
                '}';
    }

    @JsonValue
    public String toJson() {
        return getValue();
    }

    @JsonCreator
    public static IfMachineType forValue(String value) {
        if ("continue".equals(value)) {
            return cont;
        } else if ("delay".equals(value)) {
            return delay;
        } else if ("hangup".equals(value)) {
            return hangup;
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
