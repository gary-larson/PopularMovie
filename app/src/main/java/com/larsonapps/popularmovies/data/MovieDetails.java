package com.larsonapps.popularmovies.data;

import java.util.Date;
import java.util.List;

public class MovieDetails {
    //Declare variables
    private String mErrorMessage;
    private int mRuntime;
    private String mTitle;
    private Date mReleaseDate;
    private String mBackdropPath;
    private double mVoteAverage;
    private String mOverview;
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

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
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
     * Getter for movie review
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
}
