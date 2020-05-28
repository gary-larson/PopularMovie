package com.larsonapps.popularmovies.data;

/**
 * Class to structure movie detail video data
 */
public class MovieDetailVideo {
    // Declare variables
    private String mId;
    private String mIso_639_1;
    private String mIso_3166_1;
    private String mKey;
    private String mName;
    private String mSite;
    private int mSize;
    private String mType;

    /**
     * Default Constructor for movie detail video
     */
    public MovieDetailVideo() {}

    /**
     * Getter for movie detail video id
     * @return movie detail video id
     */
    public String getId() {
        return mId;
    }

    /**
     * Setter for movie detail video id
     * @param mId to set
     */
    public void setId(String mId) {
        this.mId = mId;
    }

    /**
     * Getter for movie detail video iso_639_1
     * @return movie detail video iso_639_1
     */
    public String getIso_639_1() {
        return mIso_639_1;
    }

    /**
     * Setter for movie detail video iso+639_1
     * @param mIso_639_1 to set
     */
    public void setIso_639_1(String mIso_639_1) {
        this.mIso_639_1 = mIso_639_1;
    }

    /**
     * Getter for movie detail video iso_3166_1
     * @return movie detail video iso_3166_1
     */
    public String getIso_3166_1() {
        return mIso_3166_1;
    }

    /**
     * Setter for movie detail video iso_3166_1
     * @param mIso_3166_1 to set
     */
    public void setIso_3166_1(String mIso_3166_1) {
        this.mIso_3166_1 = mIso_3166_1;
    }

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

    /**
     * Getter for movie detail video size
     * @return movie detail video size
     */
    public int getSize() {
        return mSize;
    }

    /**
     * Setter for movie detail video size
     * @param mSize to set
     */
    public void setSize(int mSize) {
        this.mSize = mSize;
    }

    /**
     * Getter for movie detail video type
     * @return movie detail video type
     */
    public String getType() {
        return mType;
    }

    /**
     * Setter for movie detail video type
     * @param mType to set
     */
    public void setType(String mType) {
        this.mType = mType;
    }
}
