package com.larsonapps.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import android.util.Base64;

import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;


public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {
    // Declare variables
    private String mApiKey;
    // variables to be saved in bundle
    private List<MovieResults> mMovieList;
    private int mPage = 0;
    private String mType;
    private static int mTotalPages;
    private static String mErrorMessage;
    private String mTitle;
    private String mSortTitle;
    private boolean isPreviousEnabled;
    private boolean isNextEnabled;
    // variables needed to deal with UI
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;
    private MovieAdapter mAdapter;
    private RecyclerView mMovieRecyclerView;
    private MenuItem mSortMenuItem;
    private MenuItem mPreviousMenuItem;
    private MenuItem mNextMenuItem;
    // Variables for size, image numbers, etc.
    public static int mNumberHorizontalImages;
    public static int mNumberVerticalImages;
    public static String mPosterSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        mErrorMessageTextView = findViewById(R.id.tv_error_message);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mMovieRecyclerView = findViewById(R.id.rv_movies);
        mNumberVerticalImages = getResources().getInteger(R.integer.number_vertical_posters);
        mNumberHorizontalImages = getResources().getInteger(R.integer.number_horizontal_posters);
        mPosterSize = getResources().getString(R.string.poster_size);


        // retrieve The Movie Database API Key from assets folder
        mApiKey = getApiKey();
        // display error if no API key is returned
        if (mApiKey.equals("")) {
            /* First, hide the currently visible data */
            mMovieRecyclerView.setVisibility(View.INVISIBLE);
            /* Then, show the error */
            mErrorMessageTextView.setVisibility(View.VISIBLE);
            mErrorMessageTextView.setText("API KEY is not available.\nPlease correct and Try again!");
        } else {
            // Setup Layout manager for RecyclerView
            GridLayoutManager layoutManager = new GridLayoutManager(this,
                    getResources().getInteger(R.integer.number_horizontal_posters));
            mMovieRecyclerView.setLayoutManager(layoutManager);
            // indicate all poster are the same size
            mMovieRecyclerView.setHasFixedSize(true);
            // setup Movie adapter for RecyclerView
            mAdapter = new MovieAdapter(this);
            mMovieRecyclerView.setAdapter(mAdapter);
            // test for saved bundle
            if (savedInstanceState == null || !savedInstanceState.containsKey("MOVIES")) {
                // Setup initial query for The Movie Database
                mType = NetworkUtilities.POPULAR_REQUEST_URL;
                mPage = 1;
                mSortTitle = "Sort Rating";
                mTitle = "Pop Movies";
                isPreviousEnabled = false;
                isNextEnabled = true;
                // call background thread to get movies from the internet
                getMovies();
            } else {
                // load information from bundle
                mMovieList = savedInstanceState.getParcelableArrayList("MOVIES");
                mPage = savedInstanceState.getInt("PAGE");
                mType = savedInstanceState.getString("TYPE");
                mTotalPages = savedInstanceState.getInt("TOTAL_PAGES");
                mErrorMessage = savedInstanceState.getString("ERROR_MESSAGE");
                mTitle = savedInstanceState.getString("TITLE");
                mSortTitle = savedInstanceState.getString("SORT");
                isPreviousEnabled = savedInstanceState.getBoolean("PREVIOUS");
                isNextEnabled = savedInstanceState.getBoolean("NEXT");
                // send information to adapter
                mAdapter.setMovieData(mMovieList);
            }
            // set activity title
            setTitle(mTitle);
        }
    }

    /**
     * Method to save information to reduce calls to the internet
     * @param outState to save information to
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // test for information
        if (mMovieList != null) {
            // save information to bundle
            outState.putParcelableArrayList("MOVIES", (ArrayList<? extends Parcelable>) mMovieList);
            outState.putInt("PAGE", mPage);
            outState.putString("TYPE", mType);
            outState.putInt("TOTAL_PAGES", mTotalPages);
            outState.putString("ERROR_MESSAGE", mErrorMessage);
            outState.putString("TITLE", mTitle);
            outState.putString("SORT", mSortTitle);
            outState.putBoolean("PREVIOUS", isPreviousEnabled);
            outState.putBoolean("NEXT", isNextEnabled);
        }
        // call super to save state
        super.onSaveInstanceState(outState);
    }

    /**
     * Method to get the api key from the assets folder
     * @return api key or null if not found
     */
    private String getApiKey() {
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

    /**
     * Method to start background task to get movies from The movie Database
     */
    private void getMovies() {
        String[] myString = {mApiKey, mType, Integer.toString(mPage)};
        // start background task
        new FetchMoviesTask(this).execute(myString);
    }

    /**
     * Method to start the movie details activity
     * @param clickedItemResults the information to send to movie details activity
     */
    @Override
    public void onClick(MovieResults clickedItemResults) {
        // Create a bundle to send information to details activity
        Bundle extras = new Bundle();
        // Put necessary information in bundle
        extras.putString("TITLE",clickedItemResults.getTitle());
        extras.putString("BACKDROP_PATH", clickedItemResults.getBackDropPath());
        extras.putLong("RELEASE_DATE", clickedItemResults.getReleaseDate().getTime());
        extras.putString("ORIGINAL_TITLE", clickedItemResults.getOriginalTitle());
        extras.putDouble("VOTE_AVERAGE", clickedItemResults.getVoteAverage());
        extras.putString("OVERVIEW", clickedItemResults.getOverview());
        // Get the context
        Context context = this;
        // Get the receiving activity class
        Class destinationClass = MovieDetailsActivity.class;
        // create intent
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        // add bundle
        intentToStartDetailActivity.putExtras(extras);
        // start detail activity
        startActivity(intentToStartDetailActivity);
    }

    /**
     * Class to run background task
     */
    static class FetchMoviesTask extends AsyncTask<String, Void, List<MovieResults>> {
        private final WeakReference<MainActivity> activityWeakReference;

        FetchMoviesTask(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * Method to shoe loading indicator
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        /**
         * Method to run the background task
         * @param params 0: api key, 1: type of query, 2: page number
         * @return Movie Information responded from The Movie Database
         */
        @Override
        protected List<MovieResults> doInBackground(String... params) {

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
        protected void onPostExecute(List<MovieResults> movieData) {
            MainActivity activity = activityWeakReference.get();
            // turn off loading indicator
            activity.mLoadingIndicator.setVisibility(View.GONE);
            // if results are good load data to adapter
            if (movieData != null) {
                /* First, make sure the error is invisible */
                activity.mErrorMessageTextView.setVisibility(View.INVISIBLE);
                /* Then, make sure the weather data is visible */
                activity.mMovieRecyclerView.setVisibility(View.VISIBLE);
                activity.mMovieList = movieData;
                activity.mAdapter.setMovieData(movieData);
            } else {
                // if error display status message from The Movie Database
                /* First, hide the currently visible data */
                activity.mMovieRecyclerView.setVisibility(View.INVISIBLE);
                /* Then, show the error */
                activity.mErrorMessageTextView.setVisibility(View.VISIBLE);
                if (activity.mErrorMessageTextView != null) {
                    activity.mErrorMessageTextView.setText(mErrorMessage);
                }
            }
        }

        // if background task is cancelled show error message
        @Override
        protected void onCancelled(List<MovieResults> movieResults) {
            super.onCancelled(movieResults);
            // turn off loading indicator
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.mLoadingIndicator.setVisibility(View.GONE);
            /* First, hide the currently visible data */
            activity.mMovieRecyclerView.setVisibility(View.INVISIBLE);
            /* Then, show the error */
            activity.mErrorMessageTextView.setVisibility(View.VISIBLE);
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
        mSortMenuItem = menu.getItem(0);
        mPreviousMenuItem = menu.getItem(1);
        mNextMenuItem =  menu.getItem(2);
        mSortMenuItem.setTitle(mSortTitle);
        mPreviousMenuItem.setEnabled(isPreviousEnabled);
        mNextMenuItem.setEnabled(isNextEnabled);
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
                    mSortTitle = "Sort Popular";
                    mTitle = "Rated Movies";
                    mPage = 1;
                    mType = NetworkUtilities.HIGHEST_RATED_REQUEST_URL;
                    getMovies();
                } else {
                    // Get Most Popular Movies
                    mSortTitle = "Sort Rating";
                    mTitle = "Pop Movies";
                    mPage = 1;
                    mType = NetworkUtilities.POPULAR_REQUEST_URL;
                    getMovies();
                }
                isPreviousEnabled = false;
                mPreviousMenuItem.setEnabled(isPreviousEnabled);
                item.setTitle(mSortTitle);
                setTitle(mTitle);
                return true;
            case R.id.action_previous_page:
                // Get previous page of Movies
                mPage--;
                if (mPage == 1) {
                    isPreviousEnabled = false;
                    item.setEnabled(isPreviousEnabled);
                }
                isNextEnabled = true;
                mNextMenuItem.setEnabled(isNextEnabled);
                getMovies();
                return true;
            case R.id.action_next_page:
                // Get Next Page of Movies
                mPage++;
                isPreviousEnabled = true;
                mPreviousMenuItem.setEnabled(isPreviousEnabled);
                if (mPage == mTotalPages) {
                    isNextEnabled = false;
                    item.setEnabled(isNextEnabled);
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

    public String getmApiKey() {
        return mApiKey;
    }

    public static int getmTotalPages() {
        return mTotalPages;
    }

    public static String getmErrorMessage() {
        return mErrorMessage;
    }

    public static void setmTotalPages(int mTotalPages) {
        MainActivity.mTotalPages = mTotalPages;
    }

    public static void setmErrorMessage(String mErrorMessage) {
        MainActivity.mErrorMessage = mErrorMessage;
    }
}
