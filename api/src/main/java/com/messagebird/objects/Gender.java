package com.messagebird.objects;

/**
 * Created by faizan on 09/12/15.
 */
public enum Gender {

    MALE("male"),
    FEMALE("female");

    private String code;

    Gender(String code) {
        this.code = code;
    }

    public String toString() {
        return this.code;
    }
}

