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
 * Class to handle retrieving movie detail data
 */
public class MovieDetailRepository {
    // Declre variables
    private static String mApiKey;
    private Application mApplication;
    // Live data variables
    private static MutableLiveData<MovieDetailInfo> mMovieDetailInfo = new MutableLiveData<>();
    private static MutableLiveData<MovieDetailSummary> mMovieDetailSummary = new MutableLiveData<>();
    private static MutableLiveData<MovieDetailReview> mMovieDetailReview = new MutableLiveData<>();
    private static MutableLiveData<List<MovieDetailVideo>> mMovieDetailVideoList =
            new MutableLiveData<>();

    // TODO Add room logic

    /**
     * Constructor for movie detail repository
     * @param application used to access data
     */
    public MovieDetailRepository (Application application) {
        mApplication = application;
        mApiKey = loadApiKey();
    }

    /**
     * Method to get movie detail info for the movie detail info live data
     */
    public MutableLiveData<MovieDetailInfo> getMovieDetailInfo(int movieId) {
        // setup for background task
        String[] myString = {mApiKey, Integer.toString(movieId)};
        // start background task
        new FetchMovieDetailsTask().execute(myString);
        // return movie detail info data
        return mMovieDetailInfo;
    }

    /**
     * Method to get movie detail summary for the movie detail summary live data
     */
    public MutableLiveData<MovieDetailSummary> getMovieDetailSummary() {
        // return movie detail summary data
        return mMovieDetailSummary;
    }

    /**
     * Method to get movie detail reviews for the movie detail review live data
     */
    public MutableLiveData<MovieDetailReview> getMovieDetailReview() {
        // return movie detail summary data
        return mMovieDetailReview;
    }

    /**
     * Method to get movie detail videos for the movie detail video live data
     */
    public MutableLiveData<List<MovieDetailVideo>> getMovieDetailVideoList() {
        // return movie detail summary data
        return mMovieDetailVideoList;
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
     * Class to run background task to get movie details
     */
    static class FetchMovieDetailsTask extends AsyncTask<String, Void, MovieDetails> {

        /**
         * Method to run the background task
         * @param params 0: api key, 1: movieId
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
            URL movieRequestUrl = MovieNetworkUtilities.buildDetailsUrl(params[0],movieId);
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
            // send results through live data movie detail info
            MovieDetailInfo movieDetailInfo = details.getMovieDetailInfo();
            mMovieDetailInfo.postValue(movieDetailInfo);
            // send results through live data movie detail summary
            MovieDetailSummary movieDetailSummary = details.getMovieDetailSummary();
            mMovieDetailSummary.postValue(movieDetailSummary);
            // send results through live data movie detail reviews
            MovieDetailReview movieDetailReview = details.getMovieDetailReview();
            mMovieDetailReview.postValue(movieDetailReview);
            // send results through live data movie detail videos
            List<MovieDetailVideo> movieDetailVideos = details.getVideoList();
            mMovieDetailVideoList.postValue(movieDetailVideos);
        }

        // if background task is cancelled show error message
        @Override
        protected void onCancelled(MovieDetails movieDetails) {
            super.onCancelled(movieDetails);
            // send empty data through live data
            mMovieDetailInfo.postValue(null);
            mMovieDetailSummary.postValue(null);
            mMovieDetailReview.postValue(null);
            mMovieDetailVideoList.postValue(null);
        }
    }

    /**
     * Class to run background task to get additional movie detail reviews
     */
    static class FetchMovieDetailReviewsTask extends AsyncTask<String, Void, MovieDetailReview> {

        /**
         * Method to run the background task
         * @param params 0: api key, 1: movie id, 2: page number
         * @return Movie Information responded from The Movie Database
         */
        @Override
        protected MovieDetailReview doInBackground(String... params) {

            /* Without information we cannot look up Movies. */
            if (params.length == 0) {
                return null;
            }

            // build url
            int movieId = Integer.parseInt(params[1]);
            int page = Integer.parseInt(params[2]);
            URL movieRequestUrl = MovieNetworkUtilities.buildReviewsUrl(params[0],movieId, page);
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
                        .getMovieDetailReview(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                // in case of an error return null
                return null;
            }
        }

        /**
         * Method to clean up
         * @param movieDetailReview to process
         */
        @Override
        protected void onPostExecute(MovieDetailReview movieDetailReview) {
            // send results through live data movie detail reviews
            // as addition reviews so add to current list
            if (mMovieDetailReview.getValue() == null) {
                mMovieDetailReview.postValue(movieDetailReview);
            } else {
                if (movieDetailReview.getErrorMessage() == null) {
                    List<MovieDetailReviewResult> results =
                            mMovieDetailReview.getValue().getReviewList();
                    results.addAll(movieDetailReview.getReviewList());
                    mMovieDetailReview.postValue(movieDetailReview);
                }
            }
        }

        // if background task is cancelled show error message
        @Override
        protected void onCancelled(MovieDetailReview movieDetailReview) {
            super.onCancelled(movieDetailReview);
            // send empty data through live data
            if (mMovieDetailReview.getValue() == null) {
                mMovieDetailReview.postValue(null);
            }
        }
    }
}
