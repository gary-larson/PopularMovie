package com.larsonapps.popularmovies.database;

import androidx.room.ColumnInfo;
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
    @ColumnInfo(name = "review_list_key")
    private int mReviewListKey;

    @ColumnInfo(name = "movie_id", defaultValue = "0")
    private final int mMovieId;

    @ColumnInfo(name = "author")
    private final String mAuthor;

    @ColumnInfo(name = "content")
    private final String mContent;

    @ColumnInfo(name = "url")
    private final String mUrl;

    /**
     * Constructor used for room INSERT
     * @param movieId to set
     * @param author to set
     * @param content to set
     * @param url to set
     */
    @Ignore
    public MovieDetailReviewListEntity(int movieId, String author, String content, String url) {
        this.mMovieId = movieId;
        this.mAuthor = author;
        this.mContent = content;
        this.mUrl = url;
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
        this.mReviewListKey = reviewListKey;
        this.mMovieId = movieId;
        this.mAuthor = author;
        this.mContent = content;
        this.mUrl = url;
    }

    /**
     * Getter for review list key (primary key)
     * @return review list key
     */
    public int getReviewListKey() {
        return mReviewListKey;
    }

    /**
     * Getter for movie id (foreign key
     * @return movie id
     */
    public int getMovieId() {
        return mMovieId;
    }

    /**
     * Getter for author
     * @return author
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Getter for content
     * @return content
     */
    public String getContent() {
        return mContent;
    }

    /**
     * Getter for URL
     * @return URL
     */
    public String getUrl() {
        return mUrl;
    }
}
