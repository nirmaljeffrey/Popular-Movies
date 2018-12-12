package com.example.android.popularmovies_stage1.Utils;

import android.net.Uri;

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

private static final String POPULAR_MOVIE_BASE_URL="http://api.themoviedb.org/3/movie/popular";
private static final String TOP_RATED_MOVIE_BASE_URL="http://api.themoviedb.org/3/movie/top_rated";
// Enter your api key generated from www.themoviedb.org website.
private static final String API_KEY="Enter your API key here";


private final static String API_KEY_PARAM = "api_key";

    /**
     *
     * @return the url link to access the most popular movies from movies db web server.
     */
    public static URL buildPopularMovieURL(){
    Uri builtUri = Uri.parse(POPULAR_MOVIE_BASE_URL).buildUpon()
            .appendQueryParameter(API_KEY_PARAM,API_KEY)
            .build();
    URL url = null;
    try{
        url=new URL(builtUri.toString());
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
    return url;
}
    /**
     *
     * @return the url link to access the most top rated movies from movies db web server.
     */
    public static URL buildTopRatedMovieURL(){
        Uri builtUri = Uri.parse(TOP_RATED_MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM,API_KEY)
                .build();
        URL url=null;
        try{
            url=new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     *
     * @param url this link is used to access the movies db servers to receive the json response.
     * @return     the contents of the Http response
     * @throws IOException related to network and stream reading.
     */

    public static String getResponseFromHttpURL(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner=new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            if(scanner.hasNext()){
                return scanner.next();
            }else{
                return null;
            }

        }finally {
            httpURLConnection.disconnect();
        }

    }
}
