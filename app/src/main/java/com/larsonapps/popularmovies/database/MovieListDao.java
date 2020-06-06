package com.larsonapps.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.larsonapps.popularmovies.data.MovieResult;

import java.util.List;

/**
 * Data Access Object class for the MovieListEntity class
 */
@Dao
public interface MovieListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovieListEntry(MovieListEntity movieListEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovieListEntry (MovieListEntity movieListEntity);

    @Delete
    void deleteMovieListEntry (MovieListEntity movieListEntity);

    @Query("SELECT EXISTS(SELECT 1 FROM movie_list WHERE movie_id = :movieId LIMIT 1)")
    boolean isMovieListEntry(int movieId);

    @Query("SELECT * FROM movie_list WHERE movie_id = :movieId")
    MovieListEntity getMovieListEntry(int movieId);

    @Query("SELECT poster_path, movie_id FROM movie_list WHERE popular_order > 0 ORDER BY popular_page ASC, popular_order ASC")
    List<MovieResult> getAllPopularMovies();

    @Query("SELECT poster_path, movie_id FROM movie_list WHERE top_rated_order > 0 ORDER BY top_rated_page ASC, top_rated_order ASC")
    List<MovieResult> getAllTopRatedMovies();

    @Query("SELECT poster_path, image_path, movie_id FROM movie_list WHERE favorite_order > 0 ORDER BY favorite_order ASC")
    List<MovieResult> getAllFavoriteMovies();
}
