package com.larsonapps.popularmovies.data;

/**
 * Class to structure movie detail review result data
 */
public class MovieDetailReviewResult {
    // Declare variables
    private String mAuthor;
    private String mContent;
    private String mUrl;

    /**
     * Default Constructor
     */
    public MovieDetailReviewResult() {}

    /**
     * Getter for author
     * @return author
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Setter for author
     * @param mAuthor to set
     */
    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    /**
     * Getter for content
     * @return content
     */
    public String getContent() {
        return mContent;
    }

    /**
     * Setter for content
     * @param mContent to set
     */
    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    /**
     * Getter for url
     * @return url
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Setter for url
     * @param mUrl to set
     */
    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
