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
    private MovieDetails mMovieDetails;
    private Application mApplication;
    private static MutableLiveData<MovieMain> mMovieMain = new MutableLiveData<>();

    public MovieListRepository (Application application) {
        mApplication = application;
        mApiKey = loadApiKey();
    }

    /**
     * Method to start background task to get movies from The movie Database
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
        protected void onPostExecute(MovieMain movieData) {
            // if results are good load data to adapter
            if (movieData.getErrorMessage().equals("")) {
                List<String> posterUrls = new ArrayList<>();
                List<MovieResult> movieResults = movieData.getMovieList();
                for (int i = 0; i < movieResults.size(); i++) {
                    posterUrls.add(movieResults.get(i).getPosterPath());
                }
                movieData.setPosterUrls(posterUrls);
            }
            mMovieMain.postValue(movieData); // TODO replace with put in room
        }

        // if background task is cancelled show error message
        @Override
        protected void onCancelled(MovieMain movieData) {
            super.onCancelled(movieData);
            // turn off loading indicator

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
