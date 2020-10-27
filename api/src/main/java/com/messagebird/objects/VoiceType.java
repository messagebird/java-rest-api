package com.messagebird.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Voice message types
 * <p/>
 * Created by rvt on 1/7/15.
 */

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum VoiceType {
    male("male"),
    female("female");

    @Getter
    final String value;

    @Override
    public String toString() {
        return value;
    }
}
