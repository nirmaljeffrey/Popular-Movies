package com.example.android.popularmovies_stage1.Utils;

import com.example.android.popularmovies_stage1.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * These utilities will be used to parse the required data from the JSON response .
 */
public  class JSONUtils {

    private static final String RESULTS = "results";
    private static final String USER_RATING="vote_average";
    private static final String TITLE = "title";
    private static final String PLOT_SYNOPSIS ="overview";
    private static final String RELEASE_DATE="release_date";
    private static final String IMAGE_PATH ="poster_path";
    // Base url used for accessing images online
    private static final String IMAGE_BASE_URL="http://image.tmdb.org/t/p/w342/";
    /**
     *
     * @param JSONresponse the JSON file received from the movies db web server.
     * @return  the values parsed from the json response is used to form the Movie object.
     */

    public static ArrayList<Movie> parseMovieDetailsFromJSON(String JSONresponse) {
         ArrayList<Movie> movieArrayList= new ArrayList<>();
        try {
            //extract object
            JSONObject movieObject = new JSONObject(JSONresponse);
            // extract  results array
            JSONArray results = movieObject.optJSONArray(RESULTS);
            //looping to extract objects stored in results array
            for (int i = 0; i < results.length(); i++) {
                // extract objects stored in results array
                 JSONObject jsonObject = results.optJSONObject(i);
                 //extract user rating form jsonObject
                String userRating = jsonObject.optString(USER_RATING);
                //extract title from jsonObject
                String title = jsonObject.optString(TITLE);
                //extract plot synopsis from jsonObject
                String plotSynopsis =jsonObject.optString(PLOT_SYNOPSIS);
                //extract release date from jsonObject
                String releaseDate = jsonObject.optString(RELEASE_DATE);
                // extract image path from jsonObject
                String  imagePath = jsonObject.optString(IMAGE_PATH);
                //String concatenation of base url and relative path url of movie posters
                String imageUrl = IMAGE_BASE_URL+imagePath;
                Movie movie = new Movie(title,userRating,releaseDate,imageUrl,plotSynopsis);
                movieArrayList.add(movie);


            }
            return movieArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return null;
    }
}