package com.zdh.readhub.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.zdh.readhub.model.Topic;

import java.util.List;

/**
 * Created by zdh on 2018/5/31.
 */

@Dao
public interface TopicDao {

    @Query("SELECT * FROM Topic")
    public List<Topic> getAllTopic();

    @Query("SELECT * FROM Topic WHERE id= :topicId")
    public Topic getTopic(String topicId);

    @Insert
    public void insertTopic(Topic... topic);

    @Delete
    public void deleteTopic(Topic topic);
}
