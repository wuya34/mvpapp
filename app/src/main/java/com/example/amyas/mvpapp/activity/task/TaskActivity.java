package com.example.amyas.mvpapp.activity.task;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.amyas.mvpapp.R;
import com.example.amyas.mvpapp.base.BaseActivity;
import com.example.amyas.mvpapp.fragment.TaskFragment;
import com.example.amyas.mvpapp.util.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TaskActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mUnbinder = ButterKnife.bind(this);
        configToolbar();
        setNavigationView();
        TaskFragment taskFragment =
                (TaskFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (taskFragment==null){
            taskFragment = TaskFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),taskFragment, R.id.fragment_container);
        }
    }

    private void configToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle(R.string.toolbar_title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void setNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_menu_1:
                        Snackbar.make(mCoordinator, "task list", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.nav_menu_2:
                        Snackbar.make(mCoordinator, "statistics", Snackbar.LENGTH_LONG).show();
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
