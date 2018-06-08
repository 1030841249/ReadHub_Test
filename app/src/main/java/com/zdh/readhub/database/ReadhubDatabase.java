package com.zdh.readhub.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.zdh.readhub.app.ReadhubApplication;
import com.zdh.readhub.database.dao.NewsDao;
import com.zdh.readhub.database.dao.TopicDao;
import com.zdh.readhub.model.News;
import com.zdh.readhub.model.Topic;

/**
 * Created by zdh on 2018/5/31.
 */

@Database(entities = {Topic.class, News.class}, version = 1, exportSchema = false)
public abstract class ReadhubDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "Readhub";

    public static ReadhubDatabase getInstance() {
        return DatabaseHolder.READHUB_DATABASE;
    }

    public abstract TopicDao getTopicDao();

    public abstract NewsDao getNewsDao();

    private static class DatabaseHolder {
        private static ReadhubDatabase READHUB_DATABASE =
                Room.databaseBuilder(ReadhubApplication.getmContext(), ReadhubDatabase.class,
                        DATABASE_NAME)
                        .build();

    }
}
