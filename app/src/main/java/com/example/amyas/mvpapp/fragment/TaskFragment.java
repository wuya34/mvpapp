package com.example.amyas.mvpapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amyas.mvpapp.R;
import com.example.amyas.mvpapp.base.BaseFragment;

/**
 * author: Amyas
 * date: 2017/12/26
 */

public class TaskFragment extends BaseFragment {

    public static TaskFragment newInstance(){
        return new TaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);


        return view;

    }

}
