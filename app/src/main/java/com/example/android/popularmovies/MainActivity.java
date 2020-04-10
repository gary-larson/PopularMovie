package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    // Declare variables
    private String mApiKey;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;
    private MovieAdapter mAdapter;
    private RecyclerView mMovieList;
    private int mTotalPages;
    private MenuItem mPreviousMenuItem;
    private MenuItem mNextMenuItem;
    static int mWidth;
    static int mHeight;
    private int mPage = 0;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieve the width and height of the screen
        mWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;
        mHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        // get the display density
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // adjust height for title bar
        mHeight -= metrics.densityDpi / 2;

        // retrieve TMDB API Key from assets folder
        mApiKey = getmApiKey();
        mErrorMessageTextView = findViewById(R.id.tv_error_message);
        // display error if no API key is returned
        if (mApiKey.equals("")) {
            showErrorMessage();
            mErrorMessageTextView.setText("API KEY is not available.\nPlease correct and Try again!");
        } else {
            // initialize variables
            mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
            mMovieList = findViewById(R.id.rv_movies);
            // Setup Layout manager for RecyclerView
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            mMovieList.setLayoutManager(layoutManager);
            // indicate all poster are the same size
            mMovieList.setHasFixedSize(true);
            // setup Movie adapter for RecyclerView
            mAdapter = new MovieAdapter();
            mMovieList.setAdapter(mAdapter);
            // Set initial query for TMDB
            mType = NetworkUtilities.TMDB_POPULAR_REQUEST_URL;
            mPage = 1;
            getMovies();
        }
    }

    /**
     * Method to get the api key from the assets folder
     * @return api key or null if not found
     */
    private String getmApiKey() {
        String apiKey = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets()
                    .open("tmdb_key.txt")));
            apiKey = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

    private void getMovies() {
        String[] myString = {mApiKey, mType, Integer.toString(mPage)};
        // start background task
        new FetchMoviesTask().execute(myString);
    }

    /**
     * Class to run background task
     */
    public class FetchMoviesTask extends AsyncTask<String, Void, MovieInformation> {

        /**
         * Method to shoe loading indicator
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        /**
         * Method to run the background task
         * @param params 0: api key, 1: type of query, 2: page number
         * @return Movie Information resonded from TMDB
         */
        @Override
        protected MovieInformation doInBackground(String... params) {

            /* Without information we cannot look up Movies. */
            if (params.length == 0) {
                return null;
            }

            // build url
            URL movieRequestUrl = NetworkUtilities.buildUrl(params[0], params[1], params[2]);

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
        protected void onPostExecute(MovieInformation movieData) {
            // turn off loading indicator
            mLoadingIndicator.setVisibility(View.GONE);
            // if results are good load data to adapter
            if (movieData.getmMovieResults() != null) {
                showMovieDataView();
                mTotalPages = movieData.getmTotalPages();
                mAdapter.setMovieData(movieData);
            } else {
                // if error display status message from TMDB
                showErrorMessage();
                if (movieData.getmStatusMessage() != null)
                mErrorMessageTextView.setText(movieData.getmStatusMessage());
            }
        }

        // if background task is cancelled show error message
        @Override
        protected void onCancelled(MovieInformation movieInformation) {
            super.onCancelled(movieInformation);
            // turn off loading indicator
            mLoadingIndicator.setVisibility(View.GONE);
            showErrorMessage();
        }
    }

    /**
     * Inflate the main menu
     * @param menu to inflate
     * @return true to indicate menu inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mPreviousMenuItem = menu.getItem(1);
        mNextMenuItem =  menu.getItem(2);
        return true;
    }

    /**
     * Method to process items clicked on menu
     * @param item to process
     * @return whether item is handled by this method or super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        switch (menuItemThatWasSelected) {
            case R.id.action_sort:
                // if sort is selected switch movies
                if (item.getTitle().equals("Sort Rating")) {
                    // get Highest Rated Movies
                    item.setTitle("Sort Popular");
                    setTitle("Highest Rated Movies");
                    mPage = 1;
                    mPreviousMenuItem.setEnabled(false);
                    mType = NetworkUtilities.TMDB_HIGHEST_RATED_REQUEST_URL;
                    getMovies();
                } else {
                    // Get Most Popular Movies
                    item.setTitle("Sort Rating");
                    setTitle("Popular Movies");
                    mPage = 1;
                    mPreviousMenuItem.setEnabled(false);
                    mType = NetworkUtilities.TMDB_POPULAR_REQUEST_URL;
                    getMovies();
                }
                return true;
            case R.id.action_previous_page:
                // Get previous page of Movies
                mPage--;
                if (mPage == 1) {
                    item.setEnabled(false);
                }
                mNextMenuItem.setEnabled(true);
                getMovies();
                return true;
            case R.id.action_next_page:
                // Get Next Page of Movies
                mPage++;
                mPreviousMenuItem.setEnabled(true);
                if (mPage == mTotalPages) {
                    item.setEnabled(false);
                }
                getMovies();
                return true;
            case R.id.action_about:
                // Show about activity
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to show error message text view and hide RecyclerView
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mMovieList.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Method to show RecyclerView and hide Error message text view
     */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mMovieList.setVisibility(View.VISIBLE);
    }
}
