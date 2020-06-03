package com.larsonapps.popularmovies.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Class for room table movie detail video list
 */
@Entity (tableName = "movie_detail_video_list")
public class MovieDetailVideoListEntity {

    @PrimaryKey(autoGenerate = true)
    private int videoListKey;
    private int movieId;
    private String key;
    private String name;
    private String site;

    /**
     * Constructor for room INSERT
     * @param movieId to set
     * @param key to set
     * @param name to set
     * @param site to set
     */
    @Ignore
    public MovieDetailVideoListEntity(int movieId, String key, String name, String site) {
        this.movieId = movieId;
        this.key = key;
        this.name = name;
        this.site = site;
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
        this.videoListKey = videoListKey;
        this.movieId = movieId;
        this.key = key;
        this.name = name;
        this.site = site;
    }

    /**
     * Getter for video list key (primary key)
     * @return video list key
     */
    public int getVideoListKey() {
        return videoListKey;
    }

    /**
     * Getter for movie id (foreign key)
     * @return movie id
     */
    public int getmovieId() {
        return movieId;
    }

    /**
     * Setter for movie id (foreign key)
     * @param movieId to set
     */
    public void setmovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     * Getter for site's key
     * @return site's key
     */
    public String getKey() {
        return key;
    }

    /**
     * Setter for site's key
     * @param key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Getter for reviewer's name
     * @return reviewer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for reviewer's name
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for site where video is located
     * @return site
     */
    public String getSite() {
        return site;
    }

    /**
     * Setter for site where video is located
     * @param site to set
     */
    public void setSite(String site) {
        this.site = site;
    }
}
