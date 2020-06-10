package com.larsonapps.popularmovies.data;

import java.util.List;

/**
 * Class to structure move detail review data
 */
public class MovieDetailReview {
    // Declare variables
    private int mPage;
    private int mTotalPages;
    private List<MovieDetailReviewResult> mReviewList;

    /**
     * Default constructor
     */
    public MovieDetailReview() {}

    /**
     * Getter for movie detail review page
     * @return movie detail review page
     */
    public int getPage() {
        return mPage;
    }

    /**
     * Setter for movie detail review page
     * @param mPage to set
     */
    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    /**
     * Getter for movie detail review total pages
     * @return movie detail review total pages
     */
    public int getTotalPages() {
        return mTotalPages;
    }

    /**
     * Setter for movie detail review total pages
     * @param mTotalPages to set
     */
    public void setTotalPages(int mTotalPages) {
        this.mTotalPages = mTotalPages;
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
