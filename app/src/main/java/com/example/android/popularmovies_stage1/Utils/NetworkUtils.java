package com.example.android.popularmovies_stage1.Utils;

import android.net.Uri;

import com.example.android.popularmovies_stage1.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the movies db servers.
 */
public class NetworkUtils {


    /**
     * @return the url link to access the most popular movies from movies db web server.
     */
    public static URL buildPopularMovieURL() {
        Uri builtUri = Uri.parse(Constants.POPULAR_MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * @return the url link to access the most top rated movies from movies db web server.
     */
    public static URL buildTopRatedMovieURL() {
        Uri builtUri = Uri.parse(Constants.TOP_RATED_MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * @return the url link to access the reviews for the movies from movies db web server.
     */
    public static URL buildMovieReviewQueryURL(String movieId) {
        Uri builtUri = Uri.parse(Constants.MOVIE_REVIEW_AND_TRAILER_BASE_URL).buildUpon()
                .appendEncodedPath(movieId)
                .appendEncodedPath(Constants.MOVIE_REVIEW_PATH)
                .appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * @return the url link to access the trailer for the movies from movies db web server.
     */
    public static URL buildMovieTrailerQueryURL(String movieId) {
        Uri builtUri = Uri.parse(Constants.MOVIE_REVIEW_AND_TRAILER_BASE_URL).buildUpon()
                .appendEncodedPath(movieId)
                .appendEncodedPath(Constants.MOVIE_TRAILER_PATH)
                .appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * @return the url link in string format to access the thumbnail for the movies from movies db web server.
     */
    public static String buildMovieImageURLString(String imagePath) {
        Uri builtUri = Uri.parse(Constants.IMAGE_BASE_URL).buildUpon()
                .appendEncodedPath(imagePath)
                .build();
        return builtUri.toString();
    }

    /**
     * @return the url link in string format to access the thumbnail for the movies from movies db web server.
     */
    public static String buildMovieTrailerThumbnailURLString(String movieId) {
        Uri builtUri = Uri.parse(Constants.MOVIE_TRAILER_THUMBNAIL_URL_PART_ONE).buildUpon()
                .appendEncodedPath(movieId)
                .appendEncodedPath(Constants.MOVIE_TRAILER_THUMBNAIL_URL_PART_TWO)
                .build();
        return builtUri.toString();
    }

    /**
     * @return the url link to access the trailer video for the movies from youtube.com.
     */
    public static String buildMovieTrailerVideoURL(String trailerKey) {
        Uri builtUri = Uri.parse(Constants.MOVIE_YOUTUBE_TRAILER_URL).buildUpon()
                .appendQueryParameter(Constants.MOVIE_TRAILER_KEY_PARAM, trailerKey)
                .build();
        return builtUri.toString();


    }

    /**
     * @param url this link is used to access the movies db servers to receive the json response.
     * @return the contents of the Http response
     * @throws IOException related to network and stream reading.
     */

    public static String getResponseFromHttpURL(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }

        } finally {
            httpURLConnection.disconnect();
        }

    }
}
