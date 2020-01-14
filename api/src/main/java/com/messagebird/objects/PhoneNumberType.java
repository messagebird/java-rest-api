package com.messagebird.objects;

public enum PhoneNumberType {
    LANDLINE("landline"),
    MOBILE("mobile"),
    PREMIUM_RATE("premium_rate");

    private String type;

    PhoneNumberType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}