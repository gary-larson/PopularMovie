package com.larsonapps.popularmovies.data;

import java.util.Date;

/**
 * Class that holds information to control room data
 */
public class MovieControl {
    // Declare variables
    private String mListType;
    private Date mDownloadDate;
    private int mHighestPage;
    private int mTotalPage;
    private int mMovieId;

    /**
     * Default constructor for movie control
     */
    public MovieControl () {}

    /**
     * Getter for list type
     * @return list type
     */
    public String getListType() {
        return mListType;
    }

    /**
     * Setter for list type
     * @param mListType to set
     */
    public void setListType(String mListType) {
        this.mListType = mListType;
    }

    /**
     * Getter for download date
     * @return download date
     */
    public Date getDownloadDate() {
        return mDownloadDate;
    }

    /**
     * Setter for download date
     * @param mDownloadDate to set
     */
    public void setDownloadDate(Date mDownloadDate) {
        this.mDownloadDate = mDownloadDate;
    }

    /**
     * Getter for the highest page downloaded
     * @return highest page downloaded
     */
    public int getHighestPage() {
        return mHighestPage;
    }

    /**
     * Setter for the highest page downloaded
     * @param mHighestPage to set
     */
    public void setHighestPage(int mHighestPage) {
        this.mHighestPage = mHighestPage;
    }

    /**
     * Getter for total pages
     * @return total pages
     */
    public int getTotalPage() {
        return mTotalPage;
    }

    /**
     * Setteer for total pages
     * @param mTotalPage to set
     */
    public void setTotalPage(int mTotalPage) {
        this.mTotalPage = mTotalPage;
    }

    /**
     * Getter for movie id
     * @return movie id
     */
    public int getMovieId() {
        return mMovieId;
    }

    /**
     * Setter for movie id
     * @param mMovieId to set
     */
    public void setMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }
}
