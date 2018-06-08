package com.zdh.readhub.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdh.readhub.base.mvp.IPresenter;
import com.zdh.readhub.base.mvp.IView;

import butterknife.ButterKnife;

/**
 * Created by zdh on 2018/5/31.
 * 对话碎片
 */

public abstract class BaseDialogFragment<P extends IPresenter> extends DialogFragment implements IView<P> {

    private P mPresenter;
    private boolean mIsPresenterNeeded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachPresenter();
    }

    @Override
    public P getPreseneter() {
        return mPresenter;
    }

    @Override
    public void attchPresenter(@NonNull P presenter) {
        mPresenter = presenter;
        mIsPresenterNeeded = true;
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        if (mPresenter == null) return;
        mPresenter.detachView();
        mPresenter = null;
    }

    @LayoutRes
    public abstract int getFragmentLayout();
}
