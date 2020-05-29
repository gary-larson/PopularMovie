package com.larsonapps.popularmovies.data;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Class to retrieve movie list data
 */
public class MovieListRepository {
    private static String mApiKey;

    private static String mType;
    private MovieDetails mMovieDetails;
    private Application mApplication;
    private static MutableLiveData<MovieMain> mMovieMain = new MutableLiveData<>();


    // TODO add room logic
    // TODO add movie lists to end instead of replacing them

    /**
     * Constructor for movie list repository
     * @param application to use background operations
     */
    public MovieListRepository (Application application) {
        mApplication = application;
        mApiKey = loadApiKey();
    }

    /**
     * Method to start background task to get movies result list
     */
    public MutableLiveData<MovieMain> getMovieMain(String mType, int page) {
        String[] myString = {mApiKey, mType, Integer.toString(page)};
        // start background task
        new FetchMovieListTask().execute(myString);
        return mMovieMain;
    }

    /**
     * Class to run background task
     */
    static class FetchMovieListTask extends AsyncTask<String, Void, MovieMain> {

        /**
         * Method to run the background task
         * @param params 0: api key, 1: type of query, 2: page number
         * @return Movie Information responded from The Movie Database
         */
        @Override
        protected MovieMain doInBackground(String... params) {

            /* Without information we cannot look up Movies. */
            if (params.length == 0) {
                return null;
            }

            // build url
            URL movieRequestUrl = MovieNetworkUtilities.buildResultsUrl(params[0], params[1], params[2]);

            try {
                // attempt to get movie information
                String jsonMovieResponse = MovieNetworkUtilities
                        .getResponseFromHttpUrl(movieRequestUrl);
                // if null cancel task (Unknown error)
                if (jsonMovieResponse == null) {
                    cancel(true);
                }
                // return Json decoded movie Information
                return MovieJsonUtilities
                        .getMovieResults(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                // in case of an error return null
                return null;
            }
        }

        /**
         * Method to clean up
         * @param movieData to process
         */
        @Override
        protected void onPostExecute(MovieMain movieData) {
            // send data to fragment through live data
            mMovieMain.postValue(movieData);
        }

        // if background task is cancelled show error message
        @Override
        protected void onCancelled(MovieMain movieData) {
            super.onCancelled(movieData);
            // send no data through live data
            mMovieMain.postValue(null);
        }
    }

    public String getApiKey () {return mApiKey;}

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
