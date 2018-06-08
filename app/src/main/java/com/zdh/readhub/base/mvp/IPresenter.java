package com.zdh.readhub.base.mvp;

import android.support.annotation.NonNull;

/**
 * Created by zdh on 2018/5/31.
 */

public interface IPresenter<V extends IView> {

    @NonNull
    V getView();

    void attachView(V view);

    void detachView();
}
