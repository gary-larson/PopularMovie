package com.larsonapps.popularmovies.database;

import androidx.room.ColumnInfo;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Class of the room table named movie_control
 */
@Entity(tableName = "movie_control")
public class MovieControlEntity {

    // Generate columns
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_control_key")
    private int mMovieControlKey;

    @ColumnInfo(name = "list_type")
    private String mListType;

    @ColumnInfo(name = "download_date")
    private Date mDownloadDate;

    @ColumnInfo(name = "highest_page", defaultValue = "0")
    private int mHighestPage;

    @ColumnInfo(name = "total_pages", defaultValue = "0")
    private int mTotalPages;

    @ColumnInfo(name = "movie_id", defaultValue = "0")
    int mMovieId;

    /**
     * Constructor for room SELECT
     * @param listType to set
     * @param downloadDate to set
     * @param highestPage to set
     * @param totalPages to set
     * @param movieId to set
     */
    @Ignore
    public MovieControlEntity(String listType, Date downloadDate, int highestPage, int totalPages,
                              int movieId) {
        this.mListType = listType;
        this.mDownloadDate = downloadDate;
        this.mHighestPage = highestPage;
        this.mTotalPages = totalPages;
        this.mMovieId = movieId;
    }

    /**
     * Constructor for room SELECT
     * @param movieControlKey to set
     * @param listType to set
     * @param downloadDate to set
     * @param highestPage to set
     * @param totalPages to set
     * @param movieId to set
     */
    public MovieControlEntity(int movieControlKey, String listType, Date downloadDate, int highestPage, int totalPages,
                              int movieId) {
        this.mMovieControlKey = movieControlKey;
        this.mListType = listType;
        this.mDownloadDate = downloadDate;
        this.mHighestPage = highestPage;
        this.mTotalPages = totalPages;
        this.mMovieId = movieId;
    }

    /**
     * Getter for movie control key
     * @return movie control key
     */
    public int getMovieControlKey() {return mMovieControlKey;}

    /**
     * Getter for list type
     * @return list type
     */
    public String getListType() {
        return mListType;
    }

    /**
     * Setter for list type
     * @param listType to set
     */
    public void setListType(String listType) {
        this.mListType = listType;
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
     * @param downloadDate download date
     */
    public void setDownloadDate(Date downloadDate) {
        this.mDownloadDate = downloadDate;
    }

    /**
     * Getter for page
     * @return page
     */
    public int getHighestPage() {
        return mHighestPage;
    }

    /**
     * Setter for page
     * @param page to set
     */
    public void setHighestPage(int page) {
        this.mHighestPage = page;
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
     * @param totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.mTotalPages = totalPages;
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
     * @param movieId to set
     */
    public void setMovieId(int movieId) {
        this.mMovieId = movieId;
    }
}
