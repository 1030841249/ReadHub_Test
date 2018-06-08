package com.zdh.readhub.network;

import com.zdh.readhub.model.ApiData;
import com.zdh.readhub.model.News;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zdh on 2018/5/30.
 */

public interface NewsService {

    @GET("news")
    Observable<ApiData<News>> getNews();

    @GET("news")
    Observable<ApiData<News>> getMoreNews(@Query("lastCursor") String lastCursor,
                                          @Query("pageSize") int pageSize);

    @GET("blockchain")
    Observable<ApiData<News>> getBCNews();

    @GET("blockchain")
    Observable<ApiData<News>> getMoreBCNews(@Query("lastCursor") String lasrCursor,
                                            @Query("pageSize") int pageSize);
}
