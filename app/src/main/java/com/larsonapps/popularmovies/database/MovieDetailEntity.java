package com.larsonapps.popularmovies.database;

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
    @PrimaryKey()
    private int movieId;
    private String title;
    private String backdropPath;
    private String overview;
    private Date releaseDate;
    private int runtime;
    private double voteAverage;

    /**
     * Constructor for room
     * @param movieId to set
     * @param title to set
     * @param backdropPath to set
     * @param overview to set
     * @param releaseDate to set
     * @param runtime to set
     * @param voteAverage to set
     */
    public MovieDetailEntity (int movieId, String title, String backdropPath, String overview,
                              Date releaseDate, int runtime, double voteAverage) {
        this.movieId = movieId;
        this.title = title;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.voteAverage = voteAverage;
    }

    /**
     * Getter for movie id
     * @return movie id
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Setter for movie id
     * @param movieId to set
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     * Getter for title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     * @param title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for backdrop path
     * @return backdrop path
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * Setter for backdrop path
     * @param backdropPath to set
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     * Getter for overview
     * @return overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Setter for overview
     * @param overview to set
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Getter for release date
     * @return release date
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Setter for release date
     * @param releaseDate to set
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Getter for runtime
     * @return runtime
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Setter for runtime
     * @param runtime to set
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Getter for vote avaerage
     * @return vote average
     */
    public double getVoteAverage() {
        return voteAverage;
    }

    /**
     * Setter for vote average
     * @param voteAverage to set
     */
    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
