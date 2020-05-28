package com.larsonapps.popularmovies.data;

/**
 * Class to structure movie detail review information data
 */
public class MovieDetailReviewInfo {
    // Declare variables
    private int mPage;
    private int mTotalPages;

    /**
     * Default constructor for movie detail review info
     */
    public MovieDetailReviewInfo() {}

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

}
