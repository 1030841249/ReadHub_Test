package com.zdh.readhub.feature.topic.list;

import com.zdh.readhub.base.BaseListPresenter;
import com.zdh.readhub.model.Topic;
import com.zdh.readhub.network.ApiService;
import com.zdh.readhub.network.HotTopicService;

import io.reactivex.Observable;

/**
 * Created by zdh on 2018/6/2.
 */

public class HotTopicPresenter extends BaseListPresenter<Topic> {

    private HotTopicService mService = ApiService.createHotTopicService();

    @Override
    public Observable request() {
        return mService.getHotTopic();
    }

    @Override
    public Observable requestMore() {
        return mService.getMoreHotTopic(getLastCursor(), 10);
    }
}
