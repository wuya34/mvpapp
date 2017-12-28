package com.example.amyas.mvpapp.activity.presenter.contract;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.amyas.mvpapp.base.BaseActivity;
import com.example.amyas.mvpapp.base.BasePresenter;
import com.example.amyas.mvpapp.base.BaseView;

import io.objectbox.BoxStore;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public interface AddEditTaskContract {
    interface view extends BaseView<Presenter>{
        void showSavedTaskBean();
        void showEmptyTaskError();
    }

    interface Presenter extends BasePresenter{
        void saveTaskBean(String title, String description);

    }
}
