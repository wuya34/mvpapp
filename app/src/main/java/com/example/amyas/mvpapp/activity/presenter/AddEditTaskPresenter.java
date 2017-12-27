package com.example.amyas.mvpapp.activity.presenter;


import com.example.amyas.mvpapp.activity.presenter.contract.AddEditTaskContract;
import com.example.amyas.mvpapp.bean.TaskBean;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public class AddEditTaskPresenter implements AddEditTaskContract.Presenter {
    private AddEditTaskContract.view mFragment;


    public AddEditTaskPresenter(AddEditTaskContract.view fragment){
        mFragment = fragment;
        mFragment.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void saveTaskBean(String title, String description) {
        TaskBean taskBean = new TaskBean(title, description);
        if (taskBean.isEmpty()){
            mFragment.showEmptyTaskError();
        }else {
            Box<TaskBean> taskBeanBox = mFragment.getBoxStore().boxFor(TaskBean.class);
            taskBeanBox.put(taskBean);
            mFragment.showSavedTaskBean();
        }


    }
}
