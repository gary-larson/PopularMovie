package com.larsonapps.popularmovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "review_stats")
public class MovieFavoriteReviewStatsEntity {

    // Generate columns
    @PrimaryKey (autoGenerate = true)
    private int statKeyid;
    private int statFavoriteKeyID;
    private int totalPages;
    private int totalReviews;

    /**
     * Constructor for room INSERT
     * @param statFavoriteKeyID to set
     * @param totalPages to set
     * @param totalReviews to set
     */
    @Ignore
    public MovieFavoriteReviewStatsEntity (int statFavoriteKeyID, int totalPages, int totalReviews) {
        this.statFavoriteKeyID = statFavoriteKeyID;
        this.totalPages = totalPages;
        this.totalReviews = totalReviews;
    }

    /**
     * Constructor for room SELECT
     * @param statKeyid to set
     * @param statFavoriteKeyID to set
     * @param totalPages to set
     * @param totalReviews to set
     */
    public MovieFavoriteReviewStatsEntity (int statKeyid, int statFavoriteKeyID, int totalPages,
                                           int totalReviews) {
        this.statKeyid = statKeyid;
        this.statFavoriteKeyID = statFavoriteKeyID;
        this.totalPages = totalPages;
        this.totalReviews = totalReviews;
    }

    /**
     * Getter for stat key id (primary key)
     * @return stat key id
     */
    public int getStatKeyid() {
        return statKeyid;
    }

    /**
     * Getter for stat favorite key id (foreign key)
     * @return stat favorite key id
     */
    public int getStatFavoriteKeyID() {
        return statFavoriteKeyID;
    }

    /**
     * Setter for stat favorite key id (foreign key)
     * @param statFavoriteKeyID to set
     */
    public void setStatFavoriteKeyID(int statFavoriteKeyID) {
        this.statFavoriteKeyID = statFavoriteKeyID;
    }

    /**
     * Getter for total pages
     * @return total pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Setter for total pages
     * @param totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Getter for total reviews
     * @returntotal reviews
     */
    public int getTotalReviews() {
        return totalReviews;
    }

    /**
     * Setter for total reviews
     * @param totalReviews to set
     */
    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }
}
