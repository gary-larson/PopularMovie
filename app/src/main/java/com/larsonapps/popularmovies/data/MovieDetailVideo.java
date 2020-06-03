package com.larsonapps.popularmovies.data;

/**
 * Class to structure movie detail video data
 */
public class MovieDetailVideo {
    // Declare variables
    private String mKey;
    private String mName;
    private String mSite;


    /**
     * Default Constructor for movie detail video
     */
    public MovieDetailVideo() {}

    /**
     * Getter for movie detail video key
     * @return movie detail video key
     */
    public String getKey() {
        return mKey;
    }

    /**
     * Setter for movie detail video key
     * @param mKey to set
     */
    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    /**
     * Getter for movie detail video name
     * @return movie detail video name
     */
    public String getName() {
        return mName;
    }

    /**
     * Setter for movie detail video name
     * @param mName to set
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Getter for movie detail video site
     * @return movie detail video site
     */
    public String getSite() {
        return mSite;
    }

    /**
     * Setter for movie detail video site
     * @param mSite to set
     */
    public void setSite(String mSite) {
        this.mSite = mSite;
    }
}
