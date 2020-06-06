package com.larsonapps.popularmovies.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Class of the room table named movie_detail
 * covers movie detail info and movie detail summary
 */
@Entity(tableName = "movie_detail")
public class MovieDetailEntity {
    // Generate columns
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    private int mMovieId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "backdrop_path")
    private String mBackdropPath;

    @ColumnInfo(name = "image_path")
    private String mImagePath;

    @ColumnInfo(name = "overview")
    private String mOverview;

    @ColumnInfo(name = "release_date")
    private Date mReleaseDate;

    @ColumnInfo(name = "runtime")
    private int mRuntime;

    @ColumnInfo(name = "vote_average")
    private double mVoteAverage;

    /**
     * Constructor for room
     * @param movieId to set
     * @param title to set
     * @param backdropPath to set
     * @param imagePath to set
     * @param overview to set
     * @param releaseDate to set
     * @param runtime to set
     * @param voteAverage to set
     */
    public MovieDetailEntity (int movieId, String title, String backdropPath, String imagePath,
                              String overview, Date releaseDate, int runtime, double voteAverage) {
        this.mMovieId = movieId;
        this.mTitle = title;
        this.mBackdropPath = backdropPath;
        this.mImagePath = imagePath;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mRuntime = runtime;
        this.mVoteAverage = voteAverage;
    }

    /**
     * Getter for movie id
     * @return movie id
     */
    public int getMovieId() {
        return mMovieId;
    }

    /**
     * Setter for movie id
     * @param movieId to set
     */
    public void setMovieId(int movieId) {
        this.mMovieId = movieId;
    }

    /**
     * Getter for title
     * @return title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Setter for title
     * @param title to set
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * Getter for backdrop path
     * @return backdrop path
     */
    public String getBackdropPath() {
        return mBackdropPath;
    }

    /**
     * Setter for backdrop path
     * @param backdropPath to set
     */
    public void setBackdropPath(String backdropPath) {
        this.mBackdropPath = backdropPath;
    }

    /**
     * Getter for image path
     * @return image path
     */
    public String getImagePath() {
        return mImagePath;
    }

    /**
     * Setter for image path
     * @param mImagePath to set
     */
    public void setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    /**
     * Getter for overview
     * @return overview
     */
    public String getOverview() {
        return mOverview;
    }

    /**
     * Setter for overview
     * @param overview to set
     */
    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    /**
     * Getter for release date
     * @return release date
     */
    public Date getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Setter for release date
     * @param releaseDate to set
     */
    public void setReleaseDate(Date releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    /**
     * Getter for runtime
     * @return runtime
     */
    public int getRuntime() {
        return mRuntime;
    }

    /**
     * Setter for runtime
     * @param runtime to set
     */
    public void setRuntime(int runtime) {
        this.mRuntime = runtime;
    }

    /**
     * Getter for vote avaerage
     * @return vote average
     */
    public double getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Setter for vote average
     * @param voteAverage to set
     */
    public void setVoteAverage(double voteAverage) {
        this.mVoteAverage = voteAverage;
    }
}
