package com.example.amyas.mvpapp.activity.presenter;

import com.example.amyas.mvpapp.activity.presenter.contract.TaskContract;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public class TaskPresenter implements TaskContract.Presenter{
    private boolean firstLoad = true;
    private TaskContract.view mFragment;

    public TaskPresenter(TaskContract.view fragment){
        mFragment = fragment;
    }

    public void start() {
        loadTask(true);
    }

    @Override
    public void loadTask(boolean forceUpdate) {
        loadTask(forceUpdate||firstLoad, true);
        firstLoad = false;
    }

    @Override
    public void loadTask(boolean forceUpdate, boolean showLoadingUi) {
        if (showLoadingUi){
            mFragment.showLoadingIndicator(true);
        }
        if (forceUpdate){

        }
    }
}
