package com.larsonapps.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("DELETE FROM movie_list")
    void deleteAllMovieList();

    @Query("UPDATE movie_list SET popular_page = 0, popular_order = 0 WHERE popular_order > 0")
    void deleteMoviePopularList();

    @Query("UPDATE movie_list SET top_rated_page = 0, top_rated_order = 0 WHERE top_rated_order > 0")
    void detletMovieTopRatedList();

    @Query("SELECT * FROM movie_list")
    List<MovieListEntity> getAllMovieListEntrys();

    @Query("SELECT EXISTS(SELECT 1 FROM movie_list WHERE movie_id = :movieId LIMIT 1)")
    boolean isMovieListEntry(int movieId);

    @Query("SELECT * FROM movie_list WHERE movie_id = :movieId")
    MovieListEntity getMovieListEntry(int movieId);

    @Query("SELECT * FROM movie_list WHERE popular_order > 0 ORDER BY popular_page ASC, popular_order ASC")
    List<MovieListEntity> getAllPopularMovies();

    @Query("SELECT * FROM movie_list WHERE top_rated_order > 0 ORDER BY top_rated_page ASC, top_rated_order ASC")
    List<MovieListEntity> getAllTopRatedMovies();

    @Query("SELECT * FROM movie_list WHERE favorite_order > 0 ORDER BY favorite_order ASC")
    List<MovieListEntity> getAllFavoriteMovies();

    @Query("SELECT MAX(favorite_order) FROM movie_list")
    int getMaximumFavoriteOrder();
}
