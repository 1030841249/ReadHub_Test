package com.zdh.readhub.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.proguard.m;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created by zdh on 2018/5/30.
 */

public class ReadhubApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Bugly.init(mContext, Constant.BUGGLY_APP_ID, false);
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(@NonNull Exception e) {
                        // TODO handle exception
                    }
                }).install();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getmContext() {
        return mContext;
    }
}
