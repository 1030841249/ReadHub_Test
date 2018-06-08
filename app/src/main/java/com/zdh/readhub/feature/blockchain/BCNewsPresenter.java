package com.zdh.readhub.feature.blockchain;

import android.view.ViewGroup;

import com.zdh.readhub.base.BaseListFragment;
import com.zdh.readhub.base.BaseListPresenter;
import com.zdh.readhub.base.BaseViewHolder;
import com.zdh.readhub.model.ApiData;
import com.zdh.readhub.model.News;
import com.zdh.readhub.network.ApiService;
import com.zdh.readhub.network.NewsService;

import io.reactivex.Observable;

/**
 * Created by zdh on 2018/6/2.
 */

public class BCNewsPresenter extends BaseListPresenter<News>{
    private NewsService mService = ApiService.createNewsService();

    @Override
    public Observable<ApiData<News>> request() {
        return mService.getBCNews();
    }

    @Override
    public Observable<ApiData<News>> requestMore() {
        return mService.getMoreBCNews(getLastCursor(), 10);
    }
}
