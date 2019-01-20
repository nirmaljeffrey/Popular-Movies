package com.example.android.popularmovies_stage1;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies_stage1.Adapter.MovieAdapter;

import com.example.android.popularmovies_stage1.Database.MovieViewModel;
import com.example.android.popularmovies_stage1.Model.Movie;
import com.example.android.popularmovies_stage1.Utils.GridLayoutUtils;
import com.example.android.popularmovies_stage1.Utils.JSONUtils;
import com.example.android.popularmovies_stage1.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.onMovieItemClickLister {
    private TextView movieListTitle;
    private RecyclerView recyclerView;
    private TextView errorMessageTextView;
    private ProgressBar loadingProgressBar;
    private MenuItem mostPopular;
    private MenuItem topRated;
    private MenuItem favourite;
    private RecyclerView.LayoutManager layoutManager;
    private Parcelable mListState;
    private MovieAdapter adapter;
    private MovieViewModel movieViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        //  This text view is used to display movie list title
        movieListTitle = findViewById(R.id.movie_list_title);
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
        layoutManager = new GridLayoutManager(this, numberOfColumns);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mListState = layoutManager.onSaveInstanceState();
        outState.putParcelable(Constants.LIST_STATE_KEY, mListState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {


            mListState = savedInstanceState.getParcelable(Constants.LIST_STATE_KEY);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        layoutManager.onRestoreInstanceState(mListState);
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
     *
     * @param movie object is passed into method.
     */
    @Override
    public void onClickItem(Movie movie) {

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.MOVIE_INTENT_EXTRA_ID, movie);


        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.xml is inflated
        getMenuInflater().inflate(R.menu.settings_menu, menu);

        mostPopular = menu.findItem(R.id.menu_most_popular);
        topRated = menu.findItem(R.id.menu_top_rated);
        favourite = menu.findItem(R.id.menu_favourites);
        mostPopular.setEnabled(false);

        return true;
    }

    // Enabling and disabling menu items when the app is either online nor offline
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // most_popular menu item is assigned to reference menu item mostPopularOption
        MenuItem mostPopularOption = menu.findItem(R.id.menu_most_popular);
        // top_rated menu option is assigned to reference menu item topRatedOption
        MenuItem topRatedOption = menu.findItem(R.id.menu_top_rated);


        if (!isOnline()) {
            mostPopularOption.setVisible(false);
            topRatedOption.setVisible(false);


        } else {
            mostPopularOption.setVisible(true);
            topRatedOption.setVisible(true);

        }
        return super.onPrepareOptionsMenu(menu);
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
                mostPopular.setEnabled(false);
                // menu item top_rated is enabled
                topRated.setEnabled(true);
                // menu item favourite is disabled
                favourite.setEnabled(true);

                break;
            case R.id.menu_top_rated:
                // Movie data is fetched into the foreground activity
                loadMovieData(NetworkUtils.buildTopRatedMovieURL());
                //setText() method is called on movieListTitle
                movieListTitle.setText(R.string.main_top_rated);
                // menu item top_rated is disabled
                topRated.setEnabled(false);
                // menu item most_popular is enabled
                mostPopular.setEnabled(true);
                // menu item favourite is disabled
                favourite.setEnabled(true);
                break;
            case R.id.menu_favourites:

                movieViewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        if (movies != null) {
                            final ArrayList<Movie> arrayList = new ArrayList<>(movies);
                            if (arrayList.isEmpty()) {
                                errorMessageTextView.setText(R.string.main_movie_favourites);
                                showErrorMessage();
                            }
                            adapter.setMovieData(arrayList);
                        }

                    }
                });
                //setText() method is called on movieListTitle
                movieListTitle.setText(R.string.menu_favourites);
                // menu item top_rated is disabled
                topRated.setEnabled(true);
                // menu item most_popular is disabled
                mostPopular.setEnabled(true);
                // menu item favourite is enabled
                favourite.setEnabled(false);
                break;


        }
        return super.onOptionsItemSelected(item);
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

    /**
     * This method will get the user's preferred option, and then tell some
     * background method to get the movie data in the background.
     *
     * @param url preferred by the user
     */
    private void loadMovieData(URL url) {

        showMovieData();
        if (isOnline()) {

            new MovieTask().execute(url);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.main_no_internet_connection_toast), Toast.LENGTH_SHORT).show();
        }
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
                errorMessageTextView.setText(R.string.main_error_message);
                showErrorMessage();

            } else {


                adapter.setMovieData(arrayList);
                showMovieData();


            }

        }
    }
}