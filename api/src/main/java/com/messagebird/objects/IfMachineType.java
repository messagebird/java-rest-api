package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * IfMachine class for Voice Messages
 * Created by rvt on 1/7/15.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum IfMachineType {
    cont("continue"),
    delay("delay"),
    hangup("hangup");

    @Getter
    final String value;

    @JsonValue
    public String toJson() {
        return value;
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
    @Override
    public String toString() {
        return value;
    }
}
