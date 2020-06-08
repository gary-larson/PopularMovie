package com.larsonapps.popularmovies.data;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.database.MovieControlDao;
import com.larsonapps.popularmovies.database.MovieControlEntity;
import com.larsonapps.popularmovies.database.MovieListDao;
import com.larsonapps.popularmovies.database.MovieListEntity;
import com.larsonapps.popularmovies.database.MovieRoomDatabase;
import com.larsonapps.popularmovies.utilities.MovieExecutor;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import com.larsonapps.popularmovies.utilities.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Interface for the callback
 * @param <T>
 */
interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}

/**
 * Class to retrieve movie list data
 */
public class MovieListRepository {
    // Declare variables
    private static String mApiKey;
    private MovieExecutor executor;
    private Application mApplication;
    private String mErrorMessage;
    private static MutableLiveData<Result<MovieMain>> mMovieMain = new MutableLiveData<>();
    // database variables
    private MovieControlDao mMovieControlDao;
    private MovieListDao mMovieListDao;

    /**
     * Constructor for movie list repository
     * @param application to use background operations
     */
    public MovieListRepository (Application application) {
        mApplication = application;
        mApiKey = loadApiKey();
        mErrorMessage = mApplication.getString(R.string.error_message);
        executor = new MovieExecutor();
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieControlDao = db.movieControlDao();
        mMovieListDao = db.movieListDao();
    }

    /**
     * Method to start background task to get movies result list
     */
    public MutableLiveData<Result<MovieMain>> getMovieMain(final String mType, final int page) {
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            boolean isDone = false;
            if (mType.equals(mApplication.getString(R.string.setting_movie_list_favorite_value))) {
                List<MovieListEntity> movieEntityResults = mMovieListDao.getAllFavoriteMovies();
                if (movieEntityResults.size() > 0) {
                    MovieMain movieMain = new MovieMain();
                    List<MovieResult> movieResults = getMovieResults(movieEntityResults);
                    movieMain.setMovieList(movieResults);
                    MovieControlEntity movieControl = mMovieControlDao.getMovieControlEntry(
                            mApplication.getString(R.string.setting_movie_list_favorite_value));
                    if (movieControl != null) {
                        movieMain.setTotalPages(movieControl.getTotalPages());
                    } else {
                        movieMain.setTotalPages(page);
                    }
                    Result<MovieMain> result = new Result.Success<>(movieMain);
                    mMovieMain.postValue(result);
                } else {
                    Result<MovieMain> result = new Result.Error<>(mApplication.getString(R.string.type_favorite_none_message));
                    mMovieMain.postValue(result);
                }
                isDone = true;
            } else if (mType.equals(mApplication.getString(R.string.setting_movie_list_popular_value))) {
                MovieControlEntity movieControl = mMovieControlDao.getMovieControlEntry(
                        mApplication.getString(R.string.setting_movie_list_popular_value));
                if (movieControl != null && page <= movieControl.getHighestPage()) {
                    List<MovieListEntity> movieEntityResults = mMovieListDao.getAllPopularMovies();
                    if (movieEntityResults.size() > 0) {
                        MovieMain movieMain = new MovieMain();
                        List<MovieResult> movieResults = getMovieResults(movieEntityResults);
                        movieMain.setMovieList(movieResults);
                        movieMain.setTotalPages(movieControl.getTotalPages());
                        Result<MovieMain> result = new Result.Success<>(movieMain);
                        mMovieMain.postValue(result);
                        isDone = true;
                    }
                }
            } else {
                MovieControlEntity movieControl = mMovieControlDao.getMovieControlEntry(
                        mApplication.getString(R.string.setting_movie_list_top_rated_value));
                if (movieControl != null && page <= movieControl.getHighestPage()) {
                    List<MovieListEntity> movieEntityResults = mMovieListDao.getAllTopRatedMovies();
                    if (movieEntityResults.size() > 0) {
                        MovieMain movieMain = new MovieMain();
                        List<MovieResult> movieResults = getMovieResults(movieEntityResults);
                        movieMain.setMovieList(movieResults);
                        movieMain.setTotalPages(movieControl.getTotalPages());
                        Result<MovieMain> result = new Result.Success<>(movieMain);
                        mMovieMain.postValue(result);
                        isDone = true;
                    }
                }
            }
            if (!isDone) {
                retrieveMovieList(mApiKey, mType, page, result -> {
                    if (result instanceof Result.Success) {
                        Result.Success<MovieMain> movieMainSuccess = (Result.Success<MovieMain>)
                                result;
                        MovieControlEntity movieControlEntity =
                                mMovieControlDao.getMovieControlEntry(mType);
                        if (movieControlEntity == null) {
                            movieControlEntity = new MovieControlEntity(mType,
                                    new Date(System.currentTimeMillis()), page,
                                    movieMainSuccess.data.getTotalPages(), 0);
                        }
                        if (mMovieControlDao.isEntry(0, mType)) {
                            mMovieControlDao.updateControl(movieControlEntity);
                        } else {
                            mMovieControlDao.insertControl(movieControlEntity);
                        }
                        for (int i = 0; i < movieMainSuccess.data.getMovieList().size(); i++) {
                            MovieResult movieResult = movieMainSuccess.data.getMovieList().get(i);
                            if (mMovieListDao.isMovieListEntry(movieResult.getMovieID())) {
                                MovieListEntity movieListEntity = mMovieListDao.
                                        getMovieListEntry(movieResult.getMovieID());
                                if (mType.equals(mApplication.getString(
                                        R.string.setting_movie_list_popular_value))) {
                                    movieListEntity.setPopularOrder(i + 1);
                                    movieListEntity.setPopularPage(page);
                                } else {
                                    movieListEntity.setTopRatedOrder(i + 1);
                                    movieListEntity.setTopRatedPage(page);
                                }
                                mMovieListDao.updateMovieListEntry(movieListEntity);
                            } else {
                                MovieListEntity movieListEntity = new MovieListEntity(
                                        movieResult.getMovieID(), movieResult.getPosterPath(),
                                        null,0,0,0,
                                        0,0);
                                if (mType.equals(mApplication.getString(
                                        R.string.setting_movie_list_popular_value))) {
                                    movieListEntity.setPopularOrder(i + 1);
                                    movieListEntity.setPopularPage(page);
                                } else {
                                    movieListEntity.setTopRatedOrder(i + 1);
                                    movieListEntity.setTopRatedPage(page);
                                }
                                mMovieListDao.insertMovieListEntry(movieListEntity);
                            }
                        }
                        mMovieControlDao.updateControl(movieControlEntity);
                        List<MovieListEntity> movieListEntities;
                        if (mType.equals(mApplication.getString(
                                R.string.setting_movie_list_popular_value))) {
                            movieListEntities = mMovieListDao.getAllPopularMovies();
                        } else {
                            movieListEntities = mMovieListDao.getAllTopRatedMovies();
                        }
                        List<MovieResult> movieResults = getMovieResults(movieListEntities);
                        movieMainSuccess.data.setMovieList(movieResults);
                    }
                    mMovieMain.postValue(result);
                });
            }
        });
        return mMovieMain;
    }

    private List<MovieResult> getMovieResults(List<MovieListEntity> movieEntityResults) {
        List<MovieResult> movieResults = new ArrayList<>();
        for (int i = 0; i < movieEntityResults.size(); i++) {
            MovieResult movieResult = new MovieResult();
            movieResult.setImagePath(movieEntityResults.get(i).getImagePath());
            movieResult.setMovieID(movieEntityResults.get(i).getMovieId());
            movieResult.setPosterPath(movieEntityResults.get(i).getPosterPath());
            movieResults.add(movieResult);
        }
        return movieResults;
    }

    /**
     * Method to retrieve the movie list from the movie database
     * @param apiKey to access the movie database
     * @param type of list
     * @param page page of the list
     * @param callback to handle results
     */
    public void retrieveMovieList (
            final String apiKey, final String type, final int page,
            final RepositoryCallback<MovieMain> callback
            ) {
        executor.execute(() -> {
            // build url
            URL movieRequestUrl = MovieNetworkUtilities.buildResultsUrl(apiKey, type,
                    Integer.toString(page));

            try {
                // attempt to get movie information
                String jsonMovieResponse = MovieNetworkUtilities
                        .getResponseFromHttpUrl(movieRequestUrl);
                // if null cancel task (Unknown error)
                if (jsonMovieResponse == null) {
                    Result<MovieMain> errorResult = new Result.Error<>(mErrorMessage);
                    callback.onComplete(errorResult);
                }
                // return Json decoded movie Information
                Result<MovieMain> result = MovieJsonUtilities
                        .getMovieResults(jsonMovieResponse);
                callback.onComplete(result);
            } catch (Exception e) {
                e.printStackTrace();
                // in case of an error return null
                Result<MovieMain> errorResult = new Result.Error<>(mErrorMessage);
                callback.onComplete(errorResult);
            }
        });
    }

    /**
     * Method to get the api key from the assets folder
     * @return api key or null if not found
     */
    private String loadApiKey() {
        String apiKey = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(mApplication.getAssets()
                    .open("tmdb_key.txt")));
            apiKey = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiKey;
    }


}
