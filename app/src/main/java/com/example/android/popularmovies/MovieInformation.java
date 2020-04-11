package com.example.android.popularmovies;

import java.util.List;

class MovieInformation {
    private int mTotalPages;
    private String mStatusMessage;
    private List<MovieResults> mMovieResults;

    /**
     * No argument constructor
     */
    MovieInformation() {
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
