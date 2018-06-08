package com.zdh.readhub.feature.topic.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdh.readhub.R;
import com.zdh.readhub.base.BaseViewHolder;
import com.zdh.readhub.feature.main.MainActivity;
import com.zdh.readhub.feature.main.MainFragment;
import com.zdh.readhub.model.TopicTimeLine;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zdh on 2018/6/4.
 */

public class TopicTimeLineViewHolder extends BaseViewHolder<TopicTimeLine> {

    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.txt_time_line_content)
    TextView mTxtContent;
    @BindView(R.id.view_top_line)
    View mDividerTop;
    @BindView(R.id.view_bottom_line)
    View mDivierBottom;

    private TopicTimeLine mTimeLine;

    public TopicTimeLineViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_time_line);
    }

    @Override
    public void bindTo(TopicTimeLine value) {
        mTimeLine = value;
        mTxtDate.setText(value.date);
        mTxtContent.setText(value.content);
        mDividerTop.setVisibility(getItemViewType() == TopicDetailFragment.VIEW_TYPE_TOP ? View.INVISIBLE : View.VISIBLE);
        mDivierBottom.setVisibility(getItemViewType() == TopicDetailFragment.VIEW_TYPE_BOTTOM ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.txt_time_line_content)
    void onClickContent(View view) {
        ((MainActivity)view.getContext()).findFragment(MainFragment.class)
                .start(TopicDetailFragment.newInstance(mTimeLine.url));
    }
}
