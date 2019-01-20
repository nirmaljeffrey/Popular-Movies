package com.example.android.popularmovies_stage1.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.popularmovies_stage1.Constants;
import com.example.android.popularmovies_stage1.Model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static MovieDatabase instance;

    public static MovieDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, Constants.DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }

    public abstract MovieDao movieDao();
}
