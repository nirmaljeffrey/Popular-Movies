package com.example.android.popularmovies_stage1.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.popularmovies_stage1.Model.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {


    private final LiveData<List<Movie>> movieList;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase mDb = MovieDatabase.getInstance(application);
        movieList = mDb.movieDao().getAllMovies();
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

}
