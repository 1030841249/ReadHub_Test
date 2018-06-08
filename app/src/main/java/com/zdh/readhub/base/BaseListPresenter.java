package com.zdh.readhub.base;

import com.zdh.readhub.base.mvp.INetworkPresenter;
import com.zdh.readhub.model.ApiData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zdh on 2018/6/2.
 */

public abstract class BaseListPresenter<T> extends BasePresenter<BaseListFragment> implements INetworkPresenter<BaseListFragment> {

    private String lastCursor;

    /**
     * 网络请求封装
     */
    @SuppressWarnings("unchecked")
    @Override
    public void start() {
        request().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ApiData>() {
                    @Override
                    public void accept(ApiData apiData) throws Exception {
                        if (getView() == null) { // 没有视图
                            return;
                        }
                        if (apiData == null || apiData.getData() == null) { // 没有数据
                            getView().onError(new Throwable("请求失败"));
                            return;
                        }
                        // 没有出现异常状况，则表示请求成功
                        getView().onSuccess(apiData.getData());
                        lastCursor = apiData.getLastCursor();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (getView() == null) {
                            return;
                        }
                        getView().onError(throwable);
                    }
                });
    }

    @Override
    public void startRequestMore() {

        requestMore().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ApiData>() {
                    @Override
                    public void accept(ApiData apiData) throws Exception {
                        if (getView() == null) {
                            return;
                        }
                        if (apiData == null || apiData.getData() == null) {
                            getView().onError(new Throwable("请求失败"));
                            return;
                        }
                        getView().onSuccess(apiData.getData());
                        if (apiData.getData().isEmpty()) { // 数据为空，无可用数据加载
                            getView().hasMore = false;
                            return;
                        }
                        lastCursor = apiData.getLastCursor();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (getView() == null) {
                            return;
                        }
                        getView().onError(throwable);
                    }
                });
    }

    @Override
    public abstract Observable request();

    @Override
    public abstract Observable requestMore();

    public String getLastCursor() {
        return lastCursor;
    }
}
