package com.zdh.readhub.base;

import android.support.annotation.NonNull;

import com.zdh.readhub.base.mvp.IPresenter;
import com.zdh.readhub.base.mvp.IView;

/**
 * Created by zdh on 2018/5/31.
 * 包装
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {

    private V mView;

    @NonNull
    @Override
    public V getView() {
        return mView;
    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
