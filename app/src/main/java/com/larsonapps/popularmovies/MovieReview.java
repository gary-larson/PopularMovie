package com.larsonapps.popularmovies;

import java.util.List;

public class MovieReview {
    // Declare variables
    private int mPage;
    private List<ReviewResult> mReviews;
    private int mTotalPages;
    private int mTotalReviews;

    /**
     * Default constructor
     */
    public MovieReview () {}

    /**
     * Constructor to create class with all items
     * @param page to set
     * @param reviews to set
     * @param totalPages to set
     * @param totalReviews to set
     */
    public MovieReview (int page, List<ReviewResult> reviews,
                        int totalPages, int totalReviews) {
        this.mPage = page;
        this.mReviews = reviews;
        this.mTotalPages = totalPages;
        this.mTotalReviews = totalReviews;
    }

    /**
     * Getter for page number
     * @return page number
     */
    public int getPage() {
        return mPage;
    }

    /**
     * Setter for page number
     * @param mPage to set
     */
    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    /**
     * Getter for reviews list
     * @return reviews list
     */
    public List<ReviewResult> getReviews() {
        return mReviews;
    }

    /**
     * Setter for reviews list
     * @param mReviews to set
     */
    public void setReviews(List<ReviewResult> mReviews) {
        this.mReviews = mReviews;
    }

    /**
     * Getter for total pages
     * @return total pages
     */
    public int getTotalPages() {
        return mTotalPages;
    }

    /**
     * Setter for total pages
     * @param mTotalPages to set
     */
    public void setTotalPages(int mTotalPages) {
        this.mTotalPages = mTotalPages;
    }

    /**
     * Getter for total reviews
     * @return total reviews
     */
    public int getTotalReviews() {
        return mTotalReviews;
    }

    /**
     * Setter for total reviews
     * @param mTotalReviews to set
     */
    public void setTotalReviews(int mTotalReviews) {
        this.mTotalReviews = mTotalReviews;
    }
}
