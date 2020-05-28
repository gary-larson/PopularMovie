package com.larsonapps.popularmovies.data;

/**
 * Class to structure movie detail information data
 */
public class MovieDetailInfo {
    // Declare variables
    private String mErrorMessage;
    private String mTitle;
    private String mBackdropPath;

    /**
     * Default constructor for movie detail info
     */
    public MovieDetailInfo() {}

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
}
