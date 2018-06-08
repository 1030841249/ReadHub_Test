package com.zdh.readhub.base.mvp;

/**
 * Created by zdh on 2018/5/31.
 */

public interface INetworkView<P extends INetworkPresenter, D> extends IView<P> {
    void onSuccess(D data);

    void onError(Throwable e);
}
