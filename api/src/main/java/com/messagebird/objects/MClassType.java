package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * MClass type
 *
 * Created by rvt on 1/7/15.
 */
public enum MClassType {
    flash(0),
    normal(1);

    final Integer value;

    MClassType(Integer type) {
        this.value = type;
    }

    @Override
    public String toString() {
        return "MClass{" +
                "value=" + value +
                '}';
    }

    @JsonValue
    public Integer toJson() {
        return getValue();
    }

    @JsonCreator
    public static MClassType forValue(String value) {
        if ("0".equals(value)) {
            return flash;
        }
        return normal;
    }


    public Integer getValue() {
        return value;
    }

}
