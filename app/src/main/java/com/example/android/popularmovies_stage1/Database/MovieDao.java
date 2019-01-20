package com.example.android.popularmovies_stage1.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmovies_stage1.Model.Movie;


import java.util.List;


@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM movie_table WHERE movie_id=:movieId")
    void deleteMovie(int movieId);

    @Query("SELECT * FROM movie_table ORDER BY movie_id ASC")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movie_table WHERE movie_id    =:movieId")
    LiveData<Movie> getMovie(int movieId);
}
