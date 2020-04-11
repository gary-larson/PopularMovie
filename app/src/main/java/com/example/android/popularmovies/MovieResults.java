package com.example.android.popularmovies;

import java.util.Date;

class MovieResults {
    private String mPosterPath;
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
