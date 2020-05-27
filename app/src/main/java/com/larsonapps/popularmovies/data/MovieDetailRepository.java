package com.larsonapps.popularmovies.data;

import android.app.Application;
import android.os.AsyncTask;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.larsonapps.popularmovies.MovieDetailsActivity;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Locale;

public class MovieDetailRepository {
    // Declre v
    private static String mApiKey;

    private Application mApplication;
    private static MutableLiveData<MovieDetails> mMovieDetails = new MutableLiveData<>();

    public MovieDetailRepository (Application application) {
        mApplication = application;
        mApiKey = loadApiKey();
    }

    /**
     * Method to start background task to get movies from The movie Database
     */
    public MutableLiveData<MovieDetails> getMovieDetails(int movieId) {
        String[] myString = {mApiKey, Integer.toString(movieId)};
        // start background task
        new FetchMovieDetailsTask().execute(myString);
        return mMovieDetails;
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

    /**
     * Class to run background task
     */
    static class FetchMovieDetailsTask extends AsyncTask<String, Void, MovieDetails> {

        /**
         * Method to run the background task
         * @param params 0: api key, 1: type of query, 2: page number
         * @return Movie Information responded from The Movie Database
         */
        @Override
        protected MovieDetails doInBackground(String... params) {

            /* Without information we cannot look up Movies. */
            if (params.length == 0) {
                return null;
            }

            // build url
            int movieId = Integer.parseInt(params[1]);
            URL movieRequestUrl = NetworkUtilities.buildDetailsUrl(params[0],movieId);
            try {
                // attempt to get movie information
                String jsonMovieResponse = NetworkUtilities
                        .getResponseFromHttpUrl(movieRequestUrl);
                // if null cancel task (Unknown error)
                if (jsonMovieResponse == null) {
                    cancel(true);
                }
                // return Json decoded movie Information
                return MovieJsonUtilities
                        .getMovieDetails(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                // in case of an error return null
                return null;
            }
        }

        /**
         * Method to clean up
         * @param details to process
         */
        @Override
        protected void onPostExecute(MovieDetails details) {
            // send results through livedata
            mMovieDetails.postValue(details);
        }

        // if background task is cancelled show error message
        @Override
        protected void onCancelled(MovieDetails movieDetails) {
            super.onCancelled(movieDetails);
            mMovieDetails.postValue(null);
        }
    }
}
