package com.example.amyas.mvpapp.activity.presenter.contract;

import com.example.amyas.mvpapp.base.BasePresenter;
import com.example.amyas.mvpapp.base.BaseView;

import io.objectbox.BoxStore;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public interface TaskContract {

    interface view extends BaseView<Presenter>{

        void showLoadingIndicator(boolean active);
        BoxStore getBoxStore();

    }

    interface Presenter extends BasePresenter{
        void loadTask(boolean forceUpdate);
        void loadTask(boolean forceUpdate, boolean showLoadingUi);

    }
}
