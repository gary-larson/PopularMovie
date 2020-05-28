package com.larsonapps.popularmovies.data;

import java.util.List;

/**
 * Class to structure Movie Detail data
 */
public class MovieDetails {
    //Declare variables

    private MovieDetailInfo mMovieDetailInfo;
    private MovieDetailSummary mMovieDetailSummary;
    private List<MovieDetailVideo> mVideoList;
    private MovieDetailReview mMovieDetailReview;

    /**
     * Default Constructor for movie details
     */
    public MovieDetails () {}

    /**
     * Getter for Mmovie detail info
     * @return movie detail info
     */
    public MovieDetailInfo getMovieDetailInfo() {
        return mMovieDetailInfo;
    }

    /**
     * Setter for movie detail info
     * @param mMovieDetailInfo to set
     */
    public void setMovieDetailInfo(MovieDetailInfo mMovieDetailInfo) {
        this.mMovieDetailInfo = mMovieDetailInfo;
    }

    /**
     * Getter for movie detail summary
     * @return movie detail summary
     */
    public MovieDetailSummary getMovieDetailSummary() {
        return mMovieDetailSummary;
    }

    /**
     * Setter for movie detail summary
     * @param mMovieDetailSummary to set
     */
    public void setMovieDetailSummary(MovieDetailSummary mMovieDetailSummary) {
        this.mMovieDetailSummary = mMovieDetailSummary;
    }

    /**
     * Getter for movie detail video list
     * @return movie video list
     */
    public List<MovieDetailVideo> getVideoList() {
        return mVideoList;
    }

    /**
     * Setter for movie detail video list
     * @param videoList to set
     */
    public void setVideoList(List<MovieDetailVideo> videoList) {
        this.mVideoList = videoList;
    }

    /**
     * Getter for movie detail review
     * @return movie detail review
     */
    public MovieDetailReview getReviews() {
        return mMovieDetailReview;
    }

    /**
     * Setter for movie detail review
     * @param mMovieDetailReview to set
     */
    public void setReviews(MovieDetailReview mMovieDetailReview) {
        this.mMovieDetailReview = mMovieDetailReview;
    }
}
