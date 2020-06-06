package com.larsonapps.popularmovies.data;

import java.util.List;

/**
 * Class to structure movie main data
 */
public class MovieMain {
    // Declare variables
    private List<MovieResult> mMovieList;
    private int mTotalPages;

    /**
     * Defailt constructor for movie main
     */
    public MovieMain () {}

    /**
     * Getter for movie list
     * @return movie list
     */
    public List<MovieResult> getMovieList() {
        return mMovieList;
    }

    /**
     * Setter for movie list
     * @param mMovieList to set
     */
    public void setMovieList(List<MovieResult> mMovieList) {
        this.mMovieList = mMovieList;
    }

    /**
     * Getter for movie main total pages
     * @return movie main total pages
     */
    public int getTotalPages() {
        return mTotalPages;
    }

    /**
     * Setter for movie main total pages
     * @param mTotalPages to set
     */
    public void setTotalPages(int mTotalPages) {
        this.mTotalPages = mTotalPages;
    }
}
