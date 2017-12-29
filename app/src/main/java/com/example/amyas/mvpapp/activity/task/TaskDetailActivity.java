package com.example.amyas.mvpapp.activity.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.amyas.mvpapp.R;
import com.example.amyas.mvpapp.activity.presenter.TaskDetailPresenter;
import com.example.amyas.mvpapp.base.BaseActivity;
import com.example.amyas.mvpapp.fragment.TaskDetailFragment;
import com.example.amyas.mvpapp.util.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Amyas
 * date: 2017/12/26
 */

public class TaskDetailActivity extends BaseActivity {
    public static final int REQUEST_CODE = 1;
    public static final String sEXTRA_task_bean_id = "sEXTRA_task_bean_id";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    private String taskId;

    public static Intent newInstance(Context context, String id) {
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra(sEXTRA_task_bean_id, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        taskId = getIntent().getStringExtra(sEXTRA_task_bean_id);
        configToolbar();

        TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(taskId);
        new TaskDetailPresenter(taskId, taskDetailFragment);
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                taskDetailFragment, R.id.fragment_container);
    }

    private void configToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.toolbar_title);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
