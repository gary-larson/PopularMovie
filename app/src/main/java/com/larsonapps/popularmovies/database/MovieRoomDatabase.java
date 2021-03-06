package com.larsonapps.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to deal with movie database
 */
@Database(entities = {MovieControlEntity.class, MovieDetailEntity.class,
        MovieDetailReviewListEntity.class, MovieDetailVideoListEntity.class,
        MovieListEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class MovieRoomDatabase extends RoomDatabase {


    public abstract MovieControlDao movieControlDao();
    public abstract MovieDetailDao movieDetailDao();
    public abstract MovieDetailReviewListDao movieDetailReviewListDao();
    public abstract MovieDetailVideoListDao movieDetailVideoListDao();
    public abstract MovieListDao movieListDao();

    private static volatile MovieRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
