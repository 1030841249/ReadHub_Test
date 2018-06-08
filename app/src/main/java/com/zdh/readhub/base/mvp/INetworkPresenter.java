package com.zdh.readhub.base.mvp;

import io.reactivex.Observable;

/**
 * Created by zdh on 2018/5/31.
 */

public interface INetworkPresenter<V extends INetworkView> extends IPresenter<V> {
    void start();

    void startRequestMore();

    Observable request();

    Observable requestMore();
}
