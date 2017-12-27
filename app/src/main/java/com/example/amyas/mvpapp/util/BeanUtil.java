package com.example.amyas.mvpapp.util;

import javax.annotation.Nullable;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public class BeanUtil {

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.length() == 0;
    }
}
