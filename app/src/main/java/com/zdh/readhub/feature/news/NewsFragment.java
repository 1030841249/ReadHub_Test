package com.zdh.readhub.feature.news;

import android.view.ViewGroup;

import com.zdh.readhub.base.BaseFragment;
import com.zdh.readhub.base.BaseListFragment;
import com.zdh.readhub.base.BaseListPresenter;
import com.zdh.readhub.base.BaseViewHolder;
import com.zdh.readhub.model.News;

/**
 * Created by zdh on 2018/6/2.
 */

public class NewsFragment extends BaseListFragment<News> {

    public static final String TAG = "NewsFragment";

    public static NewsFragment newsInstance() {
        return new NewsFragment();
    }

    @Override
    public BaseViewHolder<News> provideViewHodler(ViewGroup parent, int viewType) {
        return new NewsViewHolder(getActivity(), parent);
    }

    @Override
    public BaseListPresenter<News> createPresenter() {
        return new NewsPresenter();
    }
}
