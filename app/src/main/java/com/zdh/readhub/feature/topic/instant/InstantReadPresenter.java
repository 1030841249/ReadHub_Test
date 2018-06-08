package com.zdh.readhub.feature.topic.instant;

import com.zdh.readhub.base.BasePresenter;
import com.zdh.readhub.base.mvp.INetworkPresenter;
import com.zdh.readhub.model.InstantReadData;
import com.zdh.readhub.network.ApiService;
import com.zdh.readhub.network.HotTopicService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zdh on 2018/6/3.
 */

public class InstantReadPresenter extends BasePresenter<InstantReadFragment> implements INetworkPresenter<InstantReadFragment>{

    private HotTopicService mService = ApiService.createHotTopicService();
    private String mTopicId;

    @Override
    public void start() {
        request().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<InstantReadData>() {
                    /**
                     * 请求成功时，提交给View去处理，做UI操作
                     * @param instantReadData
                     * @throws Exception
                     */
                    @Override
                    public void accept(InstantReadData instantReadData) throws Exception {
                        if (getView() == null) {
                            return;
                        }
                        getView().onSuccess(instantReadData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (getView() == null) {
                            return;
                        }
                        throwable.printStackTrace();
                        getView().onError(throwable);
                    }
                });
    }

    @Override
    public void startRequestMore() {

    }

    @Override
    public Observable request() {
        return mService.getInstantRead(mTopicId);
    }

    @Override
    public Observable requestMore() {
        return null;
    }

    public void getInstantRead(String topicId) {
        mTopicId = topicId;
        start();
    }
}
