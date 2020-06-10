package com.larsonapps.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Data Access Object class for the MovieDetailEntity class
 */
@Dao
public interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovieDetailEntry(MovieDetailEntity movieDetailEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovieDetailEntry (MovieDetailEntity movieDetailEntity);

    @Query("SELECT EXISTS(SELECT 1 FROM movie_detail WHERE movie_id = :movieId LIMIT 1)")
    boolean isMovieDetailEntry(int movieId);

    @Query("SELECT * FROM movie_detail WHERE movie_id = :movieId")
    MovieDetailEntity getMovieDetails( int movieId);
}
