package com.example.android.popularmovies_stage1.Database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;


public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieDatabase mDb;
    private final int mId;

    public MovieDetailViewModelFactory(MovieDatabase movieDatabase, int id) {
        mDb = movieDatabase;
        mId = id;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailViewModel(mDb, mId);
    }
}
