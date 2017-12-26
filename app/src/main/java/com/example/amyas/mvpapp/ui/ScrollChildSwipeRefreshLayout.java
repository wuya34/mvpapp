package com.example.amyas.mvpapp.ui;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: Amyas
 * date: 2017/12/26
 */

public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {

    private View mScrollView;

    public ScrollChildSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollChildSwipeRefreshLayout(Context context) {
        super(context);
    }

    public void setScrollView(View view){
        mScrollView = view;
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollView!=null){
            return mScrollView.canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }
}
