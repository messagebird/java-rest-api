package com.messagebird.objects;

public enum PhoneNumberFeature {
    
    SMS("sms"),
    MMS("mms"),
    VOICE("voice");

    private String type;

    PhoneNumberFeature(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}