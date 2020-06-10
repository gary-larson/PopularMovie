package com.larsonapps.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object class for the MovieDetailReviewListEntity class
 */
@Dao
public interface MovieDetailReviewListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllReviewEntries(List<MovieDetailReviewListEntity> list);

    @Query("DELETE FROM movie_detail_review_list WHERE movie_id = :movieId")
    void deleteAllReviews (int movieId);

    @Query("SELECT *  FROM movie_detail_review_list WHERE movie_id = :movieId ORDER BY review_list_key ASC")
    List<MovieDetailReviewListEntity> getAllReviews(int movieId);
}
