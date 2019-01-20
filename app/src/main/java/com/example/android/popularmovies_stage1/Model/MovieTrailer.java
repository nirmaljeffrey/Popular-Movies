package com.example.android.popularmovies_stage1.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class MovieTrailer implements Parcelable {
    public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
    private final String mTrailerName;
    private final String mTrailerKey;
    private final String mTrailerSiteName;
    private final String mTrailerUrl;
    private final String mThumbnailUrl;


    public MovieTrailer(String trailerName, String trailerKey, String trailerSiteName, String trailerUrl, String thumbnailUrl) {
        mTrailerKey = trailerKey;
        mTrailerName = trailerName;
        mTrailerSiteName = trailerSiteName;
        mTrailerUrl = trailerUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    @SuppressWarnings("WeakerAccess")
    protected MovieTrailer(Parcel in) {
        mTrailerName = in.readString();
        mTrailerKey = in.readString();
        mTrailerSiteName = in.readString();
        mTrailerUrl = in.readString();
        mThumbnailUrl = in.readString();
    }

    public String getTrailerName() {
        return mTrailerName;
    }

    public String getTrailerKey() {
        return mTrailerKey;
    }

    public String getTrailerSiteName() {
        return mTrailerSiteName;
    }

    public String getTrailerUrl() {
        return mTrailerUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTrailerName);
        parcel.writeString(mTrailerKey);
        parcel.writeString(mTrailerSiteName);
        parcel.writeString(mTrailerUrl);
        parcel.writeString(mThumbnailUrl);
    }
}
