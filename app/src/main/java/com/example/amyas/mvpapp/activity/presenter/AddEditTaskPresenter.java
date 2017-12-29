package com.example.amyas.mvpapp.activity.presenter;


import com.example.amyas.mvpapp.activity.presenter.contract.AddEditTaskContract;
import com.example.amyas.mvpapp.bean.TaskBean;
import com.example.amyas.mvpapp.bean.TaskBean_;
import com.example.amyas.mvpapp.util.Prediction;

import io.objectbox.Box;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public class AddEditTaskPresenter implements AddEditTaskContract.Presenter {
    private AddEditTaskContract.view mFragment;
    private String taskId;

    public AddEditTaskPresenter(AddEditTaskContract.view fragment, String taskId) {
        mFragment = fragment;
        mFragment.setPresenter(this);
        this.taskId = taskId;
    }

    @Override
    public void start() {
        if (Prediction.isNullOrEmpty(taskId)) {
            return;
        }
        loadTask();
    }

    private void loadTask() {
        long id = Long.valueOf(taskId);
        Box<TaskBean> boxFor = mFragment.getBoxStore().boxFor(TaskBean.class);
        TaskBean taskBean = boxFor.query()
                .equal(TaskBean_.id, id)
                .build()
                .findUnique();

        mFragment.showTaskTitle(taskBean.getTitle());
        mFragment.showTaskDescription(taskBean.getDetail());
    }

    @Override
    public void saveTaskBean(String title, String description) {
        if (Prediction.isNullOrEmpty(taskId)){
            createTask(title, description);
        }else {
            updateTask(title, description);
        }
    }

    private void createTask(String title, String description){
        TaskBean taskBean = new TaskBean();
        taskBean.setTitle(title);
        taskBean.setDetail(description);
        if (taskBean.isEmpty()) {
            mFragment.showEmptyTaskError();
        } else {
            // 保存数据
            mFragment.getBoxStore().boxFor(TaskBean.class).put(taskBean);
            mFragment.showSavedTaskBean();
        }
    }

    private void updateTask(String title, String description){
        long id = Long.valueOf(taskId);
        Box<TaskBean> boxFor = mFragment.getBoxStore().boxFor(TaskBean.class);
        TaskBean taskBean = boxFor
                .query()
                .equal(TaskBean_.id, id)
                .build()
                .findUnique();

        taskBean.setTitle(title);
        taskBean.setDetail(description);
        if (taskBean.isEmpty()) {
            mFragment.showEmptyTaskError();
        } else {
            // 保存数据
            mFragment.getBoxStore().boxFor(TaskBean.class).put(taskBean);
            mFragment.showSavedTaskBean();
        }
    }
}
