package com.example.amyas.mvpapp.activity.presenter.contract;

import com.example.amyas.mvpapp.base.BasePresenter;
import com.example.amyas.mvpapp.base.BaseView;
import com.example.amyas.mvpapp.bean.TaskBean;

/**
 * author: Amyas
 * date: 2017/12/29
 */

public interface TaskDetailContract {

    interface view extends BaseView<Presenter> {
        void showTaskBeanDeleted();

        void startEditTaskActivity(String taskId);

        void showTaskBeanTitle(String title);

        void hideTaskBeanTitle();

        void showTaskBeanDescription(String description);

        void hideTaskBeanDescription();

        void showMissingTaskBean();

        void showIsComplete(boolean active);

        void showTaskComplete();

        void showTaskActive();
    }

    interface Presenter extends BasePresenter {
        void deleteTaskBean(String taskId);

        void editTaskBean(String taskId);

        void completeTaskBean();

        void activeTaskBean();

    }
}
