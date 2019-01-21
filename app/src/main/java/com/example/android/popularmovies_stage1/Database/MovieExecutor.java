package com.example.android.popularmovies_stage1.Database;


import android.os.Looper;
import android.support.annotation.NonNull;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Handler;


public class MovieExecutor {

    private static final Object LOCK = new Object();
    private static MovieExecutor instance;
    private final Executor diskIO;


    private MovieExecutor(Executor diskIO) {
        this.diskIO = diskIO;

    }

    public static MovieExecutor getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                instance = new MovieExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return instance;
    }

    public Executor diskIO() {
        return diskIO;
    }


}
