package com.larsonapps.popularmovies.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class of the room table named movie_list
 */
@Entity (tableName = "movie_list")
public class MovieListEntity {

    // Generate columns
    @PrimaryKey()
    @ColumnInfo(name = "movie_id")
    private int mMovieId;

    @ColumnInfo(name = "poster_path")
    private String mPosterPath;

    @ColumnInfo(name = "image_path")
    private String mImagePath;

    @ColumnInfo(name = "popular_page", defaultValue = "0")
    private int mPopularPage;

    @ColumnInfo(name = "popular_order", defaultValue = "0")
    private int mPopularOrder;

    @ColumnInfo(name = "top_rated_page", defaultValue = "0")
    private int mTopRatedPage;

    @ColumnInfo(name = "top_rated_order", defaultValue = "0")
    private int mTopRatedOrder;

    @ColumnInfo(name = "favorite_order", defaultValue = "0")
    private int mFavoriteOrder;


    public MovieListEntity(int movieId, String posterPath, String imagePath, int popularPage, int popularOrder,
                           int topRatedPage, int topRatedOrder, int favoriteOrder) {
        this.mMovieId = movieId;
        this.mPosterPath = posterPath;
        this.mImagePath = imagePath;
        this.mPopularPage = popularPage;
        this.mPopularOrder = popularOrder;
        this.mTopRatedPage = topRatedPage;
        this.mTopRatedOrder = topRatedOrder;
        this.mFavoriteOrder = favoriteOrder;
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
    public void setMovieId(int movieId) {this.mMovieId = movieId;}

    /**
     * Getter for poster path
     * @return poster path
     */
    public String getPosterPath() {
        return mPosterPath;
    }

    /**
     * Setter for poster path
     * @param posterPath to set
     */
    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
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
     * Getter for popular page
     * @return popular page
     */
    public int getPopularPage() {
        return mPopularPage;
    }

    /**
     * Setter for popular page
     * @param popularPage to set
     */
    public void setPopularPage(int popularPage) {
        this.mPopularPage = popularPage;
    }

    /**
     * Getter for popular order
     * @return popular order
     */
    public int getPopularOrder() {
        return mPopularOrder;
    }

    /**
     * Setter for popular order
     * @param popularOrder to set
     */
    public void setPopularOrder(int popularOrder) {
        this.mPopularOrder = popularOrder;
    }

    /**
     * Getter for top rated page
     * @return top rated page
     */
    public int getTopRatedPage() {
        return mTopRatedPage;
    }

    /**
     * Setter for top rated page
     * @param topRatedPage to set
     */
    public void setTopRatedPage(int topRatedPage) {
        this.mTopRatedPage = topRatedPage;
    }

    /**
     * Getter for top rated order
     * @return top rated order
     */
    public int getTopRatedOrder() {
        return mTopRatedOrder;
    }

    /**
     * Setter for top rated order
     * @param topRatedOrder to set
     */
    public void setTopRatedOrder(int topRatedOrder) {
        this.mTopRatedOrder = topRatedOrder;
    }

    /**
     * Getter for favorite order
     * @return favorite order
     */
    public int getFavoriteOrder() {
        return mFavoriteOrder;
    }

    /**
     * Setter for favorite order
     * @param favoriteOrder to set
     */
    public void setFavoriteOrder(int favoriteOrder) {
        this.mFavoriteOrder = favoriteOrder;
    }
}
