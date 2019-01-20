package com.example.android.popularmovies_stage1.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


@Entity(tableName = "movie_table")
public class Movie implements Parcelable {


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @ColumnInfo(name = "movie_id")
    private final int movieId;

    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "user_rating")
    private final String userRating;
    @ColumnInfo(name = "release_date")
    private final String releaseDate;
    @ColumnInfo(name = "image_path_url")
    private final String imagePath;
    @ColumnInfo(name = "plot")
    private final String plotSynopsis;
    @PrimaryKey(autoGenerate = true)
    private int dbId;

    // Constructor for initializing the Movie object
    public Movie(String title, String userRating, String releaseDate, String imagePath, String plotSynopsis, int movieId) {
        this.title = title;
        this.imagePath = imagePath;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
        imagePath = in.readString();
        plotSynopsis = in.readString();
        movieId = in.readInt();
    }

    public int getDbId() {
        return dbId;
    }


    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    //Method to get title
    public String getTitle() {
        return title;
    }

    //Method to get user rating
    public String getUserRating() {
        return userRating;
    }

    //Method to get release date
    public String getReleaseDate() {
        return releaseDate;
    }

    //Method to get image path
    public String getImagePath() {
        return imagePath;
    }

    //Method to get Movie Id
    public int getMovieId() {
        return movieId;
    }

    // Method to get plot synopsis
    public String getPlotSynopsis() {
        return plotSynopsis;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(userRating);
        parcel.writeString(releaseDate);
        parcel.writeString(imagePath);
        parcel.writeString(plotSynopsis);
        parcel.writeInt(movieId);
    }
}
