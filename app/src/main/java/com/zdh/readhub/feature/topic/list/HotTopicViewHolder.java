package com.zdh.readhub.feature.topic.list;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zdh.readhub.R;
import com.zdh.readhub.base.BaseViewHolder;
import com.zdh.readhub.feature.main.MainActivity;
import com.zdh.readhub.feature.main.MainFragment;
import com.zdh.readhub.feature.topic.detail.TopicDetailFragment;
import com.zdh.readhub.feature.topic.instant.InstantReadFragment;
import com.zdh.readhub.model.Topic;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by zdh on 2018/6/2.
 */

public class HotTopicViewHolder extends BaseViewHolder<Topic> {

    @BindView(R.id.txt_topic_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_topic_summary)
    TextView mTxtSummary;
    @BindView(R.id.txt_instant_read)
    TextView mTxtInstantRead;
    @BindView(R.id.divider_instant_read)
    View mDivierInstantRead;
    @BindView(R.id.frame_instant_read)
    FrameLayout mFrameInstantRead;

    private Topic mTopic;

    public HotTopicViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_topic);
    }

    @Override
    public void bindTo(final Topic value) {
        mTopic = value;
        // 设置富文本
        SpannableString spannableString =
                new SpannableString(value.getTitle() + "  " + value.getPublishDateCountDown());
        // 前景色，只包含终了坐标
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#AAACB4")),
                value.getTitle().length() + 2,
                value.getTitle().length() + value.getPublishDateCountDown().length() + 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        // 设置字符相对大小
        spannableString.setSpan(new RelativeSizeSpan(0.8f),
                value.getTitle().length() + 2,
                value.getTitle().length() + value.getPublishDateCountDown().length() + 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        mTxtTitle.setText(spannableString);
        mTxtSummary.setText(value.getSummary());

        mTxtSummary.setVisibility(TextUtils.isEmpty(value.getSummary()) ? View.GONE : View.VISIBLE);
        mTxtInstantRead.setVisibility(mTopic.hasInstantView() ? View.VISIBLE : View.GONE);
        mDivierInstantRead.setVisibility(mTopic.hasInstantView() ? View.VISIBLE : View.GONE);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISupportFragment fragment;
                if (value.newsArray == null) {
                    fragment = TopicDetailFragment.newInstance(value.id);
                } else {
                    fragment = TopicDetailFragment.newInstance(value);
                }
                ((MainActivity)v.getContext()).findFragment(MainFragment.class)
                        .start(fragment);
            }
        });

        mFrameInstantRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstantReadFragment.newInstance(value.id)
                        .show(((SupportActivity)v.getContext()).getSupportFragmentManager(), InstantReadFragment.TAG);
            }
        });
    }
}
