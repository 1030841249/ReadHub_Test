package com.zdh.readhub.network;

import com.zdh.readhub.model.ApiData;
import com.zdh.readhub.model.InstantReadData;
import com.zdh.readhub.model.Topic;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zdh on 2018/5/30.
 * 热门主题
 */

public interface HotTopicService {

    /**
     *
     * @return
     */
    @GET("topic")
    Observable<ApiData<Topic>> getHotTopic();

    @GET("topic")
    Observable<ApiData<Topic>> getMoreHotTopic(@Query("lastCursor") String lastCursor,
                                               @Query("pageSize") int pageSize);
    @GET("topic/{topic_id}")
    Observable<Topic> getHotTopicDetail(@Path("topic_id") String topicId);

    @GET("/topic/instantview")
    Observable<InstantReadData> getInstantRead(@Query("topicId") String topicId);
}
