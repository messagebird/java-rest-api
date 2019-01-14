package com.messagebird.util;

import java.util.Scanner;

public class Resources {

    public static String readResourceText(String path) {
        return new Scanner(
            Resources.class.getResourceAsStream(path), "UTF-8"
        )
            .useDelimiter("\\A")
            .next();
    }
}
