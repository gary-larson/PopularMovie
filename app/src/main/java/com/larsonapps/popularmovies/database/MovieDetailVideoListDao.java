package com.larsonapps.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.larsonapps.popularmovies.data.MovieDetailVideo;

import java.util.List;

/**
 * Data Access Object class for the MovieDetailVideoListEntity class
 */
@Dao
public interface MovieDetailVideoListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrailer(MovieDetailVideoListEntity movieDetailVideoListEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTrailer (MovieDetailVideoListEntity movieDetailVideoListEntity);

    @Delete
    void deleteTrailer (MovieDetailVideoListEntity movieDetailVideoListEntity);

    @Query("SELECT site_key, name, site FROM movie_detail_video_list WHERE movie_id = :movieId ORDER BY video_list_key ASC")
    LiveData<List<MovieDetailVideo>> getAllVideos(int movieId);
}
