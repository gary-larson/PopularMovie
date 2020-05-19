package com.larsonapps.popularmovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;
import java.util.Date;

/**
 * Class of the room table named favorites
 */
@Entity (tableName = "favorites")
public class MovieFavoriteEntity {

    // Generate columns
    @PrimaryKey(autoGenerate = true)
    private int favoriteKeyId;
    private String posterPath;
    private String backDropPath;
    private String overview;
    private OffsetDateTime releaseDate;
    private int movieId;
    private String originalTitle;
    private String title;
    private double voteAverage;

    /**
     * Constructor used for room INSERT
     * @param posterPath to set
     * @param backDropPath to set
     * @param overview to set
     * @param releaseDate to set
     * @param originalTitle to set
     * @param title to set
     * @param voteAverage to set
     */
    @Ignore
    public MovieFavoriteEntity (String posterPath, String backDropPath, String overview,
                                OffsetDateTime releaseDate, int movieId, String originalTitle,
                                String title, double voteAverage) {
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    /**
     * Constructor for room SELECT
     * @param favoriteKeyId to set
     * @param posterPath to set
     * @param backDropPath to set
     * @param overview to set
     * @param releaseDate to set
     * @param originalTitle to set
     * @param title to set
     * @param voteAverage to set
     */
    public MovieFavoriteEntity (int favoriteKeyId, String posterPath, String backDropPath,
                                String overview, OffsetDateTime releaseDate, int movieId,
                                String originalTitle, String title, double voteAverage) {
        this.favoriteKeyId = favoriteKeyId;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    /**
     * Getter for primary key (primary key)
     * @return favoriteKeyId
     */
    public int getFavoriteKeyId() {
        return favoriteKeyId;
    }

    /**
     * Getter for poster path
     * @return poster path
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Setter for poster path
     * @param posterPath to set
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * Getter for back drop path
     * @return back drop path
     */
    public String getBackDropPath() {
        return backDropPath;
    }

    /**
     * Setter for back drop path
     * @param backDropPath to set
     */
    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
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
    public OffsetDateTime getReleaseDate() {
        return releaseDate;
    }

    /**
     * Setter for release date
     * @param releaseDate to set
     */
    public void setReleaseDate(OffsetDateTime releaseDate) {
        this.releaseDate = releaseDate;
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
     * Getter for original title
     * @return original title
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Setter for original title
     * @param originalTitle to set
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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
     * Getter for vote average
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
