package com.larsonapps.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Class to hold data of each movie
 */
public class MovieResult implements Parcelable {
    // Declare member variables
    private String mPosterPath;
    private String mBackDropPath;
    private String mOverview;
    private Date mReleaseDate;
    private int movieID;
    private String mOriginalTitle;
    private String mTitle;
    private double mVoteAverage;

    /**
     * No Argument Constructor
     */
    public MovieResult() {
    }

    /**
     * Method to read data from parcel
     * @param in parcel to read from
     */
    private MovieResult(Parcel in) {
        mPosterPath = in.readString();
        mBackDropPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = new Date(in.readLong());
        movieID = in.readInt();
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
        parcel.writeInt(movieID);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mTitle);
        parcel.writeDouble(mVoteAverage);
    }

    /**
     * Creator to initialize parcel
     */
    public final Parcelable.Creator<MovieResult> CREATOR = new Parcelable.Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel parcel) {return new MovieResult(parcel);}

        @Override
        public MovieResult[] newArray(int i) {return new MovieResult[i];}
    };

    /**
     * Method to get the poster Path
     * @return the Poster path or null
     */
    public String getPosterPath() {
        return mPosterPath;
    }

    /**
     * Method to set the Poster Path
     * @param mPosterPath of the movie
     */
    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    /**
     * Method to get the poster Path
     * @return the Poster path or null
     */
    public String getBackDropPath() {
        return mBackDropPath;
    }

    /**
     * Method to set the Poster Path
     * @param mBackDropPath of the movie
     */
    public void setBackDropPath(String mBackDropPath) {
        this.mBackDropPath = mBackDropPath;
    }

    /**
     * Method to get Overview
     * @return Overview
     */
    public String getOverview() {
        return mOverview;
    }

    /**
     * Method to set Overview
     * @param mOverview to set
     */
    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    /**
     * Method to get Release Date
     * @return Release Date
     */
    public Date getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Method to set Release Date
     * @param mReleaseDate to set
     */
    public void setReleaseDate(Date mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    /**
     * Getter for movie id
     * @return movie id
     */
    public int getMovieID() {
        return movieID;
    }

    /**
     * Setter for movie id
     * @param movieID to set
     */
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    /**
     * Method to get Original Title
     * @return Original Title
     */
    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    /**
     * Method to set Original Title
     * @param mOriginalTitle to set
     */
    public void setOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    /**
     * Method to get Title
     * @return Title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Method to set Title
     * @param mTitle to set
     */
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * Method to get Vote Average
     * @return Vote Average
     */
    public double getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Method to set Vote Average
     * @param mVoteAverage to set
     */
    public void setVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

}
