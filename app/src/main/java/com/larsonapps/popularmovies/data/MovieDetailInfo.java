package com.larsonapps.popularmovies.data;

public class MovieDetailInfo {
    // Declare variables
    private int mMovieId;
    private String mErrorMessage;
    private String mTitle;
    private String mBackdropPath;
    private String mOverview;

    /**
     * Default constructor
     */
    public MovieDetailInfo() {}

    /**
     * Getter for movie id
     * @return movie id
     */
    public int getmMovieId() {
        return mMovieId;
    }

    /**
     * Setter for movie id
     * @param mMovieId to set
     */
    public void setmMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    /**
     * Getter for movie detail error message
     * @return movie error message
     */
    public String getErrorMessage() {
        return mErrorMessage;
    }

    /**
     * Setter for error message
     * @param mErrorMessage to set
     */
    public void setErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
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
