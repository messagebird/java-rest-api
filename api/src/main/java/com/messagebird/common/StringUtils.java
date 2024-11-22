package com.messagebird.common;

public class StringUtils {

    private StringUtils() {
        // static utility
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
