package com.larsonapps.popularmovies.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Class for room table movie detail video list
 */
@Entity (tableName = "movie_detail_video_list")
public class MovieDetailVideoListEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "video_list_key")
    private int mVideoListKey;

    @ColumnInfo(name = "movie_id")
    private int mMovieId;

    @ColumnInfo(name = "site_key")
    private String mKey;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "site")
    private String mSite;

    /**
     * Constructor for room INSERT
     * @param movieId to set
     * @param key to set
     * @param name to set
     * @param site to set
     */
    @Ignore
    public MovieDetailVideoListEntity(int movieId, String key, String name, String site) {
        this.mMovieId = movieId;
        this.mKey = key;
        this.mName = name;
        this.mSite = site;
    }

    /**
     * Constructor for room SELECT
     * @param videoListKey to set
     * @param movieId to set
     * @param key to set
     * @param name to set
     * @param site to set
     */
    public MovieDetailVideoListEntity(int videoListKey, int movieId, String key, String name,
                                      String site) {
        this.mVideoListKey = videoListKey;
        this.mMovieId = movieId;
        this.mKey = key;
        this.mName = name;
        this.mSite = site;
    }

    /**
     * Getter for video list key (primary key)
     * @return video list key
     */
    public int getVideoListKey() {
        return mVideoListKey;
    }

    /**
     * Getter for movie id (foreign key)
     * @return movie id
     */
    public int getMovieId() {
        return mMovieId;
    }

    /**
     * Setter for movie id (foreign key)
     * @param movieId to set
     */
    public void setMovieId(int movieId) {
        this.mMovieId = movieId;
    }

    /**
     * Getter for site's key
     * @return site's key
     */
    public String getKey() {
        return mKey;
    }

    /**
     * Setter for site's key
     * @param key to set
     */
    public void setKey(String key) {
        this.mKey = key;
    }

    /**
     * Getter for reviewer's name
     * @return reviewer's name
     */
    public String getName() {
        return mName;
    }

    /**
     * Setter for reviewer's name
     * @param name to set
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Getter for site where video is located
     * @return site
     */
    public String getSite() {
        return mSite;
    }

    /**
     * Setter for site where video is located
     * @param site to set
     */
    public void setSite(String site) {
        this.mSite = site;
    }
}
