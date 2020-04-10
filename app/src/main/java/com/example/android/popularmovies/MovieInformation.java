package com.example.android.popularmovies;

import java.util.List;

class MovieInformation {
    private int mPage;
    private int mTotalResults;
    private int mTotalPages;
    private String mStatusMessage;
    private int mStatusCode;
    private List<MovieResults> mMovieResults;

    /**
     * No argument constructor
     */
    MovieInformation() {
    }

    /**
     * Method to get the page number
     * @return page
     */
    int getPage() {
        return mPage;
    }

    /**
     * Method to set the page number
     * @param page number to set
     */
    void setPage(int page) {
        this.mPage = page;
    }

    /**
     * Method to get the total number of results
     * @return total results
     */
    int getTotalResults() {
        return mTotalResults;
    }

    /**
     * Method to set the total number of results
     * @param totalResults to set
     */
    void setTotalResults(int totalResults) {
        this.mTotalResults = totalResults;
    }

    /**
     * Method to get the total number of pages
     * @return the total number of pages
     */
    int getTotalPages() {
        return mTotalPages;
    }

    /**
     * Method to set the total number of pages
     * @param totalPages to set
     */
    void setTotalPages(int totalPages) {
        this.mTotalPages = totalPages;
    }

    /**
     * Method to get the status message
     * @return the status message
     */
    String getStatusMessage() {
        return mStatusMessage;
    }

    /**
     * Method to set the status message
     * @param statusMessage to set
     */
    void setStatusMessage(String statusMessage) {
        this.mStatusMessage = statusMessage;
    }

    /**
     * Method to get the status code
     * @return the status code
     */
    int getStatusCode() {
        return mStatusCode;
    }

    /**
     * Method to set the status code
     * @param statusCode to set
     */
    void setStatusCode(int statusCode) {
        mStatusCode = statusCode;
    }

    /**
     * Method to get the Movie results
     * @return the movie results
     */
    List<MovieResults> getMovieResults() {
        return mMovieResults;
    }

    /**
     * Method to set the Movie results
     * @param movieResults to set
     */
    void setMovieResults(List<MovieResults> movieResults) {
        this.mMovieResults = movieResults;
    }
}
