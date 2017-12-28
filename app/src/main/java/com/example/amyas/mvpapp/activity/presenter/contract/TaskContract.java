package com.example.amyas.mvpapp.activity.presenter.contract;

import android.content.Intent;

import com.example.amyas.mvpapp.base.BasePresenter;
import com.example.amyas.mvpapp.base.BaseView;
import com.example.amyas.mvpapp.base.FilteringType;
import com.example.amyas.mvpapp.bean.TaskBean;

import java.util.List;


/**
 * author: Amyas
 * date: 2017/12/27
 */

public interface TaskContract {

    interface view extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showTasks(List<TaskBean> taskBeans);

        void showFilterMessages(String s);

        void showEmptyTask();

        void showActiveFilter();

        void showCompleteFilter();

        void showAllFilter();

        void showNoActiveTask();

        void showNoCompleteTask();

        void showNoTask();

        void showFilteringPopUpMenu();

        boolean isActive();

        void showTaskSaved();

        void addTask();



    }

    interface Presenter extends BasePresenter {
        void loadTask(boolean forceUpdate);

        void loadTask(boolean forceUpdate, boolean showLoadingUi);

        void setCurrentFilteringType(FilteringType type);

        void setResultFeedback(int requestCode, int resultCode, Intent data);

        void setAddTask();

        /**
         *  checkbox 取消选中时回调 更改 `TaskBean` 字段 isComplete 为false
         * @param id `TaskBean` id
         */
        void completeTask(long id);

        void activeTask(long id);

        void taskItemClick(long id);




    }
}
