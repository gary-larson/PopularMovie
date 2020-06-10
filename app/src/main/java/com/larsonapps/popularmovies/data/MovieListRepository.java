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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        // Declare variables
        mApplication = application;
        mApiKey = loadApiKey();
        mErrorMessage = mApplication.getString(R.string.error_message);
        executor = new MovieExecutor();
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieControlDao = db.movieControlDao();
        mMovieListDao = db.movieListDao();
    }

    /**
     * Method to get movie lists (popular, top rated or favorite) from either room database or
     * The Movie Database
     */
    public MutableLiveData<Result<MovieMain>> getMovieMain(final String mType, final int page) {
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            // flag to get movies from internet
            boolean isDone = false;
            // Check if lists are current
            // Get current time
            Calendar calendar = new GregorianCalendar();
            // make midnight
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            // put in Date
            Date now = calendar.getTime();
            // get popular list control
            MovieControlEntity controlEntity =
                    mMovieControlDao.getMovieListControlEntry(
                            mApplication.getString(R.string.setting_movie_list_popular_value));
            // if download date prior to today delete list
            if (controlEntity != null && controlEntity.getDownloadDate().before(now)) {
                mMovieControlDao.deleteControl(controlEntity);
                mMovieListDao.deleteMoviePopularList();
            }
            // get top rated control
            controlEntity = mMovieControlDao.getMovieListControlEntry(
                    mApplication.getString(R.string.setting_movie_list_top_rated_value));
            // if download date prior to today delete list
            if (controlEntity != null && controlEntity.getDownloadDate().before(now)) {
                mMovieControlDao.deleteControl(controlEntity);
                mMovieListDao.detletMovieTopRatedList();
            }
            // check if type is favorite and get list if any have been selected
            if (mType.equals(mApplication.getString(R.string.setting_movie_list_favorite_value))) {
                // get favorite list from room database
                List<MovieListEntity> movieEntityResults = mMovieListDao.getAllFavoriteMovies();
                // if results process them
                if (movieEntityResults.size() > 0) {
                    // create variable
                    MovieMain movieMain = new MovieMain();
                    // transfer entity list to movie results list
                    List<MovieResult> movieResults = getMovieResults(movieEntityResults);
                    // put list in variable
                    movieMain.setMovieList(movieResults);
                    // set page in variable
                    movieMain.setTotalPages(page);
                    // create a result success to transfer through live data
                    Result<MovieMain> result = new Result.Success<>(movieMain);
                    mMovieMain.postValue(result);
                } else {
                    // send message if no favorites have been selected
                    Result<MovieMain> result = new Result.Error<>(mApplication.getString(R.string.type_favorite_none_message));
                    mMovieMain.postValue(result);
                }
                // flag not to get movies from internet
                isDone = true;
            } else if (mType.equals(mApplication.getString(R.string.setting_movie_list_popular_value))) {
                // get movie control entry
                MovieControlEntity movieControl = mMovieControlDao.getMovieListControlEntry(
                        mApplication.getString(R.string.setting_movie_list_popular_value));
                // test whether need to get more movies or just retrieve from room database
                if (movieControl != null && page <= movieControl.getHighestPage()) {
                    // get movie list entries from room database
                    List<MovieListEntity> movieEntityResults = mMovieListDao.getAllPopularMovies();
                    // if results process them
                    if (movieEntityResults.size() > 0) {
                        // create variable for list
                        MovieMain movieMain = new MovieMain();
                        // transfer list from movie entity to movie results
                        List<MovieResult> movieResults = getMovieResults(movieEntityResults);
                        // put list in variable
                        movieMain.setMovieList(movieResults);
                        // put total pages in variable
                        movieMain.setTotalPages(movieControl.getTotalPages());
                        // Create a result success to transfer through live data
                        Result<MovieMain> result = new Result.Success<>(movieMain);
                        mMovieMain.postValue(result);
                        // flag not to get movies from internet
                        isDone = true;
                    }
                }
            } else {
                // Get movie control entry
                MovieControlEntity movieControl = mMovieControlDao.getMovieListControlEntry(
                        mApplication.getString(R.string.setting_movie_list_top_rated_value));
                // test whether need to get more movies or just retrieve from room database
                if (movieControl != null && page <= movieControl.getHighestPage()) {
                    // get movie list entries from room database
                    List<MovieListEntity> movieEntityResults = mMovieListDao.getAllTopRatedMovies();
                    // if results process them
                    if (movieEntityResults.size() > 0) {
                        // create variable for list
                        MovieMain movieMain = new MovieMain();
                        // transfer list from movie entity to movie results
                        List<MovieResult> movieResults = getMovieResults(movieEntityResults);
                        // put list in variable
                        movieMain.setMovieList(movieResults);
                        // put total pages in variable
                        movieMain.setTotalPages(movieControl.getTotalPages());
                        // Create a result success to transfer through live data
                        Result<MovieMain> result = new Result.Success<>(movieMain);
                        mMovieMain.postValue(result);
                        // flag not to get movies from internet
                        isDone = true;
                    }
                }
            }
            if (!isDone) {
                // Request movie list (or additional page) from internet
                retrieveMovieList(mApiKey, mType, page, result -> {
                    // test result
                    if (result instanceof Result.Success) {
                        // extract results
                        Result.Success<MovieMain> movieMainSuccess = (Result.Success<MovieMain>)
                                result;
                        // get movie control entry
                        MovieControlEntity movieControlEntity =
                                mMovieControlDao.getMovieListControlEntry(mType);
                        // if no movie control create one and populate then save
                        if (movieControlEntity == null) {
                            movieControlEntity = new MovieControlEntity(mType,
                                    new Date(System.currentTimeMillis()), page,
                                    movieMainSuccess.data.getTotalPages(), 0);
                            mMovieControlDao.insertControl(movieControlEntity);
                        } else {
                            // populate existing movie control entry with new information then update
                            movieControlEntity.setHighestPage(page);
                            movieControlEntity.setTotalPages(movieMainSuccess.data.getTotalPages());
                            movieControlEntity.setDownloadDate(new Date(System.currentTimeMillis()));
                            mMovieControlDao.updateControl(movieControlEntity);
                        }
                        // loop through movie list from internet
                        for (int i = 0; i < movieMainSuccess.data.getMovieList().size(); i++) {
                            // create variable for entry and populate
                            MovieResult movieResult = movieMainSuccess.data.getMovieList().get(i);
                            // test for movie in room database
                            if (mMovieListDao.isMovieListEntry(movieResult.getMovieID())) {
                                // get movie entry
                                MovieListEntity movieListEntity = mMovieListDao.
                                        getMovieListEntry(movieResult.getMovieID());
                                // put proper page and order depending on type of list
                                if (mType.equals(mApplication.getString(
                                        R.string.setting_movie_list_popular_value))) {
                                    movieListEntity.setPopularOrder(i + 1);
                                    movieListEntity.setPopularPage(page);
                                } else {
                                    movieListEntity.setTopRatedOrder(i + 1);
                                    movieListEntity.setTopRatedPage(page);
                                }
                                // update entry in room database
                                mMovieListDao.updateMovieListEntry(movieListEntity);
                            } else {
                                // create entry
                                MovieListEntity movieListEntity = new MovieListEntity(
                                        movieResult.getMovieID(), movieResult.getPosterPath(),
                                        null,0,0,0,
                                        0,0);
                                // put proper page and order depending on type of list
                                if (mType.equals(mApplication.getString(
                                        R.string.setting_movie_list_popular_value))) {
                                    movieListEntity.setPopularOrder(i + 1);
                                    movieListEntity.setPopularPage(page);
                                } else {
                                    movieListEntity.setTopRatedOrder(i + 1);
                                    movieListEntity.setTopRatedPage(page);
                                }
                                // insert entry in room database
                                mMovieListDao.insertMovieListEntry(movieListEntity);
                            }
                        }
                        // create movie entity list
                        List<MovieListEntity> movieListEntities;
                        // get entire movie list depending on list type from room database
                        if (mType.equals(mApplication.getString(
                                R.string.setting_movie_list_popular_value))) {
                            movieListEntities = mMovieListDao.getAllPopularMovies();
                        } else {
                            movieListEntities = mMovieListDao.getAllTopRatedMovies();
                        }
                        // transfer movie entity list to movie result list
                        List<MovieResult> movieResults = getMovieResults(movieListEntities);
                        // Creat a Result success entry to pass list through live data
                        movieMainSuccess.data.setMovieList(movieResults);
                    }
                    // post results to live data
                    mMovieMain.postValue(result);
                });
            }
        });
        // return results
        return mMovieMain;
    }

    /**
     * Method to transfer list from room database to movie result list
     * @param movieEntityResults to process into result list
     * @return result list
     */
    private List<MovieResult> getMovieResults(List<MovieListEntity> movieEntityResults) {
        // create list to return
        List<MovieResult> movieResults = new ArrayList<>();
        // loop through entity list
        for (int i = 0; i < movieEntityResults.size(); i++) {
            // create movie result to hold entity data
            MovieResult movieResult = new MovieResult();
            // transfer data from entity to result
            movieResult.setImagePath(movieEntityResults.get(i).getImagePath());
            movieResult.setMovieID(movieEntityResults.get(i).getMovieId());
            movieResult.setPosterPath(movieEntityResults.get(i).getPosterPath());
            // add movie result to lisr
            movieResults.add(movieResult);
        }
        // return result list
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
