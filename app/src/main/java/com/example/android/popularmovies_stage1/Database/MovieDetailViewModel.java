package com.example.android.popularmovies_stage1.Database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies_stage1.Model.Movie;

public class MovieDetailViewModel extends ViewModel {
    private final LiveData<Movie> movie;

    public MovieDetailViewModel(MovieDatabase movieDatabase, int id) {
        movie = movieDatabase.movieDao().getMovie(id);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
