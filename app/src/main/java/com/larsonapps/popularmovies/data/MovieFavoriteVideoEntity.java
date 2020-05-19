package com.larsonapps.popularmovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "videos")
public class MovieFavoriteVideoEntity {

    @PrimaryKey(autoGenerate = true)
    private int videoKeyId;
    private int videoFavoriteKeyId;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    /**
     * Constructor for room INSERT
     * @param videoFavoriteKeyId to set
     * @param iso_639_1 to set
     * @param iso_3166_1 to set
     * @param key to set
     * @param name to set
     * @param site to set
     * @param size to set
     * @param type to set
     */
    @Ignore
    public MovieFavoriteVideoEntity (int videoFavoriteKeyId, String iso_639_1, String iso_3166_1,
                                     String key, String name, String site, int size, String type) {
        this.videoFavoriteKeyId = videoFavoriteKeyId;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    /**
     * Constructor for room SELECT
     * @param videoKeyId to set
     * @param videoFavoriteKeyId to set
     * @param iso_639_1 to set
     * @param iso_3166_1 to set
     * @param key to set
     * @param name to set
     * @param site to set
     * @param size to set
     * @param type to set
     */
    public MovieFavoriteVideoEntity (int videoKeyId, int videoFavoriteKeyId, String iso_639_1,
                                     String iso_3166_1, String key, String name, String site,
                                     int size, String type) {
        this.videoKeyId = videoKeyId;
        this.videoFavoriteKeyId = videoFavoriteKeyId;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    /**
     * Getter for video key id (primary key)
     * @return
     */
    public int getVideoKeyId() {
        return videoKeyId;
    }

    /**
     * Getter for video favorite key id (foreign key)
     * @return video favorite key id
     */
    public int getVideoFavoriteKeyId() {
        return videoFavoriteKeyId;
    }

    /**
     * Setter for video favorite key id (foreign key)
     * @param videoFavoriteKeyId to set
     */
    public void setVideoFavoriteKeyId(int videoFavoriteKeyId) {
        this.videoFavoriteKeyId = videoFavoriteKeyId;
    }

    /**
     * Getter for iso_639_1
     * @return iso_639_1
     */
    public String getIso_639_1() {
        return iso_639_1;
    }

    /**
     * Setter for iso_639_1
     * @param iso_639_1 to set
     */
    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    /**
     * Getter for iso_3166_1
     * @return iso_3166_1
     */
    public String getIso_3166_1() {
        return iso_3166_1;
    }

    /**
     * Setter for iso_3166_1
     * @param iso_3166_1 to set
     */
    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
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

    /**
     * Getter for size of video
     * @return size of video
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter for size of video
     * @param size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Getter for type of video
     * @return type of video
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type of video
     * @param type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
