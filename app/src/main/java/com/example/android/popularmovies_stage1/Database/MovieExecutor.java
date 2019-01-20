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
    private final Executor networkIO;
    private final Executor mainThread;

    private MovieExecutor(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static MovieExecutor getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                instance = new MovieExecutor(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());
            }
        }
        return instance;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());


        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);

        }
    }
}
