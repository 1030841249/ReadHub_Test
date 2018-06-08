package com.zdh.readhub.feature.topic.list;

import android.view.ViewGroup;

import com.zdh.readhub.base.BaseListFragment;
import com.zdh.readhub.base.BaseListPresenter;
import com.zdh.readhub.base.BaseViewHolder;
import com.zdh.readhub.model.Topic;

/**
 * Created by zdh on 2018/6/2.
 */

public class HotTopicFragment extends BaseListFragment<Topic>{


    public static HotTopicFragment newInstance() {
        return new HotTopicFragment();
    }

    @Override
    public BaseViewHolder<Topic> provideViewHodler(ViewGroup parent, int viewType) {
        return new HotTopicViewHolder(getActivity(), parent);
    }

    @Override
    public BaseListPresenter<Topic> createPresenter() {
        return new HotTopicPresenter();
    }
}
