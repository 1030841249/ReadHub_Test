package com.zdh.readhub.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.zdh.readhub.model.News;

import java.util.List;

import retrofit2.http.DELETE;

/**
 * Created by zdh on 2018/5/31.
 */

@Dao
public interface NewsDao {

    @Query("SELECT * FROM News")
    public List<News> getAllNews();

    @Query("SELECT * FROM News WHERE id= :newsId")
    public News getNews(String newsId);

    @Insert
    public void insertNews(News... news);

    @Delete
    public void deleteNews(News news);
}
