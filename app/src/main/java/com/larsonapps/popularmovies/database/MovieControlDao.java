package com.larsonapps.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object class for the MovieControlEntity class
 */
@Dao
public interface MovieControlDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertControl(MovieControlEntity movieControlEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateControl (MovieControlEntity movieControlEntity);

    @Delete
    void deleteControl (MovieControlEntity movieControlEntity);

    @Query("DELETE FROM movie_control")
    void deleteAllMovieControl();

    @Query("SELECT * FROM movie_control")
    List<MovieControlEntity> getAllMovieControlEntrys();

    @Query("SELECT EXISTS(SELECT 1 FROM movie_control WHERE movie_id = :movieId AND list_type = :listType LIMIT 1)")
    boolean isEntry(int movieId, String listType);

    @Query("SELECT * FROM movie_control WHERE list_type = :listType")
    MovieControlEntity getMovieListControlEntry(String listType);

    @Query("SELECT highest_page FROM movie_control WHERE list_type = :listType")
    int getHighestPage(String listType);

    @Query("SELECT * FROM movie_control WHERE list_type = :listType AND movie_id = :movieId")
    MovieControlEntity getMovieReviewControlEntry(String listType, int movieId);
}
