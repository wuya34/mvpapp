package com.example.amyas.mvpapp.activity.presenter;

import com.example.amyas.mvpapp.activity.presenter.contract.TaskDetailContract;
import com.example.amyas.mvpapp.bean.TaskBean;
import com.example.amyas.mvpapp.bean.TaskBean_;
import com.example.amyas.mvpapp.util.Prediction;

import io.objectbox.Box;

/**
 * author: Amyas
 * date: 2017/12/29
 */

public class TaskDetailPresenter implements TaskDetailContract.Presenter{

    private TaskDetailContract.view mFragment;
    private String taskId;

    public TaskDetailPresenter(String taskId, TaskDetailContract.view fragment){
        mFragment = fragment;
        mFragment.setPresenter(this);
        this.taskId = taskId;
    }


    public void start() {
        loadTaskBean();
    }

    @Override
    public void deleteTaskBean(String taskId) {
        long id = Long.valueOf(taskId);
        mFragment.getBoxStore().boxFor(TaskBean.class).remove(id);
        mFragment.showTaskBeanDeleted();
    }

    @Override
    public void editTaskBean(String taskId) {
        mFragment.startEditTaskActivity(taskId);

    }

    @Override
    public void completeTaskBean() {
        long id = Long.valueOf(taskId);
        Box<TaskBean> boxFor = mFragment.getBoxStore().boxFor(TaskBean.class);
        TaskBean taskBean = boxFor
                .query()
                .equal(TaskBean_.__ID_PROPERTY, id)
                .build()
                .findUnique();

        taskBean.setCompleted(true);
        boxFor.put(taskBean);

        mFragment.showTaskComplete();
    }

    @Override
    public void activeTaskBean() {
        long id = Long.valueOf(taskId);
        Box<TaskBean> boxFor = mFragment.getBoxStore().boxFor(TaskBean.class);
        TaskBean taskBean = boxFor
                .query()
                .equal(TaskBean_.__ID_PROPERTY, id)
                .build()
                .findUnique();

        taskBean.setCompleted(false);
        boxFor.put(taskBean);

        mFragment.showTaskActive();
    }


    private void loadTaskBean() {
        long id = Long.valueOf(taskId);
        TaskBean taskBean = mFragment.getBoxStore().boxFor(TaskBean.class)
                .query()
                .equal(TaskBean_.__ID_PROPERTY, id)
                .build()
                .findUnique();

        if (taskBean.isEmpty()){
            mFragment.showMissingTaskBean();
        }else {
            showTask(taskBean);
        }
    }

    private void showTask(TaskBean taskBean){
        mFragment.showIsComplete(taskBean.isCompleted());
        if (Prediction.isNullOrEmpty(taskBean.getTitle())){
            mFragment.hideTaskBeanTitle();
            mFragment.showTaskBeanDescription(taskBean.getDetail());
        }else if (Prediction.isNullOrEmpty(taskBean.getDetail())){
            mFragment.showTaskBeanTitle(taskBean.getTitle());
            mFragment.hideTaskBeanDescription();
        }else {
            mFragment.showTaskBeanTitle(taskBean.getTitle());
            mFragment.showTaskBeanDescription(taskBean.getDetail());
        }
    }
}
