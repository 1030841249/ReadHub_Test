package com.zdh.readhub.feature.developer;

import com.zdh.readhub.base.BaseFragment;
import com.zdh.readhub.base.BaseListPresenter;
import com.zdh.readhub.model.News;
import com.zdh.readhub.network.ApiService;
import com.zdh.readhub.network.TechNewsService;

import io.reactivex.Observable;

/**
 * Created by zdh on 2018/6/5.
 */

public class TechNewsPresenter extends BaseListPresenter<News> {
    private TechNewsService mService = ApiService.createTechNewsService();

    @Override
    public Observable request() {
        return mService.getTechNews();
    }

    @Override
    public Observable requestMore() {
        return mService.getMoreTechNews(getLastCursor(), 10);
    }
}
