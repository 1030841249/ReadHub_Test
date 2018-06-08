package com.zdh.readhub.feature.topic.detail;

import android.os.AsyncTask;

import com.zdh.readhub.app.Constant;
import com.zdh.readhub.base.BasePresenter;
import com.zdh.readhub.base.mvp.INetworkPresenter;
import com.zdh.readhub.model.Topic;
import com.zdh.readhub.model.TopicTimeLine;
import com.zdh.readhub.network.ApiService;
import com.zdh.readhub.network.HotTopicService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zdh on 2018/6/4.
 */

public class TopicDetailPresenter extends BasePresenter<TopicDetailFragment>
    implements INetworkPresenter<TopicDetailFragment>{

    private HotTopicService mService = ApiService.createHotTopicService();
    private String mTopicId;

    /**
     * 网络请求操作的开端，获取到数据后返回给view去处理
     */
    @Override
    public void start() {
        request().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Topic>() {
                    @Override
                    public void accept(Topic topic) throws Exception {
                        if (getView() == null) { // 没有链接视图,无法做操作
                            return;
                        }
                        getView().onSuccess(topic);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (getView() == null) {
                            return;
                        }
                        getView().onError(throwable);
                    }
                });
    }

    @Override
    public void startRequestMore() {

    }

    /**
     * 返回被观察者对象，去做网络请求
     * @return
     */
    @Override
    public Observable request() {
        return mService.getHotTopicDetail(mTopicId);
    }

    @Override
    public Observable requestMore() {
        return null;
    }

    /**
     * 获取到具体请求的值后，去开启网络请求操作
     * @param topicId
     */
    public void getTopicDetail(String topicId) {
        mTopicId = topicId;
        start();
    }

    protected void getTimeLine(String topicId) {
        new TimeLineAsyncTask(topicId, this).execute();
    }

    /**
     * 异步任务
     */
    public static class TimeLineAsyncTask extends AsyncTask<Void, Void, Document> {
        private String mTopicId;
        private TopicDetailPresenter mPresenter;

        public TimeLineAsyncTask(String topicId, TopicDetailPresenter presenter) {
            mTopicId = topicId;
            mPresenter = presenter;
        }

        @Override
        protected Document doInBackground(Void... voids) {
            Document document = null;
            try {
                document = Jsoup.connect(Constant.TOPIC_DETAIL_URL + mTopicId).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            if (document == null) {
                return;
            }
            Elements timelineContainer = document.getElementsByClass(
                    "timeline__container__3JHS8 timeline__container--PC__1D1r7");
            if (timelineContainer == null || timelineContainer.select("li").isEmpty()) {
                return;
            }
            List<TopicTimeLine> timeLines = new ArrayList<>();
            for (Element liElement : timelineContainer.select("li")) {
                TopicTimeLine timeLine = new TopicTimeLine();
                timeLine.date = liElement.getElementsByClass("date-item__1io1R").text();
                Element contentElement = liElement.getElementsByClass("content-item___3KfMq").get(0);
                timeLine.content = contentElement.getElementsByTag("a").get(0).text();
                timeLine.url = contentElement.getElementsByTag("a").get(0).attr("href");
                timeLines.add(timeLine);
            }
            if (mPresenter.getView() != null) {
                mPresenter.getView().onGetTimeLineSuccess(timeLines);
            }
            mPresenter = null;
        }
    }
}
