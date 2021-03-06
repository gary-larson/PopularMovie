package com.larsonapps.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object class for the MovieDetailVideoListEntity class
 */
@Dao
public interface MovieDetailVideoListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllVideos(List<MovieDetailVideoListEntity> list);

    @Query("DELETE FROM movie_detail_video_list WHERE movie_id = :movieId")
    void deleteAllVideos (int movieId);

    @Query("SELECT EXISTS (SELECT 1 FROM movie_detail_video_list WHERE movie_id = :movieId LIMIT 1)")
    boolean isVideos(int movieId);

    @Query("SELECT * FROM movie_detail_video_list WHERE movie_id = :movieId ORDER BY video_list_key ASC")
    List<MovieDetailVideoListEntity> getAllVideos(int movieId);
}
