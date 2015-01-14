package com.messagebird.objects;

/**
 * Voice message types
 * <p/>
 * Created by rvt on 1/7/15.
 */
public enum VoiceType {
    male("male"),
    female("female");

    final String value;

    VoiceType(String type) {
        this.value = type;
    }

    @Override
    public String toString() {
        return "Voice{" +
                "value='" + value + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

}
