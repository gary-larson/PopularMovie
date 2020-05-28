package com.larsonapps.popularmovies.data;

import java.util.List;

/**
 * Class to structure move detail review data
 */
public class MovieDetailReview {
    // Declare variables
    private MovieDetailReviewInfo mMovieDetailReviewInfo;
    private List<MovieDetailReviewResult> mReviewList;


    /**
     * Default constructor
     */
    public MovieDetailReview() {}

    /**
     * Getter for movie detail review info
     * @return page number
     */
    public MovieDetailReviewInfo getMovieDetailReviewInfo() {
        return mMovieDetailReviewInfo;
    }

    /**
     * Setter for movie detail review info
     * @param movieDetailReviewInfo to set
     */
    public void setMovieDetailReviewInfo(MovieDetailReviewInfo movieDetailReviewInfo) {
        this.mMovieDetailReviewInfo = movieDetailReviewInfo;
    }

    /**
     * Getter for movie detail review list
     * @return movie detail review list
     */
    public List<MovieDetailReviewResult> getReviewList() {
        return mReviewList;
    }

    /**
     * Setter for movie detail review list
     * @param mReviews to set
     */
    public void setReviewList(List<MovieDetailReviewResult> mReviews) {
        this.mReviewList = mReviews;
    }
}
