package com.larsonapps.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.larsonapps.popularmovies.data.MovieDetailInfo;
import com.larsonapps.popularmovies.data.MovieDetailSummary;
import com.larsonapps.popularmovies.data.MovieDetails;
import com.larsonapps.popularmovies.data.MovieResult;

import java.util.List;

/**
 * Data Access Object class for the MovieDetailEntity class
 */
@Dao
public interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovieDetailEntry(MovieDetailEntity movieDetailEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovieDetailEntry (MovieDetailEntity movieDetailEntity);

    @Delete
    void deleteMovieDetailEntry (MovieDetailEntity movieDetailEntity);

    @Query("SELECT EXISTS(SELECT 1 FROM movie_detail WHERE movie_id = :movieId LIMIT 1)")
    boolean isMovieDetailEntry(int movieId);

    @Query("SELECT movie_id, title, backdrop_path, image_path, overview FROM movie_detail WHERE movie_id = :movieId")
    MovieDetailInfo getMovieInfo( int movieId);

    @Query("SELECT release_date, runtime, vote_average FROM movie_detail WHERE movie_id = :movieId")
    MovieDetailSummary getMovieSummary(int movieId);
}
