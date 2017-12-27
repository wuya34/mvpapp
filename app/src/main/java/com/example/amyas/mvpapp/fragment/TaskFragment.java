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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amyas.mvpapp.MyApplication;
import com.example.amyas.mvpapp.R;
import com.example.amyas.mvpapp.activity.presenter.contract.TaskContract;
import com.example.amyas.mvpapp.activity.task.AddEditTaskActivity;
import com.example.amyas.mvpapp.base.BaseFragment;
import com.example.amyas.mvpapp.bean.TaskBean;
import com.example.amyas.mvpapp.ui.ScrollChildSwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.objectbox.BoxStore;

/**
 * author: Amyas
 * date: 2017/12/26
 */

public class TaskFragment extends BaseFragment implements TaskContract.view{

    @BindView(R.id.filtering_label)
    TextView mFilteringLabel;
    @BindView(R.id.no_task)
    LinearLayout mNoTask;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.list_view)
    ListView mListView;

    private TaskContract.Presenter mPresenter;
    private BoxStore mBoxStore;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBoxStore = ((MyApplication)getActivity().getApplication()).getBoxStore();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task, container, false);
        unbinder = ButterKnife.bind(this, root);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
            startActivity(intent);
        });
        mRefreshLayout.setColorSchemeColors(
                getActivity().getResources().getColor(R.color.colorPrimary),
                getActivity().getResources().getColor(R.color.colorAccent),
                getActivity().getResources().getColor(R.color.colorPrimaryDark)
        );
        mRefreshLayout.setScrollView(mListView);
        mRefreshLayout.setOnRefreshListener(()->
                Snackbar.make(getView(), "refreshing", Snackbar.LENGTH_SHORT).show());
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
        switch (item.getItemId()){
            case R.id.menu_filter:
                Snackbar.make(getView(), "menu_filter", Snackbar.LENGTH_SHORT).show();
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
    public void showLoadingIndicator(boolean active) {
        if (getView()==null){
            return;
        }
        mRefreshLayout.post(()->mRefreshLayout.setRefreshing(true));
    }

    @Override
    public BoxStore getBoxStore() {
        if (mBoxStore==null){
            mBoxStore = ((MyApplication)getActivity().getApplication()).getBoxStore();
        }
        return mBoxStore;
    }

    private static class TaskAdapter extends BaseAdapter{
        private List<TaskBean> mTaskBeans;

        public TaskAdapter(List<TaskBean> taskBeans) {
            mTaskBeans = taskBeans;
        }

        @Override
        public int getCount() {
            return mTaskBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return mTaskBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View root = convertView;
            if (root==null){
                root = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.task_item,parent, false
                );
            }
            TaskBean taskBean = (TaskBean) getItem(position);
            CheckBox complete = root.findViewById(R.id.complete);
            TextView title = root.findViewById(R.id.title);

            complete.setChecked(taskBean.isCompleted());
            title.setText(taskBean.getTitle());
            if (taskBean.isCompleted()){
                root.setBackground(parent.getContext().getResources()
                .getDrawable(R.drawable.complete_touch_feedback));
            }else {
                root.setBackground(parent.getContext().getResources()
                        .getDrawable(R.drawable.touch_feedback));
            }

            return root;
        }
    }


}
