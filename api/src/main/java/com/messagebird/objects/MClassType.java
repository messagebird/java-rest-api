package com.messagebird.objects;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * MClass type
 *
 * Created by rvt on 1/7/15.
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum MClassType {
    flash(0),
    normal(1);

    @Getter
    final Integer value;

    @JsonValue
    public Integer toJson() {
        return getValue();
    }

    public static MClassType forValue(String value) {
        if ("0".equals(value)) {
            return flash;
        }
        return normal;
    }

}
