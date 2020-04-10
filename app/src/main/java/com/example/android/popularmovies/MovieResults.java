package com.example.android.popularmovies;

class MovieResults {
    private String mPosterPath;
    private boolean mAdult;
    private String mOverview;
    private String mReleaseDate;
    private int[] mGenreIds;
    private int mId;
    private String mOriginalTitle;
    private String mOriginalLanguage;
    private String mTitle;
    private String mBackdropPath;
    private double mPopularity;
    private int mVoteCount;
    private boolean mVideo;
    private double mVoteAverage;

    /**
     * No Argument Constructor
     */
    MovieResults() {
    }

    /**
     * Method to get the poster Path
     * @return the Poster path or null
     */
    String getPosterPath() {
        return mPosterPath;
    }

    /**
     * Method to set the Poster Path
     * @param mPosterPath of the movie
     */
    void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    /**
     * Method to get if an Adult movie
     * @return weather it is an adult movie
     */
    boolean ismAdult() {
        return mAdult;
    }

    /**
     * Method to set if an Adult movie
     * @param mAdult is set
     */
    void setAdult(boolean mAdult) {
        this.mAdult = mAdult;
    }

    /**
     * Method to get Overview
     * @return Overview
     */
    String getOverview() {
        return mOverview;
    }

    /**
     * Method to set Overview
     * @param mOverview to set
     */
    void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    /**
     * Method to get Release Date
     * @return Release Date
     */
    String getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Method to set Release Date
     * @param mReleaseDate to set
     */
    void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    /**
     * Method to get Genre Ids
     * @return Genre Ids
     */
    int[] getGenreIds() {
        return mGenreIds;
    }

    /**
     * Method to set Genre Ids
     * @param mGenreIds to set
     */
    void setGenreIds(int[] mGenreIds) {
        this.mGenreIds = mGenreIds;
    }

    /**
     * Method to get Id
     * @return Id
     */
    int getId() {
        return mId;
    }

    /**
     * Method to set Id
     * @param mId to set
     */
    void setId(int mId) {
        this.mId = mId;
    }

    /**
     * Method to get Original Title
     * @return Original Title
     */
    String getOriginalTitle() {
        return mOriginalTitle;
    }

    /**
     * Method to set Original Title
     * @param mOriginalTitle to set
     */
    void setOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    /**
     * Method to get Original Language
     * @return Original Language
     */
    String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    /**
     * Method to set Original Language
     * @param mOriginalLanguage to set
     */
    void setOriginalLanguage(String mOriginalLanguage) {
        this.mOriginalLanguage = mOriginalLanguage;
    }

    /**
     * Method to get Title
     * @return Title
     */
    String getTitle() {
        return mTitle;
    }

    /**
     * Method to set Title
     * @param mTitle to set
     */
    void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * Method to get drop back path
     * @return Backdrop Path
     */
    String getBackdropPath() {
        return mBackdropPath;
    }

    /**
     * Method to set drop back path
     * @param mBackdropPath to set
     */
    void setBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    /**
     * Method to get Popularity
     * @return Popularity
     */
    double getPopularity() {
        return mPopularity;
    }

    /**
     * Method to set Popularity
     * @param mPopularity to set
     */
    void setPopularity(double mPopularity) {
        this.mPopularity = mPopularity;
    }

    /**
     * Method to get Vote Count
     * @return Vote Count
     */
    int getVoteCount() {
        return mVoteCount;
    }

    /**
     * Method to set Vote Count
     * @param mVoteCount to set
     */
    void setVoteCount(int mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    /**
     * Method to get if a Video is available
     * @return if a video is available
     */
    boolean isVideo() {
        return mVideo;
    }

    /**
     * Method to set if a video is available
     * @param mVideo to set
     */
    void setVideo(boolean mVideo) {
        this.mVideo = mVideo;
    }

    /**
     * Method to get Vote Average
     * @return Vote Average
     */
    double getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Method to set Vote Average
     * @param mVoteAverage to set
     */
    void setVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

}
