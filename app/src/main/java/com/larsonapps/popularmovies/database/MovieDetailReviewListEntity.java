package com.larsonapps.popularmovies.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Class of the room table named movie_detail_review_list
 */
@Entity (tableName = "movie_detail_review_list")
public class MovieDetailReviewListEntity {

    // Generate columns
    @PrimaryKey (autoGenerate = true)
    private int reviewListKey;
    private int movieId;
    private String author;
    private String content;
    private String url;

    /**
     * Constructor used for room INSERT
     * @param movieId to set
     * @param author to set
     * @param content to set
     * @param url to set
     */
    @Ignore
    public MovieDetailReviewListEntity(int movieId, String author, String content, String url) {
        this.movieId = movieId;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    /**
     * Constructor used for room SELECT
     * @param reviewListKey to set
     * @param movieId to set
     * @param author to set
     * @param content to set
     * @param url to set
     */
    public MovieDetailReviewListEntity(int reviewListKey, int movieId, String author, String content,
                                       String url) {
        this.reviewListKey = reviewListKey;
        this.movieId = movieId;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    /**
     * Getter for review list key (primary key)
     * @return review list key
     */
    public int getReviewListKey() {
        return reviewListKey;
    }

    /**
     * Getter for movie id (foreign key
     * @return movie id
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Setter for movie id (foreign key)
     * @param movieId to set
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
