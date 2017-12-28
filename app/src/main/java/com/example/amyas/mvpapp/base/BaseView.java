package com.example.amyas.mvpapp.base;

import io.objectbox.BoxStore;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    BoxStore getBoxStore();
}
