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
    void insertControl(MovieDetailReviewListEntity movieDetailReviewListEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateControl (MovieDetailReviewListEntity movieDetailReviewListEntity);

    @Delete
    void deleteControl (MovieDetailReviewListEntity movieDetailReviewListEntity);

    @Query("SELECT author, content, url  FROM movie_detail_review_list WHERE movie_id = :movieId")
    List<MovieDetailReviewResult> getAllReviews(int movieId);
}
