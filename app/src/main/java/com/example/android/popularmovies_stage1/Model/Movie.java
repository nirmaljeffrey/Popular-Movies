package com.example.android.popularmovies_stage1.Model;

public class Movie {
private final String mTitle;
private final String mUserRating;
private final String mReleaseDate;
private final String mImagePath;
private final String mPlotSynopsis;
// Constructor for initializing the Movie object
    public Movie(String title,String userRating,String releaseDate,String imagePath,String plotSynopsis){
mTitle=title;
mImagePath=imagePath;
mPlotSynopsis=plotSynopsis;
mUserRating=userRating;
mReleaseDate=releaseDate;
    }

    //Method to get title
    public String getTitle(){
        return mTitle;
    }

    //Method to get user rating
    public String getUserRating(){
        return mUserRating;
    }

    //Method to get release date
    public String getReleaseDate(){
        return mReleaseDate;
    }

    //Method to get image path
    public String getImageUrl(){
        return mImagePath;
    }

    // Method to get plot synopsis
    public String getPlotSynopsis(){
        return mPlotSynopsis;
    }
}
