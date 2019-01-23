package com.example.android.popularmovies_stage1.Utils;


import com.example.android.popularmovies_stage1.Constants;
import com.example.android.popularmovies_stage1.Model.Movie;
import com.example.android.popularmovies_stage1.Model.MovieReview;
import com.example.android.popularmovies_stage1.Model.MovieTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


/**
 * The JSONUtils class will be used to parse the required data from the JSON response .
 */
public final class JSONUtils {
    //Constructor is made private, so this class will not be instantiated
private JSONUtils(){}

    /**
     * @param JSONresponse the JSON file received from the movies db web server.
     * @return the values parsed from the json response is used to form the Movie object.
     */

    public static ArrayList<Movie> parseMovieDetailsFromJSON(String JSONresponse) {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        try {
            //extract object
            JSONObject movieObject = new JSONObject(JSONresponse);
            // extract  results array
            JSONArray results = movieObject.optJSONArray(Constants.RESULTS);
            //looping to extract objects stored in results array
            for (int i = 0; i < results.length(); i++) {
                // extract objects stored in results array
                JSONObject jsonObject = results.optJSONObject(i);
                //extract movie id from jsonObject
                int movieId = jsonObject.optInt(Constants.MOVIE_ID);
                //extract user rating from jsonObject
                String userRating = jsonObject.optString(Constants.USER_RATING);
                //extract title from jsonObject
                String title = jsonObject.optString(Constants.TITLE);
                //extract plot synopsis from jsonObject
                String plotSynopsis = jsonObject.optString(Constants.PLOT_SYNOPSIS);
                //extract release date from jsonObject
                String releaseDate = jsonObject.optString(Constants.RELEASE_DATE);
                // extract image path from jsonObject
                String imagePath = jsonObject.optString(Constants.IMAGE_PATH);
                // extract movie thumbnail using buildMovieImageURLString method of NetworkUtils class
                String imageUrl = NetworkUtils.buildMovieImageURLString(imagePath);
                Movie movie = new Movie(title, userRating, releaseDate, imageUrl, plotSynopsis, movieId);
                movieArrayList.add(movie);


            }
            return movieArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param JsonResponse the JSON file received from the movies db web server.
     * @return the values parsed from the json response is used to form the Movie trailer object.
     */
    public static ArrayList<MovieTrailer> parseMovieTrailerFromJSON(String JsonResponse) {
        ArrayList<MovieTrailer> trailerArrayList = new ArrayList<>();
        try {
            // extract trailer object
            JSONObject trailerObject = new JSONObject(JsonResponse);
            // extract array
            JSONArray results = trailerObject.optJSONArray(Constants.RESULTS);
            // check if there any objects in the JSONArray.
            if (results.length() != 0) {
                // looping to extract objects stored in array
                for (int i = 0; i < results.length(); i++) {
                    // extract objects stored in results array
                    JSONObject jsonObject = results.optJSONObject(i);
                    // extract trailer name from jsonObject
                    String trailerName = jsonObject.optString(Constants.MOVIE_TRAILER_NAME);
                    // extract trailer key from jsonObject
                    String trailerKey = jsonObject.optString(Constants.MOVIE_TRAILER_YOUTUBE_URL_KEY);
                    //extract trailer website name form jsonObject
                    String trailerSiteName = jsonObject.optString(Constants.MOVIE_TRAILER_SITE_NAME);
                    //build the youtube url for the trailer video using the buildMovieTrailerVideoUrl method using NetworkUtils.java
                    String trailerLink = NetworkUtils.buildMovieTrailerVideoURL(trailerKey);
                    //build the youtube THUMBNAIL url for the trailer video using the buildMovieTrailerVideoUrl method using NetworkUtils.java
                    String thumbnailLink = NetworkUtils.buildMovieTrailerThumbnailURLString(trailerKey);
                    MovieTrailer movieTrailer = new MovieTrailer(trailerName, trailerKey, trailerSiteName, trailerLink, thumbnailLink);

                    trailerArrayList.add(movieTrailer);
                }

                return trailerArrayList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param JsonResponse the JSON file received from the movies db web server.
     * @return the values parsed from the json response is used to form the Movie review object.
     */
    public static ArrayList<MovieReview> parseMovieReviewFromJSON(String JsonResponse) {
        ArrayList<MovieReview> reviewArrayList = new ArrayList<>();
        try {
            // extract review object
            JSONObject reviewObject = new JSONObject(JsonResponse);
            // extract array
            JSONArray results = reviewObject.optJSONArray(Constants.RESULTS);
            // check if there any objects in the JSONArray.
            if (results.length() != 0) {
                // looping to extract objects stored in array
                for (int i = 0; i < results.length(); i++) {
                    // extract objects stored in results array
                    JSONObject jsonObject = results.optJSONObject(i);
                    // extract author name from jsonObject
                    String author = jsonObject.optString(Constants.MOVIE_REVIEW_AUTHOR);
                    // extract content from jsonObject
                    String content = jsonObject.optString(Constants.MOVIE_REVIEW_CONTENT);
                    MovieReview movieReview = new MovieReview(author, content);
                    reviewArrayList.add(movieReview);
                }

                return reviewArrayList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}