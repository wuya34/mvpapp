package com.example.amyas.mvpapp;

import android.app.Application;

import com.example.amyas.mvpapp.bean.MyObjectBox;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import io.objectbox.BoxStore;

/**
 * author: Amyas
 * date: 2017/12/26
 */

public class MyApplication extends Application {

    private BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
    }

    public BoxStore getBoxStore(){
        return mBoxStore;
    }
}
