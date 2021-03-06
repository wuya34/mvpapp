package com.example.amyas.mvpapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.amyas.mvpapp.MyApplication;
import com.example.amyas.mvpapp.R;
import com.example.amyas.mvpapp.activity.presenter.contract.AddEditTaskContract;
import com.example.amyas.mvpapp.base.BaseActivity;
import com.example.amyas.mvpapp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.objectbox.BoxStore;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public class AddTaskFragment extends BaseFragment implements AddEditTaskContract.view {

    @BindView(R.id.add_task_title)
    EditText mAddTaskTitle;
    @BindView(R.id.add_task_description)
    EditText mAddTaskDescription;
    Unbinder unbinder;
    private AddEditTaskContract.Presenter mPresenter;
    private BoxStore mBoxStore;
    public static final String EXTRA_TASK_ID = "AddTaskFragment.EXTRA_TASK_ID";
    private String taskId;

    public static AddTaskFragment newInstance(String taskId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TASK_ID, taskId);
        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBoxStore = ((MyApplication)getActivity().getApplication()).getBoxStore();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_task, container, false);

        taskId = getArguments().getString(EXTRA_TASK_ID);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(v -> mPresenter.saveTaskBean(mAddTaskTitle.getText().toString(),
                mAddTaskDescription.getText().toString()));


        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(AddEditTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSavedTaskBean() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showEmptyTaskError() {
        Snackbar.make(getView(), "TO DOs cannot be empty", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTaskTitle(String title) {
        mAddTaskTitle.setText(title);
    }

    @Override
    public void showTaskDescription(String description) {
        mAddTaskDescription.setText(description);
    }

    @Override
    public BoxStore getBoxStore() {
        if (mBoxStore==null){
            mBoxStore = ((MyApplication)getActivity().getApplication()).getBoxStore();
        }
        return mBoxStore;
    }



}
