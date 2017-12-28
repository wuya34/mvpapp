package com.example.amyas.mvpapp.activity.presenter;


import android.support.v7.app.AppCompatActivity;

import com.example.amyas.mvpapp.activity.presenter.contract.AddEditTaskContract;
import com.example.amyas.mvpapp.base.BaseActivity;
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
        TaskBean taskBean = new TaskBean();
        taskBean.setTitle(title);
        taskBean.setDetail(description);
        if (taskBean.isEmpty()){
            mFragment.showEmptyTaskError();
        }else {
            // 保存数据
            mFragment.getBoxStore().boxFor(TaskBean.class).put(taskBean);
            mFragment.showSavedTaskBean();
        }


    }
}
