package com.zdh.readhub.feature.blockchain;

import com.zdh.readhub.base.BaseListPresenter;
import com.zdh.readhub.feature.news.NewsFragment;
import com.zdh.readhub.model.News;

/**
 * Created by zdh on 2018/6/2.
 */

public class BCNewsFragment extends NewsFragment{

    public static NewsFragment newInstance() {
        return new BCNewsFragment();
    }

    @Override
    public BaseListPresenter<News> createPresenter() {
        return new BCNewsPresenter();
    }
}
