package com.example.amyas.mvpapp.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * author: Amyas
 * date: 2017/12/26
 */

public class ActivityUtil {
    public static void addFragmentToActivity(FragmentManager manager, Fragment fragment, int resId){
        FragmentTransaction t = manager.beginTransaction();
        t.replace(resId, fragment);
        t.commit();
    }
}
