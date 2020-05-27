package com.larsonapps.popularmovies.data;

public class MovieVideo {
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
     * Default Constructor
     */
    public MovieVideo() {}

    /**
     * Getter for id
     * @return id
     */
    public String getId() {
        return mId;
    }

    /**
     * Setter for id
     * @param mId to set
     */
    public void setId(String mId) {
        this.mId = mId;
    }

    /**
     * Getter for iso_639_1
     * @return iso_639_1
     */
    public String getIso_639_1() {
        return mIso_639_1;
    }

    /**
     * Setter for iso+639_1
     * @param mIso_639_1 to set
     */
    public void setIso_639_1(String mIso_639_1) {
        this.mIso_639_1 = mIso_639_1;
    }

    /**
     * Getter for iso_3166_1
     * @return iso_3166_1
     */
    public String getIso_3166_1() {
        return mIso_3166_1;
    }

    /**
     * Setter for iso_3166_1
     * @param mIso_3166_1 to set
     */
    public void setIso_3166_1(String mIso_3166_1) {
        this.mIso_3166_1 = mIso_3166_1;
    }

    /**
     * Getter for key
     * @return key
     */
    public String getKey() {
        return mKey;
    }

    /**
     * Setter for key
     * @param mKey to set
     */
    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    /**
     * Getter for video name
     * @return video name
     */
    public String getName() {
        return mName;
    }

    /**
     * Setter for video name
     * @param mName to set
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Getter for site
     * @return site
     */
    public String getSite() {
        return mSite;
    }

    /**
     * Setter for site
     * @param mSite to set
     */
    public void setSite(String mSite) {
        this.mSite = mSite;
    }

    /**
     * Getter for video size
     * @return video size
     */
    public int getSize() {
        return mSize;
    }

    /**
     * Setter for video size
     * @param mSize to set
     */
    public void setSize(int mSize) {
        this.mSize = mSize;
    }

    /**
     * Getter for type of video
     * @return type of video
     */
    public String getType() {
        return mType;
    }

    /**
     * Setter for type of video
     * @param mType to set
     */
    public void setType(String mType) {
        this.mType = mType;
    }
}
