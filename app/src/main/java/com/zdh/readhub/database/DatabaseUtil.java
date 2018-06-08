package com.zdh.readhub.database;

import android.app.Activity;
import android.util.Log;

import com.zdh.readhub.database.dao.NewsDao;
import com.zdh.readhub.database.dao.TopicDao;
import com.zdh.readhub.model.News;
import com.zdh.readhub.model.Topic;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.functions.Consumer;

/**
 * Created by zdh on 2018/5/31.
 */

public class DatabaseUtil {
    private static final String TAG = "databaseutil";
    private static ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private static NewsDao mNewsDao = ReadhubDatabase.getInstance().getNewsDao();
    private static TopicDao mTopicDao = ReadhubDatabase.getInstance().getTopicDao();
    
    public static void insert(final Topic topic) {
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                if (mTopicDao.getTopic(topic.id) == null) {
                    mTopicDao.insertTopic(topic);
                    Log.d(getClass().getSimpleName(), "Topic" + topic.id + "insert success");

                } else {
                    Log.d(getClass().getSimpleName(), "Topic" + topic.id + "already success");
                }
            }
        });
    }
    
    public static void insert(final News news) {
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                if (mNewsDao.getNews(news.id) == null) {
                    mNewsDao.insertNews(news);
                    Log.d(getClass().getSimpleName(), "News " + news.id + " insert success");
                } else {
                    Log.d(getClass().getSimpleName(), "News " + news.id + " already success");
                }
            }
        });
    }

    public static <T> boolean isExist(Class<T> tClass, String id) {
        return get(tClass, id) != null;
    }

    private static <T> Object get(final Class<T> tClass, final String id) {
        try {
            return mExecutor.submit(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    if (Topic.class.equals(tClass)) {
                        return (T) mTopicDao.getTopic(id);
                    } else if (News.class.equals(tClass)) {
                        return (T) mNewsDao.getNews(id);
                    }
                    return null;
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param tClass
     * @param activity
     * @param consumer 观察者事件
     * @param <T>
     */
    public static <T> void getAll(final Class<T> tClass, final Activity activity,
                                  final Consumer<List<T>> consumer) {
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                final List<T> list;
                if (Topic.class.equals(tClass)) {
                    list = (List<T>) mTopicDao.getAllTopic();
                } else if (News.class.equals(tClass)) {
                    list = (List<T>) mNewsDao.getAllNews();
                } else {
                    list = null;
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            consumer.accept(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public static void delete(final Topic topic) {
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                if (mTopicDao.getTopic(topic.id) == null) {
                    Log.d(getClass().getSimpleName(), "News " + topic.id + " doesn't existed");
                } else {
                    mTopicDao.deleteTopic(topic);
                    Log.d(getClass().getSimpleName(), "News " + topic.id + " already deleted");
                }
            }
        });
    }

    public static void delete(final News news) {
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                if (mNewsDao.getNews(news.id) == null) {
                    Log.d(getClass().getSimpleName(), "News " + news.id + " doesn't deleted");
                } else {
                    mNewsDao.deleteNews(news);
                    Log.d(getClass().getSimpleName(), "News " + news.id + " already deleted");
                }
            }
        });
    }
}
