package com.messagebird.objects;

public enum PhoneNumberSearchPattern {
    START("start"),
    ANYWHERE("anywhere"),
    END("end");

    private String type;

    PhoneNumberSearchPattern(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}