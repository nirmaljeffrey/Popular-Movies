package com.example.android.popularmovies_stage1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReview implements Parcelable {
    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };
    private final String mAuthor;
    private final String mContent;

    public MovieReview(String author, String content) {
        mAuthor = author;
        mContent = content;
    }

    @SuppressWarnings("WeakerAccess")
    protected MovieReview(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAuthor);
        parcel.writeString(mContent);
    }
}
