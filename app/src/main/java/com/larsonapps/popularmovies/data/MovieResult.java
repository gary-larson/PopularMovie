package com.larsonapps.popularmovies.data;


import androidx.room.ColumnInfo;

/**
 * Class to structure movie result data
 */
public class MovieResult {
    // Declare member variables
    private String mPosterPath;
    private String mImagePath;
    private int movieID;


    /**
     * Default Constructor
     */
    public MovieResult() {}

    /**
     * getter for movie poster Path
     * @return the movie Poster path
     */
    public String getPosterPath() {
        return mPosterPath;
    }

    /**
     * Method to set the movie poster path
     * @param mPosterPath of the movie to set
     */
    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
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

}
