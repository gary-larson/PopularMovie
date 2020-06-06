package com.larsonapps.popularmovies.data;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.utilities.MovieExecutor;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import com.larsonapps.popularmovies.utilities.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Executor;

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
    private static String mType;
    private MovieDetails mMovieDetails;
    private Application mApplication;
    private String mErrorMessage;
    private static MutableLiveData<Result<MovieMain>> mMovieMain = new MutableLiveData<>();


    // TODO add room logic
    // TODO add movie lists to end instead of replacing them with room

    /**
     * Constructor for movie list repository
     * @param application to use background operations
     */
    public MovieListRepository (Application application) {
        mApplication = application;
        mApiKey = loadApiKey();
        mErrorMessage = mApplication.getString(R.string.error_message);
        executor = new MovieExecutor();
    }

    /**
     * Method to start background task to get movies result list
     */
    public MutableLiveData<Result<MovieMain>> getMovieMain(String mType, int page) {
        if (mType.equals(mApplication.getString(R.string.setting_movie_list_favorite_value))) {
            // TODO process favorite with room
            Result<MovieMain> result = new Result.Error<>(mApplication.getString(R.string.type_favorite_none_message));
        } else {
            // TODO process with room
            retrieveMovieList(mApiKey, mType, page, new RepositoryCallback<MovieMain>() {
                @Override
                public void onComplete(Result<MovieMain> result) {
                    mMovieMain.postValue(result);
                }
            });
        }
        return mMovieMain;
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
        executor.execute(new Runnable() {
            @Override
            public void run() {
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
