package com.larsonapps.popularmovies.database;

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
    private final int mMovieId;

    @ColumnInfo(name = "title")
    private final String mTitle;

    @ColumnInfo(name = "backdrop_path")
    private final String mBackdropPath;

    @ColumnInfo(name = "image_path")
    private String mImagePath;

    @ColumnInfo(name = "overview")
    private final String mOverview;

    @ColumnInfo(name = "release_date")
    private final Date mReleaseDate;

    @ColumnInfo(name = "runtime")
    private final int mRuntime;

    @ColumnInfo(name = "vote_average")
    private final double mVoteAverage;

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
     * Getter for title
     * @return title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Getter for backdrop path
     * @return backdrop path
     */
    public String getBackdropPath() {
        return mBackdropPath;
    }

    /**
     * Getter for image path
     * @return image path
     */
    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {mImagePath = imagePath;}
    /**
     * Getter for overview
     * @return overview
     */
    public String getOverview() {
        return mOverview;
    }

    /**
     * Getter for release date
     * @return release date
     */
    public Date getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Getter for runtime
     * @return runtime
     */
    public int getRuntime() {
        return mRuntime;
    }

    /**
     * Getter for vote average
     * @return vote average
     */
    public double getVoteAverage() {
        return mVoteAverage;
    }

}
