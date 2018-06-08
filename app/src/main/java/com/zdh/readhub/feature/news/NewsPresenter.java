package com.zdh.readhub.feature.news;

import com.zdh.readhub.base.BaseListPresenter;
import com.zdh.readhub.model.News;
import com.zdh.readhub.network.ApiService;
import com.zdh.readhub.network.NewsService;

import io.reactivex.Observable;

/**
 * Created by zdh on 2018/6/2.
 */

public class NewsPresenter extends BaseListPresenter<News> {

    private NewsService mService = ApiService.createNewsService();

    @Override
    public Observable request() {
        return mService.getNews();
    }

    @Override
    public Observable requestMore() {
        return mService.getMoreNews(getLastCursor(), 10);
    }
}
