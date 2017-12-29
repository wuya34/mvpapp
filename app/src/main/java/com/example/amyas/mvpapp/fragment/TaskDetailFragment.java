package com.example.amyas.mvpapp.fragment;

import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.amyas.mvpapp.MyApplication;
import com.example.amyas.mvpapp.R;
import com.example.amyas.mvpapp.activity.presenter.contract.TaskDetailContract;
import com.example.amyas.mvpapp.activity.task.AddEditTaskActivity;
import com.example.amyas.mvpapp.base.BaseFragment;
import com.example.amyas.mvpapp.bean.TaskBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.objectbox.BoxStore;

import static com.example.amyas.mvpapp.activity.task.AddEditTaskActivity.REQUEST_CODE;

/**
 * author: Amyas
 * date: 2017/12/29
 */

public class TaskDetailFragment extends BaseFragment implements TaskDetailContract.view {

    public static final String EXTRA_TASK_ID = "EXTRA_TASK_ID";
    @BindView(R.id.is_complete)
    CheckBox mIsComplete;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;
    Unbinder unbinder;
    private BoxStore mBoxStore;
    private TaskDetailContract.Presenter mPresenter;
    private String taskId;

    public static TaskDetailFragment newInstance(String taskId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TASK_ID, taskId);
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        taskDetailFragment.setArguments(bundle);
        return taskDetailFragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBoxStore = ((MyApplication) getActivity().getApplication()).getBoxStore();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task_detail, container, false);
        unbinder = ButterKnife.bind(this, root);
        setHasOptionsMenu(true);

        taskId = (String) getArguments().getSerializable(EXTRA_TASK_ID);
        mIsComplete.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                mPresenter.completeTaskBean();
            }else {
                mPresenter.activeTaskBean();
            }
        });
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_edit);
        fab.setOnClickListener(v -> mPresenter.editTaskBean(taskId));
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.task_detail_delete, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                mPresenter.deleteTaskBean(taskId);
                return true;

        }
        return false;
    }

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BoxStore getBoxStore() {
        if (mBoxStore == null) {
            mBoxStore = ((MyApplication) getActivity().getApplication()).getBoxStore();
        }
        return mBoxStore;
    }

    @Override
    public void showTaskBeanDeleted() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void startEditTaskActivity(String taskId) {
        Intent intent = AddEditTaskActivity.newInstance(taskId, getContext());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void showTaskBeanTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void hideTaskBeanTitle() {
        mTitle.setVisibility(View.GONE);
    }

    @Override
    public void showTaskBeanDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void hideTaskBeanDescription() {
        mDescription.setVisibility(View.GONE);
    }

    @Override
    public void showMissingTaskBean() {
        mTitle.setText("");
        mDescription.setText("NO DATA");
        mIsComplete.setChecked(false);
    }

    @Override
    public void showIsComplete(boolean active) {
        mIsComplete.setChecked(active);
    }

    @Override
    public void showTaskComplete() {
        Snackbar.make(getView(), "Task marked complete", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTaskActive() {
        Snackbar.make(getView(), "Task marked active", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=Activity.RESULT_OK){
            return;
        }
        switch (requestCode){
            case AddEditTaskActivity.REQUEST_CODE:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
