package com.example.amyas.mvpapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.amyas.mvpapp.MyApplication;
import com.example.amyas.mvpapp.R;
import com.example.amyas.mvpapp.activity.presenter.contract.TaskContract;
import com.example.amyas.mvpapp.activity.task.AddEditTaskActivity;
import com.example.amyas.mvpapp.activity.task.TaskDetailActivity;
import com.example.amyas.mvpapp.base.BaseFragment;
import com.example.amyas.mvpapp.base.FilteringType;
import com.example.amyas.mvpapp.bean.TaskBean;
import com.example.amyas.mvpapp.ui.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.objectbox.BoxStore;

import static com.example.amyas.mvpapp.activity.task.TaskDetailActivity.REQUEST_CODE;

/**
 * author: Amyas
 * date: 2017/12/26
 */

public class TaskFragment extends BaseFragment implements TaskContract.view {

    @BindView(R.id.filtering_label)
    TextView mFilteringLabel;
    @BindView(R.id.no_task_layout)
    LinearLayout mNoTask;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.tasks_layout)
    LinearLayout mTasksLayout;
    @BindView(R.id.no_task_img)
    ImageView mNoTaskImg;
    @BindView(R.id.no_task_text)
    TextView mNoTaskText;

    private TaskContract.Presenter mPresenter;
    private BoxStore mBoxStore;
    private TaskAdapter mTaskAdapter;

    TaskItemListener mTaskItemListener = new TaskItemListener() {
        @Override
        public void onCompleteTaskClick(long id) {
            mPresenter.completeTask(id);
        }

        @Override
        public void onActiveTaskClick(long id) {
            mPresenter.activeTask(id);
        }

        @Override
        public void onTaskClick(long id) {
            mPresenter.taskItemClick(id);
        }
    };


    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBoxStore = ((MyApplication) getActivity().getApplication()).getBoxStore();
        mTaskAdapter = new TaskAdapter(new ArrayList<>(), mTaskItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task, container, false);
        unbinder = ButterKnife.bind(this, root);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add);
        // 新建Task
        fab.setOnClickListener(v -> mPresenter.setAddTask());
        mListView.setAdapter(mTaskAdapter);
        mRefreshLayout.setColorSchemeColors(
                getActivity().getResources().getColor(R.color.colorPrimary),
                getActivity().getResources().getColor(R.color.colorAccent),
                getActivity().getResources().getColor(R.color.colorPrimaryDark)
        );
        mRefreshLayout.setScrollView(mListView);
        mRefreshLayout.setOnRefreshListener(() ->
                mPresenter.loadTask(true));
        setHasOptionsMenu(true);
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tasks_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_clear:
                Snackbar.make(getView(), "menu_clear", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.menu_refresh:
                Snackbar.make(getView(), "menu_refresh", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(TaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        mRefreshLayout.post(() -> mRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showTasks(List<TaskBean> taskBeanList) {
        mTaskAdapter.replaceData(taskBeanList);
        setLoadingIndicator(false);
        mTasksLayout.setVisibility(View.VISIBLE);
        mNoTask.setVisibility(View.GONE);
    }

    @Override
    public void showFilterMessages(String s) {
        mFilteringLabel.setText(s);
    }

    @Override
    public void showEmptyTask() {
        setLoadingIndicator(false);
        mTasksLayout.setVisibility(View.GONE);
        mNoTask.setVisibility(View.VISIBLE);
    }

    @Override
    public void showActiveFilter() {
        showFilterMessages("Active TO-DOs");
    }

    @Override
    public void showCompleteFilter() {
        showFilterMessages("Completed TO-DOs");
    }

    @Override
    public void showAllFilter() {
        showFilterMessages("All TO-DOs");
    }

    @Override
    public void showNoActiveTask() {
        NoTaskHint(
                R.drawable.ic_no_active,
                "You have no active TO-DOs"
        );
    }

    @Override
    public void showNoCompleteTask() {
        NoTaskHint(
                R.drawable.ic_no_complete,
                "You have no completed TO-DOs"
        );
    }

    @Override
    public void showNoTask() {
        NoTaskHint(
                R.drawable.ic_no_task,
                "You have no TO-DOs");
    }

    @Override
    public void showFilteringPopUpMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popupMenu.getMenuInflater().inflate(R.menu.filter_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.active:
                    mPresenter.setCurrentFilteringType(FilteringType.ACTIVE_TASK);
                    break;
                case R.id.all:
                    mPresenter.setCurrentFilteringType(FilteringType.ALL_TASK);
                    break;
                case R.id.complete:
                    mPresenter.setCurrentFilteringType(FilteringType.COMPLETE_TASK);
                    break;
            }
            mPresenter.loadTask(false);
            return true;
        });
        popupMenu.show();
    }

    @Override
    public boolean isActive() {
        return this.isAdded();
    }

    @Override
    public void showTaskSaved() {
        Snackbar.make(getView(), "TO-DO saved", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTaskDeleted() {
        Snackbar.make(getView(), "TO-DO deleted", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void startAddTaskActivity() {
        Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_CODE);
    }

    @Override
    public void startDetailActivity(long id) {
        Intent intent = TaskDetailActivity.newInstance(getContext(),String.valueOf(id));
        startActivityForResult(intent, REQUEST_CODE);
    }


    private void NoTaskHint(int drawable, String hint){
        showEmptyTask();
        mNoTaskImg.setImageDrawable(getResources().getDrawable(drawable));
        mNoTaskText.setText(hint);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.setResultFeedback(requestCode,resultCode,data);
    }

    @Override
    public BoxStore getBoxStore() {
        if (mBoxStore == null) {
            mBoxStore = ((MyApplication) getActivity().getApplication()).getBoxStore();
        }
        return mBoxStore;
    }

    interface TaskItemListener {
        void onCompleteTaskClick(long id);

        void onActiveTaskClick(long id);

        void onTaskClick(long id);
    }

    private static class TaskAdapter extends BaseAdapter {
        private List<TaskBean> mTaskBeans;
        private TaskItemListener mTaskItemListener;

        public TaskAdapter(List<TaskBean> taskBeans, TaskItemListener listener) {
            mTaskBeans = taskBeans;
            mTaskItemListener = listener;
        }

        public void replaceData(List<TaskBean> taskBeans) {
            setTaskBeans(taskBeans);
            notifyDataSetChanged();
        }

        private void setTaskBeans(List<TaskBean> taskBeans) {
            if (!taskBeans.isEmpty()) {
                mTaskBeans = taskBeans;
            }
        }

        @Override
        public int getCount() {
            return mTaskBeans.size();
        }

        @Override
        public TaskBean getItem(int position) {
            return mTaskBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View root = convertView;
            if (root == null) {
                root = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.task_item, parent, false
                );
            }
            TaskBean taskBean = getItem(position);
            CheckBox complete = root.findViewById(R.id.complete);
            TextView title = root.findViewById(R.id.title);

            complete.setChecked(taskBean.isCompleted());
            complete.setOnClickListener(v -> {
                if (taskBean.isCompleted()) {
                    // 取消选中
                    mTaskItemListener.onActiveTaskClick(taskBean.getId());
                } else {
                    // 设置选中
                    mTaskItemListener.onCompleteTaskClick(taskBean.getId());
                }
            });
            root.setOnClickListener(v -> mTaskItemListener.onTaskClick(taskBean.getId()));
            title.setText(taskBean.getTitle());
            if (taskBean.isCompleted()) {
                root.setBackground(parent.getContext().getResources()
                        .getDrawable(R.drawable.complete_touch_feedback));
            } else {
                root.setBackground(parent.getContext().getResources()
                        .getDrawable(R.drawable.touch_feedback));
            }

            return root;
        }
    }


}
