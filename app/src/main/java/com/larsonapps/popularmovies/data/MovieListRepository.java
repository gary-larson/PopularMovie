package com.larsonapps.popularmovies.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.larsonapps.popularmovies.data.MovieDetails;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieListRepository {
    private static String mApiKey;

    private static String mType;
    private static MovieMain mMovieMain;
    private MovieDetails mMovieDetails;
    private Application mApplication;
    private static MutableLiveData<List<String>> mPosterUrls = new MutableLiveData<List<String>>();

    public MovieListRepository (Application application) {
        mApplication = application;
        mApiKey = loadApiKey();
        mMovieMain = new MovieMain();
        mType = NetworkUtilities.POPULAR_REQUEST_URL;
    }

    /**
     * Method to start background task to get movies from The movie Database
     */
    public MutableLiveData<List<String>> getPosterUrls() {
//        if (mPosterUrls.getValue().size() > 0) {
//            return mPosterUrls;
//        }
        String[] myString = {mApiKey, mType, Integer.toString(mMovieMain.getPage())};
        // start background task
        new FetchMovieListTask().execute(myString);
        return mPosterUrls;
    }

    /**
     * Class to run background task
     */
    static class FetchMovieListTask extends AsyncTask<String, Void, List<MovieResult>> {

        /**
         * Method to run the background task
         * @param params 0: api key, 1: type of query, 2: page number
         * @return Movie Information responded from The Movie Database
         */
        @Override
        protected List<MovieResult> doInBackground(String... params) {

            /* Without information we cannot look up Movies. */
            if (params.length == 0) {
                return null;
            }

            // build url
            URL movieRequestUrl = NetworkUtilities.buildResultsUrl(params[0], params[1], params[2]);

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
        protected void onPostExecute(List<MovieResult> movieData) {
            // if results are good load data to adapter
            if (movieData != null) {
                mMovieMain.setMovieList(movieData); // TODO replace with put in room
                List<String> posterUrls = new ArrayList<>();
                for (int i = 0; i < movieData.size(); i++) {
                    posterUrls.add(movieData.get(i).getPosterPath());
                }
                mPosterUrls.postValue(posterUrls);
            } else {
                // if error display status message from The Movie Database
                /* First, hide the currently visible data */
                mMovieMain.setErrorMessage("Error retrieving Data");
            }
        }

        // if background task is cancelled show error message
        @Override
        protected void onCancelled(List<MovieResult> movieResults) {
            super.onCancelled(movieResults);
            // turn off loading indicator

            mMovieMain = null;
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