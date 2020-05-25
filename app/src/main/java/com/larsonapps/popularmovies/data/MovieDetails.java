package com.larsonapps.popularmovies.data;

import java.util.List;

public class MovieDetails {
    //Declare variables
    private String mErrorMessage;
    private int mRuntime;
    private List<MovieVideo> mVideos;
    private MovieReview mMovieReview;

    /**
     * Default Constructor
     */
    public MovieDetails () {

    }

    /**
     * Constructor for all variables
     * @param runtime to set
     * @param videos to set
     * @param review to set
     */
    public MovieDetails (String errorMessage, int runtime, List<MovieVideo> videos,
                         MovieReview review) {
        this.mErrorMessage = errorMessage;
        this.mRuntime = runtime;
        this.mVideos = videos;
        this.mMovieReview = review;
    }

    /**
     * Getter for movie runtime
     * @return movie runtime
     */
    public int getRuntime() {
        return mRuntime;
    }

    /**
     * Setter for movie runtime
     * @param mRuntime to set
     */
    public void setRuntime(int mRuntime) {
        this.mRuntime = mRuntime;
    }

    /**
     * Getter for movie video list
     * @return movie video list
     */
    public List<MovieVideo> getVideos() {
        return mVideos;
    }

    /**
     * Setter for movie video list
     * @param mVideos to set
     */
    public void setVideos(List<MovieVideo> mVideos) {
        this.mVideos = mVideos;
    }

    /**
     * Getter for movie review
     * @return movie review
     */
    public MovieReview getReviews() {
        return mMovieReview;
    }

    /**
     * Setter for movie review
     * @param mMovieReview to set
     */
    public void setReviews(MovieReview mMovieReview) {
        this.mMovieReview = mMovieReview;
    }

    /**
     * Setter for error message
     * @param mErrorMessage to set
     */
    public void setErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }
}
