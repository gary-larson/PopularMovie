package com.larsonapps.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.larsonapps.popularmovies.data.MovieControl;

import java.util.Date;

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

    @Query("SELECT * FROM movie_control WHERE list_type = :listType")
    MovieControl getMovieControlEntry(String listType);

    @Query("SELECT highest_page FROM movie_control WHERE list_type = :listType")
    int getHighestPage(String listType);
}
