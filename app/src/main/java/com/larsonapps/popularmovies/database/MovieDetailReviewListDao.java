package com.larsonapps.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.larsonapps.popularmovies.data.MovieDetailReviewResult;

import java.util.List;

/**
 * Data Access Object class for the MovieDetailReviewListEntity class
 */
@Dao
public interface MovieDetailReviewListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReviewEntry(MovieDetailReviewListEntity movieDetailReviewListEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllReviewEntries(List<MovieDetailReviewListEntity> list);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateReviewEntry (MovieDetailReviewListEntity movieDetailReviewListEntity);

    @Delete
    void deleteReviewEntry (MovieDetailReviewListEntity movieDetailReviewListEntity);

    @Query("DELETE FROM movie_detail_review_list WHERE movie_id = :movieId")
    void deleteAllReviews (int movieId);

    @Query("SELECT EXISTS (SELECT 1 FROM movie_detail_review_list WHERE movie_id = :movieId LIMIT 1)")
    boolean isReviews(int movieId);

    @Query("SELECT *  FROM movie_detail_review_list WHERE movie_id = :movieId ORDER BY review_list_key ASC")
    List<MovieDetailReviewListEntity> getAllReviews(int movieId);
}
