package com.messagebird.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum PhoneNumberType {
    LANDLINE("landline"),
    MOBILE("mobile"),
    PREMIUM_RATE("premium_rate");

    private String type;

    @Override
    public String toString(){
        return type;
    }
}