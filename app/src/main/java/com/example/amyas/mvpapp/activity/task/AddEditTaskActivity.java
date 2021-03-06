package com.example.amyas.mvpapp.activity.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.amyas.mvpapp.R;
import com.example.amyas.mvpapp.activity.presenter.AddEditTaskPresenter;
import com.example.amyas.mvpapp.base.BaseActivity;
import com.example.amyas.mvpapp.fragment.AddTaskFragment;
import com.example.amyas.mvpapp.util.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: Amyas
 * date: 2017/12/27
 */

public class AddEditTaskActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    Unbinder unbinder;
    private ActionBar mActionBar;
    public static final int REQUEST_CODE = 0;
    public static final String EXTRA_TASK_KEY = "AddEditTaskActivity.EXTRA_TASK_KEY";
    private String taskId;

    public static Intent newInstance(String taskId, Context context){
        Intent intent = new Intent(context, AddEditTaskActivity.class);
        intent.putExtra(EXTRA_TASK_KEY, taskId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        unbinder = ButterKnife.bind(this);

        taskId = getIntent().getStringExtra(EXTRA_TASK_KEY);
        configToolBar();
        AddTaskFragment addTaskFragment = AddTaskFragment.newInstance(taskId);
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),addTaskFragment,
                R.id.fragment_container);
        new AddEditTaskPresenter(addTaskFragment, taskId);
    }

    /**
     * 配置 actionbar
     * 设置返回键 返回上一层
     */
    private void configToolBar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
