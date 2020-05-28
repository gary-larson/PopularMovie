package com.larsonapps.popularmovies.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Class of the room table named reviews
 */
@Entity (tableName = "reviews")
public class MovieReviewEntity {

    // Generate columns
    @PrimaryKey (autoGenerate = true)
    private int reviewKeyId;
    private int reviewStatId;
    private int pageNumber;
    private String reviewId;
    private String author;
    private String content;
    private String url;

    /**
     * Constructor used for room INSERT
     * @param reviewStatId
     * @param pageNumber
     * @param reviewId
     * @param author
     * @param content
     * @param url
     */
    @Ignore
    public MovieReviewEntity(int reviewStatId, int pageNumber, String reviewId,
                             String author, String content, String url) {
        this.reviewStatId = reviewStatId;
        this.pageNumber = pageNumber;
        this.reviewId = reviewId;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    /**
     * Constructor used for room SELECT
     * @param reviewKeyId
     * @param reviewStatId
     * @param pageNumber
     * @param reviewId
     * @param author
     * @param content
     * @param url
     */
    public MovieReviewEntity(int reviewKeyId, int reviewStatId, int pageNumber,
                             String reviewId, String author, String content, String url) {
        this.reviewKeyId = reviewKeyId;
        this.reviewStatId = reviewStatId;
        this.pageNumber = pageNumber;
        this.reviewId = reviewId;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    /**
     * Getter for review key id (primary key)
     * @return review key id
     */
    public int getReviewKeyId() {
        return reviewKeyId;
    }

    /**
     * Getter for review stat id (foreign key)
     * @return review stat id
     */
    public int getReviewStatId() {
        return reviewStatId;
    }

    /**
     * Setter for review stat id (foreign key)
     * @param reviewStatId to set
     */
    public void setReviewStatId(int reviewStatId) {
        this.reviewStatId = reviewStatId;
    }

    /**
     * Getter for page number
     * @return page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Setter for page number
     * @param pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Getter for review id
     * @return review id
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Setter for review id
     * @param reviewId to set
     */
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * Getter for author
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter for author
     * @param author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Getter for content
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for content
     * @param content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter for URL
     * @return URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for URL
     * @param url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
