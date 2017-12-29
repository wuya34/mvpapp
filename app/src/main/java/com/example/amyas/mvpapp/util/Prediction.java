package com.example.amyas.mvpapp.util;

import javax.annotation.Nullable;

/**
 * author: Amyas
 * date: 2017/12/28
 */

public class Prediction {
    public static <T> T checkNotNull(T reference) {
        if(reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.length() == 0;
    }
}
