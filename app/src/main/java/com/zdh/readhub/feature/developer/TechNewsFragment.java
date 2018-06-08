package com.zdh.readhub.feature.developer;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import com.zdh.readhub.base.BaseListFragment;
import com.zdh.readhub.base.BaseListPresenter;
import com.zdh.readhub.base.BaseViewHolder;
import com.zdh.readhub.feature.news.NewsViewHolder;
import com.zdh.readhub.model.News;

/**
 * Created by zdh on 2018/6/5.
 */

public class TechNewsFragment extends BaseListFragment<News> {

    public static TechNewsFragment newInstance() {
        return new TechNewsFragment();
    }

    @Override
    public BaseViewHolder<News> provideViewHodler(ViewGroup parent, int viewType) {
        return new NewsViewHolder(getActivity(), parent);
    }

    @Override
    public BaseListPresenter<News> createPresenter() {
        return new TechNewsPresenter();
    }
}
