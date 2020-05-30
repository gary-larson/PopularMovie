package com.larsonapps.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

/**
 * Class for Movie activity
 */
public class MovieActivity extends AppCompatActivity implements
        MovieItemFragment.OnListFragmentInteractionListener {
    // Declare CONSTANTS
    public final static String DETAIL_MOVIE_ID_KEY = "movie_id";
    // Declare variables
    public static int mNumberHorizontalImages;
    public static int mNumberVerticalImages;
    public static String mPosterSize;
    MovieListViewModel mMovieListViewModel;
    String mTitle;
    boolean isNextEnabled;
    private MenuItem mMoreMovieMenuItem;
    SharedPreferences mSharedPreferences;

    /**
     * Method to create the movie activity
     * @param savedInstanceState variables for state persistence
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        // Get information from demension to apply changes is screen size and orientation
        mNumberVerticalImages = getResources().getInteger(R.integer.number_vertical_posters);
        mNumberHorizontalImages = getResources().getInteger(R.integer.number_horizontal_posters);
        mPosterSize = getResources().getString(R.string.poster_size);
        // initialize the movie list view model
        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        // initialize information for menus
        // Get shared preferences
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String type = mSharedPreferences.getString(
                getString(R.string.setting_movie_list_key),
                getString(R.string.setting_movie_list_popular_value));
        // Set title based on movie list type preference
        if (type.equals(getString(R.string.setting_movie_list_popular_value))) {
            mTitle = getString(R.string.setting_movie_list_popular_label);
        } else if (type.equals(getString(R.string.setting_movie_list_favorite_value))) {
            mTitle = getString(R.string.setting_movie_list_favorite_label);
        } else {
            mTitle = getString(R.string.setting_movie_list_top_rated_label);
        }
        // set movie list preference in view model
        mMovieListViewModel.setType(type);
        // set title of activity
        setTitle(mTitle);
    }

    /**
     * Listener for the adapter to react to movie clicked
     * @param movieResult of the movie clicked
     */
    @Override
    public void onListFragmentInteraction(MovieResult movieResult) {
        Intent detailIntent = new Intent(this, MovieDetailsActivity.class);
        detailIntent.putExtra(DETAIL_MOVIE_ID_KEY, movieResult.getMovieID());
        startActivity(detailIntent);
    }

    /**
     * Inflate the main menu
     * @param menu to inflate
     * @return true to indicate menu inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMoreMovieMenuItem =  menu.getItem(0);
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
            case R.id.action_more_movies:
                // Get Next Page of Movies
                // TODO fix add to list instead of replace list use page number
                mMovieListViewModel.setPage(mMovieListViewModel.getPage() + 1);
                if (mMovieListViewModel.getPage() == mMovieListViewModel.getTotalPages()) {
                    isNextEnabled = false;
                    item.setEnabled(isNextEnabled);
                }
                mMovieListViewModel.retrieveMovieMain();
                return true;
            case R.id.action_settings:
                // Show settings activity
                Intent settingsIntent = new Intent(this, MovieSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_about:
                // Show about activity
                Intent aboutIntent = new Intent(this, MovieAboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public MenuItem getMoreMovieMenuItem () {
        return mMoreMovieMenuItem;
    }
}
