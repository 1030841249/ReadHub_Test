package com.zdh.readhub.network;

import com.zdh.readhub.model.ApiData;
import com.zdh.readhub.model.News;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zdh on 2018/5/31.
 * 技术新闻
 */

public interface TechNewsService {

    @GET("technews")
    Observable<ApiData<News>> getTechNews();

    @GET("technews")
    Observable<ApiData<News>> getMoreTechNews(@Query("lastCursor") String lastCursor,
                                              @Query("pageSize") int pageSize);

}
