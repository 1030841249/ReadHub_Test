package com.zdh.readhub.network;

import android.util.Log;

import com.zdh.readhub.app.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zdh on 2018/5/30.
 */

public class ApiService {

    private static final String TAG = "retrofit";
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d(TAG, message);
                }
            })
            ).build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.API_HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static HotTopicService createHotTopicService() {
        return retrofit.create(HotTopicService.class);
    }

    public static NewsService createNewsService() {
        return retrofit.create(NewsService.class);
    }

    public static TechNewsService createTechNewsService() {
        return retrofit.create(TechNewsService.class);
    }

}
