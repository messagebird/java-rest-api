package com.messagebird.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum PhoneNumberSearchPattern {
    START("start"),
    ANYWHERE("anywhere"),
    END("end");

    @Getter
    private String type;

    @Override
    public String toString(){
        return type;
    }
}