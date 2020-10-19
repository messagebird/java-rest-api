package com.messagebird.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by faizan on 09/12/15.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum Gender {

    MALE("male"),
    FEMALE("female");

    @Getter
    private String code;

    @Override
    public String toString() {
        return code;
    }
}

