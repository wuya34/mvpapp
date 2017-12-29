package com.example.amyas.mvpapp.activity.presenter;

import android.app.Activity;
import android.content.Intent;

import com.example.amyas.mvpapp.activity.presenter.contract.TaskContract;
import com.example.amyas.mvpapp.activity.task.AddEditTaskActivity;
import com.example.amyas.mvpapp.activity.task.TaskDetailActivity;
import com.example.amyas.mvpapp.base.FilteringType;
import com.example.amyas.mvpapp.bean.TaskBean;
import com.example.amyas.mvpapp.bean.TaskBean_;
import com.example.amyas.mvpapp.util.Prediction;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public class TaskPresenter implements TaskContract.Presenter{
    private boolean firstLoad = true;
    private TaskContract.view mFragment;
    private FilteringType currentFilteringType = FilteringType.ALL_TASK;

    public TaskPresenter(TaskContract.view fragment){
        mFragment = fragment;
        mFragment.setPresenter(this);
    }

    public void start() {
        loadTask(true);
    }

    @Override
    public void loadTask(boolean forceUpdate) {
        loadTask(forceUpdate||firstLoad, true);
        firstLoad = false;
    }

    /**
     *
     * @param forceUpdate true 从远程仓库取数据 false从本地仓库取
     * @param showLoadingUi 指示显示刷新按钮
     */
    @Override
    public void loadTask(boolean forceUpdate, boolean showLoadingUi) {
        if (showLoadingUi){
            mFragment.setLoadingIndicator(true);
        }
        // 目前一律从本地取
        List<TaskBean> taskBeanList = mFragment.getBoxStore()
                .boxFor(TaskBean.class)
                .query()
                .build()
                .find();
        Logger.d(taskBeanList);
        dispatchTask(taskBeanList);

    }

    private void dispatchTask(List<TaskBean> taskBeanList){
        List<TaskBean> list = new ArrayList<>();
        for (TaskBean bean : taskBeanList) {
            switch (currentFilteringType){
                case ACTIVE_TASK:
                    if (!bean.isCompleted()){
                        list.add(bean);
                    }
                    break;
                case COMPLETE_TASK:
                    if (bean.isCompleted()){
                        list.add(bean);
                    }
                    break;
                default:
                    list.add(bean);
            }
        }
        processTask(list);
    }

    private void processTask(List<TaskBean> taskBeanList){
        if (!taskBeanList.isEmpty()){
            mFragment.showTasks(taskBeanList);
            switch (currentFilteringType){
                case ACTIVE_TASK:
                    mFragment.showActiveFilter();
                    break;
                case COMPLETE_TASK:
                    mFragment.showCompleteFilter();
                    break;
                default:
                    mFragment.showAllFilter();
                    break;
            }
        }else {
            processEmptyTask();
        }

    }

    private void processEmptyTask(){
        switch (currentFilteringType){
            case ALL_TASK:
                mFragment.showNoTask();
                break;
            case COMPLETE_TASK:
                mFragment.showNoCompleteTask();
                break;
            case ACTIVE_TASK:
                mFragment.showNoActiveTask();
                break;
        }
    }


    @Override
    public void setCurrentFilteringType(FilteringType type) {
        currentFilteringType = type;
    }

    @Override
    public void setResultFeedback(int requestCode, int resultCode, Intent data) {
        Logger.e("requestCode: "+ requestCode+" resultCode: "+resultCode);
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        switch (requestCode){
            case AddEditTaskActivity.REQUEST_CODE:
                Logger.e("AddEditTaskActivity 返回结果");
                mFragment.showTaskSaved();
                break;
            case TaskDetailActivity.REQUEST_CODE:
                Logger.e("TaskDetailActivity 返回结果");
                mFragment.showTaskDeleted();
        }

    }

    @Override
    public void setAddTask() {
        mFragment.startAddTaskActivity();
    }

    @Override
    public void completeTask(long id) {
        Box<TaskBean> boxFor = mFragment.getBoxStore().boxFor(TaskBean.class);
        TaskBean taskBean = boxFor
                .query()
                .equal(TaskBean_.__ID_PROPERTY, id)
                .build()
                .findFirst();
        Prediction.checkNotNull(taskBean);
        taskBean.setCompleted(true);
        boxFor.put(taskBean);
        loadTask(false, false);
    }

    @Override
    public void activeTask(long id) {
        Box<TaskBean> boxFor = mFragment.getBoxStore().boxFor(TaskBean.class);
        TaskBean taskBean = boxFor
                .query()
                .equal(TaskBean_.__ID_PROPERTY, id)
                .build()
                .findFirst();
        Prediction.checkNotNull(taskBean);
        taskBean.setCompleted(false);
        boxFor.put(taskBean);
        loadTask(false, false);
    }

    @Override
    public void taskItemClick(long id) {
        mFragment.startDetailActivity(id);
    }
}
