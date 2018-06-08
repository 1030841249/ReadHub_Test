package com.zdh.readhub.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zdh.readhub.R;
import com.zdh.readhub.model.Topic;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zdh on 2018/6/4.
 */

public class TopicShareView extends ScrollView{

    @BindView(R.id.txt_topic_share_title)
    TextView mTxtTopicTitle;
    @BindView(R.id.txt_topic_summary)
    TextView mTxtTopicSummary;

    private Topic mTopic;

    public TopicShareView(Context context) {
        this(context, null);
    }

    public TopicShareView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopicShareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_topic_share, this, true);
        ButterKnife.bind(this);
    }

    public void setup(Topic topic) {
        mTopic = topic;
        mTxtTopicTitle.setText(topic.getTitle());
        mTxtTopicSummary.setText(topic.getSummary());
    }

    public Topic getTopic() {
        return mTopic;
    }
}
