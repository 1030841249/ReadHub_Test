package com.zdh.readhub.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdh.readhub.R;

import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by zdh on 2018/6/5.
 */

public class MyDynamicBox extends DynamicBox {
    private final static String TAG_EMPTY_VIEW = "TAG_EMPTY_VIEW";
    private Context mContext;
    private ViewGroup mTargetView;

    public MyDynamicBox(Context context, ViewGroup targetView) {
        super(context, targetView);
        mContext = context;
        mTargetView = targetView;
        init();
    }

    private void init() {
        addCustomView(LayoutInflater.from(mContext).inflate(R.layout.view_empty_box,
                mTargetView, false), TAG_EMPTY_VIEW);
    }

    public void showEmptyView() {
        showCustomView(TAG_EMPTY_VIEW);
    }
}
