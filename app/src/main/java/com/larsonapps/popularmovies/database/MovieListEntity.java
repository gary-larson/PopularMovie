package com.larsonapps.popularmovies.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;
import java.util.Date;

/**
 * Class of the room table named movie_list
 */
@Entity (tableName = "movie_list")
public class MovieListEntity {

    // Generate columns
    @PrimaryKey()
    private int movieId;
    private String posterPath;
    @ColumnInfo(defaultValue = "0")
    private int popularPage;
    @ColumnInfo(defaultValue = "0")
    private int popularOrder;
    @ColumnInfo(defaultValue = "0")
    private int topRatedPage;
    @ColumnInfo(defaultValue = "0")
    private int topRatedOrder;
    @ColumnInfo(defaultValue = "0")
    private int favoriteOrder;


    public MovieListEntity(int movieId, String posterPath, int popularPage, int popularOrder,
                           int topRatedPage, int topRatedOrder, int favoriteOrder) {
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.popularPage = popularPage;
        this.popularOrder = popularOrder;
        this.topRatedPage = topRatedPage;
        this.topRatedOrder = topRatedOrder;
        this.favoriteOrder = favoriteOrder;
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
    public void setMovieId(int movieId) {this.movieId = movieId;}

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
     * Getter for popular page
     * @return popular page
     */
    public int getPopularPage() {
        return popularPage;
    }

    /**
     * Setter for popular page
     * @param popularPage to set
     */
    public void setPopularPage(int popularPage) {
        this.popularPage = popularPage;
    }

    /**
     * Getter for popular order
     * @return popular order
     */
    public int getPopularOrder() {
        return popularOrder;
    }

    /**
     * Setter for popular order
     * @param popularOrder to set
     */
    public void setPopularOrder(int popularOrder) {
        this.popularOrder = popularOrder;
    }

    /**
     * Getter for top rated page
     * @return top rated page
     */
    public int getTopRatedPage() {
        return topRatedPage;
    }

    /**
     * Setter for top rated page
     * @param topRatedPage to set
     */
    public void setTopRatedPage(int topRatedPage) {
        this.topRatedPage = topRatedPage;
    }

    /**
     * Getter for top rated order
     * @return top rated order
     */
    public int getTopRatedOrder() {
        return topRatedOrder;
    }

    /**
     * Setter for top rated order
     * @param topRatedOrder to set
     */
    public void setTopRatedOrder(int topRatedOrder) {
        this.topRatedOrder = topRatedOrder;
    }

    /**
     * Getter for favorite order
     * @return favorite order
     */
    public int getFavoriteOrder() {
        return favoriteOrder;
    }

    /**
     * Setter for favorite order
     * @param favoriteOrder to set
     */
    public void setFavoriteOrder(int favoriteOrder) {
        this.favoriteOrder = favoriteOrder;
    }
}
