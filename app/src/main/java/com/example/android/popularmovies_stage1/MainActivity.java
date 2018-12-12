package com.example.android.popularmovies_stage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies_stage1.Model.Movie;
import com.example.android.popularmovies_stage1.Utils.GridLayoutUtils;
import com.example.android.popularmovies_stage1.Utils.JSONUtils;
import com.example.android.popularmovies_stage1.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public  class MainActivity extends AppCompatActivity implements MovieAdapter.onMovieItemClickLister{
    private TextView movieListTitle;
    private RecyclerView recyclerView;
    private TextView errorMessageTextView;
    private ProgressBar loadingProgressBar;
    private MenuItem mostPopularOption;
    private MenuItem topRatedOption;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  This text view is used to display movie list title
         movieListTitle =findViewById(R.id.movie_list_title);
         movieListTitle.setText(R.string.main_most_popular);
        //  This text view is used to display error message
        errorMessageTextView = findViewById(R.id.tv_error_message);
        //  This progress bar is used as a loading indicator while performing network requests
        loadingProgressBar = findViewById(R.id.pb_loading_bar);

        recyclerView = findViewById(R.id.rv_movie_list);

        recyclerView.setHasFixedSize(true);
        adapter = new MovieAdapter(this);

        recyclerView.setAdapter(adapter);
        // calculate the number of columns to be displayed in a screen using the method from GridLayoutUtils class
        int numberOfColumns = GridLayoutUtils.calculateNumberOfColumns(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);

       // The movie data is loaded into foreground activity by using the loadMovieData(URL url) method
        URL popularMoviesUrl = NetworkUtils.buildPopularMovieURL();
        loadMovieData(popularMoviesUrl);


    }
 //This method will make the View for the movies data visible and
 // hides the error message.
    private void showMovieData() {
        errorMessageTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);


    }
    //This method will make the View for the error message visible and
    // hides the movies data.
    private void showErrorMessage() {
        recyclerView.setVisibility(View.GONE);
        errorMessageTextView.setVisibility(View.VISIBLE);

    }

    /**
     * This method is overridden by our MainActivity class in order to handle RecyclerView item
     * clicks.
      * @param movie object is passed into method.
     */
    @Override
    public void onClickItem(Movie movie) {

        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
      intent.putExtra("movie_title",movie.getTitle());
      intent.putExtra("movie_release_date",movie.getReleaseDate());
      intent.putExtra("movie_rating",movie.getUserRating());
      intent.putExtra("movie_plot",movie.getPlotSynopsis());
      intent.putExtra("movie_imageUrl",movie.getImageUrl());
      startActivity(intent);
    }

    private class MovieTask extends AsyncTask<URL, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(URL... urls) {
            if (urls[0] != null) {
                String jsonResponse = "";
                try {
                    jsonResponse = NetworkUtils.getResponseFromHttpURL(urls[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return JSONUtils.parseMovieDetailsFromJSON(jsonResponse);

            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> arrayList) {
            loadingProgressBar.setVisibility(View.INVISIBLE);
            if (arrayList == null) {
                showErrorMessage();
                // Since there is no network, both options are not selected, so they are enabled.
                   mostPopularOption.setEnabled(true);
                   topRatedOption.setEnabled(true);
            } else {

                showMovieData();
                adapter.setMovieData(arrayList);


            }

        }
    }

    /**
     * This method will get the user's preferred option, and then tell some
     *  background method to get the movie data in the background.
     * @param url preferred by the user
     */
    private void loadMovieData(URL url) {
        showMovieData();
        new MovieTask().execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.xml is inflated
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        // most_popular menu item is assigned to reference menu item mostPopularOption
       mostPopularOption=menu.findItem(R.id.menu_most_popular);
       // most_popular menu item is disabled
       mostPopularOption.setEnabled(false);
       // top_rated menu option is assigned to reference menu item topRatedOption
       topRatedOption=menu.findItem(R.id.menu_top_rated);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.menu_most_popular:
                // Movie data is fetched into the foreground activity
                loadMovieData(NetworkUtils.buildPopularMovieURL());
                //setText() method is called on movieListTitle
                movieListTitle.setText(R.string.main_most_popular);
                // menu item most_popular is disabled
                mostPopularOption.setEnabled(false);
                // menu item top_rated is enabled
                topRatedOption.setEnabled(true);
                break;
            case R.id.menu_top_rated:
                // Movie data is fetched into the foreground activity
                loadMovieData(NetworkUtils.buildTopRatedMovieURL());
                //setText() method is called on movieListTitle
                movieListTitle.setText(R.string.main_top_rated);
                // menu item top_rated is disabled
                topRatedOption.setEnabled(false);
                // menu item most_popular is enabled
                mostPopularOption.setEnabled(true);
                 break;



        }
        return super.onOptionsItemSelected(item);
    }


}