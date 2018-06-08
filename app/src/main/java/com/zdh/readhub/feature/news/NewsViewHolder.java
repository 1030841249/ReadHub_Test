package com.zdh.readhub.feature.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdh.readhub.R;
import com.zdh.readhub.app.ReadhubApplication;
import com.zdh.readhub.base.BaseViewHolder;
import com.zdh.readhub.database.ReadhubDatabase;
import com.zdh.readhub.feature.common.WebViewFragment;
import com.zdh.readhub.feature.main.MainFragment;
import com.zdh.readhub.model.News;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by zdh on 2018/6/2.
 */

public class NewsViewHolder extends BaseViewHolder<News> {

    @BindView(R.id.txt_news_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_news_summary)
    TextView mTxtSummary;
    @BindView(R.id.txt_news_time)
    TextView mTxtTime;

    public NewsViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_news);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void bindTo(final News value) {
        mTxtTitle.setText(value.getTitle());
        mTxtSummary.setText(value.getSummary());

        String time;
        if (!TextUtils.isEmpty(value.authorName) && !TextUtils.isEmpty(value.siteName)) {
            time = ReadhubApplication.getmContext().getString(R.string.site_author_time_format,
                    value.siteName, value.authorName,
                    value.getPublishDateCountDown());
        } else if (TextUtils.isEmpty(value.authorName) && TextUtils.isEmpty(value.siteName)) {
            time = value.getPublishDateCountDown();
        } else if (TextUtils.isEmpty(value.siteName)) {
            time = ReadhubApplication.getmContext()
                    .getString(R.string.author_time_format,
                            value.authorName, value.getPublishDateCountDown());
        } else {
            time = ReadhubApplication.getmContext()
                    .getString(R.string.author_time_format,
                            value.siteName,
                            value.getPublishDateCountDown());
        }
        mTxtTime.setText(time);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SupportActivity)v.getContext()).findFragment(MainFragment.class)
                        .start(WebViewFragment.newInstance(value, true));

            }
        });

        mTxtTime.setVisibility(TextUtils.isEmpty(value.getTitle()) ? View.GONE : View.VISIBLE);
        mTxtSummary.setVisibility(TextUtils.isEmpty(value.getSummary()) ? View.GONE : View.VISIBLE);
    }
}
