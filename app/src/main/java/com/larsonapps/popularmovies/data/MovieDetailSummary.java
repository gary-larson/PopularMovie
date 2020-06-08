package com.larsonapps.popularmovies.data;

import androidx.room.ColumnInfo;

import java.util.Date;

/**
 * Class to structure movie detail summary data
 */
public class MovieDetailSummary {
    // Declare variables
    private Date mReleaseDate;
    private int mRuntime;
    private double mVoteAverage;

    /**
     * Default constructor for movie detail summary
     */
    public MovieDetailSummary () {}

    /**
     * Getter for movie detail summary release date
     * @return movie detail summary release date
     */
    public Date getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Setter for movie detail summary release date
     * @param mReleaseDate to set
     */
    public void setReleaseDate(Date mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    /**
     * Getter for movie detail summary runtime
     * @return movie detail summary runtime
     */
    public int getRuntime() {
        return mRuntime;
    }

    /**
     * Setter for movie detail summary runtime
     * @param mRuntime to set
     */
    public void setRuntime(int mRuntime) {
        this.mRuntime = mRuntime;
    }

    /**
     * Getter for movie detail summary vote average
     * @return movie detail summary vote average
     */
    public double getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Setter for movie detail summary vote average
     * @param voteAverage to set
     */
    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }
}
