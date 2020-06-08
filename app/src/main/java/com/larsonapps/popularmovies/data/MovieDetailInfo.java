package com.larsonapps.popularmovies.data;

import androidx.room.ColumnInfo;

public class MovieDetailInfo {
    // Declare variables
    private int mMovieId;
    private String mTitle;
    private String mBackdropPath;
    private String mImagePath;
    private String mOverview;
    // TODO finish adding image path for favorite type in movie detail fragment
    /**
     * Default constructor
     */
    public MovieDetailInfo() {}

    /**
     * Getter for movie id
     * @return movie id
     */
    public int getMovieId() {
        return mMovieId;
    }

    /**
     * Setter for movie id
     * @param mMovieId to set
     */
    public void setMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    /**
     * Getter for movie title
     * @return movie title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Setter for movie title
     * @param mTitle to set
     */
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * Getter for movie backdrop path
     * @return movie backdrop path
     */
    public String getBackdropPath() {
        return mBackdropPath;
    }

    /**
     * Setter for movie backdrop path
     * @param mBackdropPath to set
     */
    public void setBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    /**
     * Getter for image path
     * @return image path
     */
    public String getImagePath() {
        return mImagePath;
    }

    /**
     * Setter for image path
     * @param mImagePath to set
     */
    public void setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    /**
     * Getter for movie detail info overview
     * @return movie detail summary overview
     */
    public String getOverview() {
        return mOverview;
    }

    /**
     * Setter for movie detail info overview
     * @param mOverview to set
     */
    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }
}
