package com.example.android.popularmovies_stage1;

public class Constants {
    // Enter your api key generated from www.themoviedb.org website.
    public static final String API_KEY="";


    // constants used in main activity and detail activity
    public static final String IS_ONLINE = "/system/bin/ping -c 1 8.8.8.8";
    public static final String MOVIE_INTENT_EXTRA_ID="movie_parcel";

    // constants used in DetailActivity
    public static final String MIME_TYPE_FOR_SHARE_INTENT="text/plain";


    //Constants used in main activity
    public static final String LIST_STATE_KEY="list_key";


    //Constants used in JSONUtils.java
    // Constants used for parsing movie details from json
    public static final String RESULTS = "results";
    public static final String USER_RATING="vote_average";
    public static final String TITLE = "title";
    public static final String PLOT_SYNOPSIS ="overview";
    public static final String RELEASE_DATE="release_date";
    public static final String IMAGE_PATH ="poster_path";
    public static final String MOVIE_ID = "id";
    //constants used for parsing movie trailer from json
    public static final String MOVIE_TRAILER_NAME="name";
    public static final String MOVIE_TRAILER_YOUTUBE_URL_KEY="key";
    public static final String MOVIE_TRAILER_SITE_NAME="site";
    //constants used for parsing movie reviews from json
    public static final String MOVIE_REVIEW_AUTHOR="author";
    public static final String MOVIE_REVIEW_CONTENT="content";

    //Constants used in NetworkUtils.java
    //Constants used for performing the network operations.
    public static final String POPULAR_MOVIE_BASE_URL="http://api.themoviedb.org/3/movie/popular";
    public static final String TOP_RATED_MOVIE_BASE_URL="http://api.themoviedb.org/3/movie/top_rated";
    public static final String MOVIE_REVIEW_AND_TRAILER_BASE_URL = "https://api.themoviedb.org/3/movie";
    public static final String MOVIE_REVIEW_PATH="reviews";
    public static final String MOVIE_TRAILER_PATH="videos";

    public final static String API_KEY_PARAM = "api_key";
    // Base url used for accessing images online
    public static final String IMAGE_BASE_URL="http://image.tmdb.org/t/p/w342";
    // Base url used for sharing trailer video links online
    public static final String MOVIE_YOUTUBE_TRAILER_URL="https://www.youtube.com/watch";
    public static final String MOVIE_TRAILER_KEY_PARAM="v";
    public static final String MOVIE_TRAILER_THUMBNAIL_URL_PART_ONE= "https://img.youtube.com/vi/";
    public static final String MOVIE_TRAILER_THUMBNAIL_URL_PART_TWO="0.jpg";

    // Constants used in MovieDatabase.java
    public static final String DATABASE_NAME="movie_database";

}
