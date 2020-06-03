package com.larsonapps.popularmovies.database;

import androidx.room.ColumnInfo;
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
    private int movieControlKey;
    private String listType;
    private Date downloadDate;
    @ColumnInfo(defaultValue = "0")
    private int page;
    @ColumnInfo(defaultValue = "0")
    private int totalPages;
    @ColumnInfo(defaultValue = "0")
    int movieId;

    /**
     * Constructor for room SELECT
     * @param listType to set
     * @param downloadDate to set
     * @param page to set
     * @param totalPages to set
     * @param movieId to set
     */
    @Ignore
    public MovieControlEntity(String listType, Date downloadDate, int page, int totalPages,
                              int movieId) {
        this.listType = listType;
        this.downloadDate = downloadDate;
        this.page = page;
        this.totalPages = totalPages;
        this.movieId = movieId;
    }

    /**
     * Constructor for room INSERT
     * @param movieControlKey to set
     * @param listType to set
     * @param downloadDate to set
     * @param page to set
     * @param totalPages to set
     * @param movieId to set
     */
    public MovieControlEntity(int movieControlKey, String listType, Date downloadDate, int page,
                              int totalPages, int movieId) {
        this.movieControlKey = movieControlKey;
        this.listType = listType;
        this.downloadDate = downloadDate;
        this.page = page;
        this.totalPages = totalPages;
        this.movieId = movieId;
    }

    /**
     * Getter for movie control key
     * @return movie control key
     */
    public int getMovieControlKey() {
        return movieControlKey;
    }

    /**
     * Getter for list type
     * @return list type
     */
    public String getListType() {
        return listType;
    }

    /**
     * Setter for list type
     * @param listType to set
     */
    public void setListType(String listType) {
        this.listType = listType;
    }

    /**
     * Getter for download date
     * @return download date
     */
    public Date getDownloadDate() {
        return downloadDate;
    }

    /**
     * Setter for download date
     * @param downloadDate download date
     */
    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }

    /**
     * Getter for page
     * @return page
     */
    public int getPage() {
        return page;
    }

    /**
     * Setter for page
     * @param page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Getter for total pages
     * @return total pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Setter for total pages
     * @param totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Getter for movie id
     * @return movie id
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Setter for movie id
     * @param movieId to set
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
