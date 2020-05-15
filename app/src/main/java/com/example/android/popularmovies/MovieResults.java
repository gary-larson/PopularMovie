package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Class to hold data of each movie
 */
class MovieResults implements Parcelable {
    // Declare member variables
    private String mPosterPath;
    private String mBackDropPath;
    private String mOverview;
    private Date mReleaseDate;
    private String mOriginalTitle;
    private String mTitle;
    private double mVoteAverage;

    /**
     * No Argument Constructor
     */
    MovieResults() {
    }

    /**
     * Method to read data from parcel
     * @param in parcel to read from
     */
    private MovieResults (Parcel in) {
        mPosterPath = in.readString();
        mBackDropPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = new Date(in.readLong());
        mOriginalTitle = in.readString();
        mTitle = in.readString();
        mVoteAverage = in.readDouble();
    }

    /**
     * Method to provide a description code of the parcel
     * @return default
     */
    @Override
    public int describeContents() {return 0;}

    /**
     * Method to data to the parcel
     * @param parcel to write to
     * @param i position of data
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPosterPath);
        parcel.writeString(mBackDropPath);
        parcel.writeString(mOverview);
        parcel.writeLong(mReleaseDate.getTime());
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mTitle);
        parcel.writeDouble(mVoteAverage);
    }

    /**
     * Creator to initialize parcel
     */
    public final Parcelable.Creator<MovieResults> CREATOR = new Parcelable.Creator<MovieResults>() {
        @Override
        public MovieResults createFromParcel(Parcel parcel) {return new MovieResults(parcel);}

        @Override
        public MovieResults[] newArray(int i) {return new MovieResults[i];}
    };

    /**
     * Method to get the poster Path
     * @return the Poster path or null
     */
    String getPosterPath() {
        return mPosterPath;
    }

    /**
     * Method to set the Poster Path
     * @param mPosterPath of the movie
     */
    void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    /**
     * Method to get the poster Path
     * @return the Poster path or null
     */
    String getBackDropPath() {
        return mBackDropPath;
    }

    /**
     * Method to set the Poster Path
     * @param mBackDropPath of the movie
     */
    void setBackDropPath(String mBackDropPath) {
        this.mBackDropPath = mBackDropPath;
    }

    /**
     * Method to get Overview
     * @return Overview
     */
    String getOverview() {
        return mOverview;
    }

    /**
     * Method to set Overview
     * @param mOverview to set
     */
    void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    /**
     * Method to get Release Date
     * @return Release Date
     */
    Date getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Method to set Release Date
     * @param mReleaseDate to set
     */
    void setReleaseDate(Date mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    /**
     * Method to get Original Title
     * @return Original Title
     */
    String getOriginalTitle() {
        return mOriginalTitle;
    }

    /**
     * Method to set Original Title
     * @param mOriginalTitle to set
     */
    void setOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    /**
     * Method to get Title
     * @return Title
     */
    String getTitle() {
        return mTitle;
    }

    /**
     * Method to set Title
     * @param mTitle to set
     */
    void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * Method to get Vote Average
     * @return Vote Average
     */
    double getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Method to set Vote Average
     * @param mVoteAverage to set
     */
    void setVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

}
