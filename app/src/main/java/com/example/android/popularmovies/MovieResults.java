package com.example.android.popularmovies;

public class MovieResults {
    private String mPosterPath;
    private boolean mAdult;
    private String mOverview;
    private String mReleaseDate;
    private int[] mGenreIds;
    private int mId;
    private String mOriginalTitle;
    private String mOriginalLanguage;
    private String mTitle;
    private String mDropbackPath;
    private double mPopularity;
    private int mVoteCount;
    private boolean mVideo;
    private double mVoteAverage;

    /**
     * No Argument Constructor
     */
    public MovieResults() {
    }

    /**
     * Full argument Constructor
     * @param mPosterPath for movie may be null
     * @param mAdult for movie
     * @param mOverview for movie
     * @param mReleaseDate for movie
     * @param mGenreIds for movie
     * @param mId for movie
     * @param mOriginalTitle for movie
     * @param mOriginalLanguage for movie
     * @param mTitle for movie
     * @param mDropbackPath for movie may be null
     * @param mPopularity for movie
     * @param mVoteCount for movie
     * @param mVideo for movie
     * @param mVoteAverage for movie
     */
    public MovieResults(String mPosterPath, boolean mAdult, String mOverview, String mReleaseDate,
                        int[] mGenreIds, int mId, String mOriginalTitle, String mOriginalLanguage,
                        String mTitle, String mDropbackPath, double mPopularity, int mVoteCount,
                        boolean mVideo, double mVoteAverage) {
        this.mPosterPath = mPosterPath;
        this.mAdult = mAdult;
        this.mOverview = mOverview;
        this.mReleaseDate = mReleaseDate;
        this.mGenreIds = mGenreIds;
        this.mId = mId;
        this.mOriginalTitle = mOriginalTitle;
        this.mOriginalLanguage = mOriginalLanguage;
        this.mTitle = mTitle;
        this.mDropbackPath = mDropbackPath;
        this.mPopularity = mPopularity;
        this.mVoteCount = mVoteCount;
        this.mVideo = mVideo;
        this.mVoteAverage = mVoteAverage;
    }

    /**
     * Method to get the poster Path
     * @return the Poster path or null
     */
    public String getmPosterPath() {
        return mPosterPath;
    }

    /**
     * Method to set the Poster Path
     * @param mPosterPath of the movie
     */
    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    /**
     * Method to get if an Adult movie
     * @return weather it is an adult movie
     */
    public boolean ismAdult() {
        return mAdult;
    }

    /**
     * Method to set if an Adult movie
     * @param mAdult is set
     */
    public void setmAdult(boolean mAdult) {
        this.mAdult = mAdult;
    }

    /**
     * Method to get Overview
     * @return Overview
     */
    public String getmOverview() {
        return mOverview;
    }

    /**
     * Method to set Overview
     * @param mOverview to set
     */
    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    /**
     * Method to get Release Date
     * @return Release Date
     */
    public String getmReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Method to set Release Date
     * @param mReleaseDate to set
     */
    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    /**
     * Method to get Genre Ids
     * @return Genre Ids
     */
    public int[] getmGenreIds() {
        return mGenreIds;
    }

    /**
     * Method to set Genre Ids
     * @param mGenreIds to set
     */
    public void setmGenreIds(int[] mGenreIds) {
        this.mGenreIds = mGenreIds;
    }

    /**
     * Method to get Id
     * @return Id
     */
    public int getmId() {
        return mId;
    }

    /**
     * Method to set Id
     * @param mId to set
     */
    public void setmId(int mId) {
        this.mId = mId;
    }

    /**
     * Method to get Original Title
     * @return Original Title
     */
    public String getmOriginalTitle() {
        return mOriginalTitle;
    }

    /**
     * Method to set Original Title
     * @param mOriginalTitle to set
     */
    public void setmOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    /**
     * Method to get Original Language
     * @return Original Language
     */
    public String getmOriginalLanguage() {
        return mOriginalLanguage;
    }

    /**
     * Method to set Original Language
     * @param mOriginalLanguage to set
     */
    public void setmOriginalLanguage(String mOriginalLanguage) {
        this.mOriginalLanguage = mOriginalLanguage;
    }

    /**
     * Method to get Title
     * @return Title
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * Method to set Title
     * @param mTitle to set
     */
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * Method to get Bropback Path
     * @return Dropback Path
     */
    public String getmDropbackPath() {
        return mDropbackPath;
    }

    /**
     * Method to set Dropback Path
     * @param mDropbackPath to set
     */
    public void setmDropbackPath(String mDropbackPath) {
        this.mDropbackPath = mDropbackPath;
    }

    /**
     * Method to get Popularity
     * @return Popularity
     */
    public double getmPopularity() {
        return mPopularity;
    }

    /**
     * Method to set Popularity
     * @param mPopularity to set
     */
    public void setmPopularity(double mPopularity) {
        this.mPopularity = mPopularity;
    }

    /**
     * Method to get Vote Count
     * @return Vote Count
     */
    public int getmVoteCount() {
        return mVoteCount;
    }

    /**
     * Method to set Vote Count
     * @param mVoteCount to set
     */
    public void setmVoteCount(int mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    /**
     * Method to get if a Video is available
     * @return if a video is available
     */
    public boolean ismVideo() {
        return mVideo;
    }

    /**
     * Method to set if a video is available
     * @param mVideo to set
     */
    public void setmVideo(boolean mVideo) {
        this.mVideo = mVideo;
    }

    /**
     * Method to get Vote Average
     * @return Vote Average
     */
    public double getmVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Method to set Vote Average
     * @param mVoteAverage to set
     */
    public void setmVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

}
