package com.example.android.popularmovies_stage1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies_stage1.Adapter.ReviewAdapter;
import com.example.android.popularmovies_stage1.Adapter.TrailerAdapter;
import com.example.android.popularmovies_stage1.Database.MovieDatabase;
import com.example.android.popularmovies_stage1.Database.MovieDetailViewModel;
import com.example.android.popularmovies_stage1.Database.MovieDetailViewModelFactory;
import com.example.android.popularmovies_stage1.Database.MovieExecutor;
import com.example.android.popularmovies_stage1.Model.Movie;
import com.example.android.popularmovies_stage1.Model.MovieReview;
import com.example.android.popularmovies_stage1.Model.MovieTrailer;
import com.example.android.popularmovies_stage1.Utils.JSONUtils;
import com.example.android.popularmovies_stage1.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerClickListener {
    private RecyclerView trailerRecyclerView;
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private TextView trailerErrorMessage;
    private ProgressBar trailerLoadingBar;
    private TextView reviewErrorMessage;
    private ProgressBar reviewLoadingBar;
    private FloatingActionButton favouriteFab;
    private MovieDatabase mDb;
    private Movie movie;
    private boolean isClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        mDb = MovieDatabase.getInstance(this);
        //Method for instantiating the recycler view for trailer
        setTrailerRecyclerView(this);
        //Method for instantiating the recycler view for review
        setReviewRecyclerView();

        trailerErrorMessage = findViewById(R.id.tv_error_message_detail);
        trailerLoadingBar = findViewById(R.id.pb_loading_bar_detail);
        reviewLoadingBar = findViewById(R.id.review_loading_bar_detail);
        reviewErrorMessage = findViewById(R.id.tv_error_message_review);

        //  This text view is used to display movie's user rating
        TextView ratingTextView = findViewById(R.id.tv_user_rating_detail);
        //  This text view is used to display movie's storyline
        TextView plotTextView = findViewById(R.id.plot_detail);
        //  This text view is used to display movie's release date
        TextView releaseDateTextView = findViewById(R.id.tv_release_date_detail);
        // //  This image view is used to display movie's poster
        ImageView movieImageView = findViewById(R.id.iv_image_detail);
        //Receiving the intent from the main activity
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        movie = intent.getParcelableExtra(Constants.MOVIE_INTENT_EXTRA_ID);
        setTitle(movie.getTitle());

        releaseDateTextView.setText(movie.getReleaseDate());
        String rating = movie.getUserRating() + getString(R.string.detail_rating_value);
        ratingTextView.setText(rating);
        plotTextView.setText(movie.getPlotSynopsis());
        Picasso.get().load(movie.getImagePath()).into(movieImageView);
        String movieId = String.valueOf(movie.getMovieId());
        // Building URL to query movie db API for movies review using the buildMovieReviewQueryURL() method from NetworkUtils.java
        URL movieReviewQueryURL = NetworkUtils.buildMovieReviewQueryURL(movieId);
        // Building URL to query movie db API for movies trailers using the buildMovieTrailerQueryURL() method from NetworkUtils.java
        URL movieTrailerQueryURL = NetworkUtils.buildMovieTrailerQueryURL(movieId);
        loadTrailerData(movieTrailerQueryURL);
        loadReviewData(movieReviewQueryURL);
        favouriteFab = findViewById(R.id.fab);
        setUpFabButton(movie.getMovieId());
        favouriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFab();
            }
        });


    }


    /**
     * Method to perform insert and delete actions on database when the floating action button is pressed
     */
    private void onClickFab() {
        if (isClicked) {

            MovieExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.movieDao().deleteMovie(movie.getMovieId());
                    favouriteFab.setImageResource(R.drawable.ic_star_border);
                    Snackbar.make(findViewById(R.id.fab), getString(R.string.detail_snackbar_deleted_database), Snackbar.LENGTH_SHORT).show();
                }
            });

            isClicked = false;

        } else {


            MovieExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.movieDao().insertMovie(movie);
                    favouriteFab.setImageResource(R.drawable.ic_star_filled);
                    Snackbar.make(findViewById(R.id.fab), getString(R.string.detail_snackbar_insert_database), Snackbar.LENGTH_SHORT).show();

                }
            });
            isClicked = true;
        }
    }

    /***
     * Method is used to check the given movie is in database or not
     * @param id the movie id of  the given movie is taken as a parameter
     */
    private void setUpFabButton(final int id) {
        MovieDetailViewModelFactory mvf = new MovieDetailViewModelFactory(mDb, id);
        final MovieDetailViewModel mvm = ViewModelProviders.of(this, mvf).get(MovieDetailViewModel.class);
        mvm.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movieInDatabase) {

                if (movieInDatabase == null) {
                    isClicked = false;
                    favouriteFab.setImageResource(R.drawable.ic_star_border);

                } else if (movieInDatabase.getMovieId() == movie.getMovieId()) {
                    favouriteFab.setImageResource(R.drawable.ic_star_filled);
                    isClicked = true;

                }
            }
        });


    }

    //Method is used when the intent received from the main activity is null
    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.detail_close_on_error_toast), Toast.LENGTH_SHORT).show();
    }


    /**
     * This method is used to set layout manager and adapter for trailer recycler view
     *
     * @param context is casted into trailerClickListener in trailerAdapter object
     */
    private void setTrailerRecyclerView(Context context) {
        trailerRecyclerView = findViewById(R.id.trailer_recycler_view);
        trailerRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(mLayoutManagaer);
        trailerAdapter = new TrailerAdapter((TrailerAdapter.TrailerClickListener) context);
        trailerRecyclerView.setAdapter(trailerAdapter);
    }

    /**
     * This method is used to set layout manager and adapter for review recycler view
     */
    private void setReviewRecyclerView() {
        reviewRecyclerView = findViewById(R.id.review_recycler_view);
        reviewRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(reviewAdapter);
    }

    @Override
    public void onItemClick(MovieTrailer movieTrailer) {
        Uri youtubeUri = Uri.parse(movieTrailer.getTrailerUrl());
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, youtubeUri);
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

    @Override
    public void onShareClick(MovieTrailer movieTrailer) {
        String uri = movieTrailer.getTrailerUrl();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(Constants.MIME_TYPE_FOR_SHARE_INTENT);
        shareIntent.putExtra(Intent.EXTRA_TEXT, uri);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.detail_share_intent_title)));
    }

    //This method will make the View for the trailer data visible and
    // hides the error message.
    private void showTrailerData() {
        trailerErrorMessage.setVisibility(View.GONE);
        trailerRecyclerView.setVisibility(View.VISIBLE);


    }

    //This method will make the View for the error message visible and
    // hides the trailer data.
    private void showTrailerErrorMessage() {
        trailerRecyclerView.setVisibility(View.GONE);
        trailerErrorMessage.setVisibility(View.VISIBLE);

    }

    //This method will make the View for the error message visible and
    // hides the review data.
    private void showReviewErrorMessage() {
        reviewRecyclerView.setVisibility(View.GONE);
        reviewErrorMessage.setVisibility(View.VISIBLE);

    }

    //This method will make the View for the review data visible and
    // hides the error message.
    private void showReviewData() {
        reviewErrorMessage.setVisibility(View.GONE);
        reviewRecyclerView.setVisibility(View.VISIBLE);


    }

    /**
     * This method will get the user's preferred option, and then tell some
     * background method to get the movie data in the background.
     *
     * @param url preferred by the user
     */
    private void loadTrailerData(URL url) {
        showTrailerData();
        new TrailerFetchTask().execute(url);
    }

    /**
     * This method will get the user's preferred option, and then tell some
     * background method to get the movie data in the background.
     *
     * @param url preferred by the user
     */
    private void loadReviewData(URL url) {
        showReviewData();
        if (isOnline()) {
            new ReviewFetchTask().execute(url);
        } else {
            reviewErrorMessage.setText(getString(R.string.detail_Review_error_Internet));
            showReviewErrorMessage();

        }
    }

    /*** CHECKS IF ONLINE
     * https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out ***/
    private boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec(Constants.IS_ONLINE);
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    private class TrailerFetchTask extends AsyncTask<URL, Void, ArrayList<MovieTrailer>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            trailerLoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MovieTrailer> doInBackground(URL... urls) {
            if (urls[0] != null) {
                String jsonResponse;
                try {
                    jsonResponse = NetworkUtils.getResponseFromHttpURL(urls[0]);

                    return JSONUtils.parseMovieTrailerFromJSON(jsonResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieTrailer> trailerArrayList) {
            trailerLoadingBar.setVisibility(View.INVISIBLE);
            if (trailerArrayList == null) {
                showTrailerErrorMessage();
            } else {
                showTrailerData();
                trailerAdapter.setMovieTrailerData(trailerArrayList);

            }
        }
    }

    private class ReviewFetchTask extends AsyncTask<URL, Void, ArrayList<MovieReview>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            reviewLoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MovieReview> doInBackground(URL... urls) {
            if (urls[0] != null) {
                String jsonResponse;
                try {
                    jsonResponse = NetworkUtils.getResponseFromHttpURL(urls[0]);

                    return JSONUtils.parseMovieReviewFromJSON(jsonResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieReview> movieReviews) {
            reviewLoadingBar.setVisibility(View.INVISIBLE);

            if (movieReviews == null) {
                reviewErrorMessage.setText(R.string.detail_Review_error_message);
                showReviewErrorMessage();
            } else {
                showReviewData();
                reviewAdapter.setMovieReviewData(movieReviews);
            }
        }
    }


}
