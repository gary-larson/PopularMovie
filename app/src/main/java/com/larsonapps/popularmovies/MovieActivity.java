package com.larsonapps.popularmovies;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.larsonapps.popularmovies.data.MovieDetailReviewResult;
import com.larsonapps.popularmovies.data.MovieDetailVideo;
import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

/**
 * Class for Movie activity
 */
public class MovieActivity extends AppCompatActivity implements
        MovieItemFragment.OnListFragmentInteractionListener,
        MovieDetailVideoFragment.OnListFragmentInteractionListener,
        MovieDetailReviewFragment.OnListFragmentInteractionListener {
    // Declare constants
    final String SETINGS_FRAGMENT = "SettingsFragment";
    final String ABOUT_FRAGMENT = "AboutFragment";
    final String DETAILS_FRAGMENT = "DetailsFragment";
    // Declare variables
    public static int mNumberHorizontalImages;
    public static int mNumberVerticalImages;
    public static String mPosterSize;
    private ActionBar mActionBar;
    MovieListViewModel mMovieListViewModel;
    MovieDetailViewModel mMovieDetailViewModel;
    MenuItem mMoreMovieMenuItem;
    SharedPreferences mSharedPreferences;
    FragmentManager mFragmentManager;

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
        // initialize movie detail view model
        mMovieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        // initialize information for menus
        // Get shared preferences
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String type = mSharedPreferences.getString(
                getString(R.string.setting_movie_list_key),
                getString(R.string.setting_movie_list_popular_value));
        // Set title based on movie list type preference
        convertTypeToTitleAndSet(type);
        // set movie list preference in view model
        mMovieListViewModel.setType(type);
        mMovieDetailViewModel.setType(type);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * Method to determine title based on type and set title
     * @param type of movie list
     */
    public void convertTypeToTitleAndSet (String type) {
        String title;

        // Determine title from type
        if (type.equals(getString(R.string.setting_movie_list_popular_value))) {
            title = getString(R.string.setting_movie_list_popular_label);
        } else if (type.equals(getString(R.string.setting_movie_list_favorite_value))) {
            title = getString(R.string.setting_movie_list_favorite_label);
        } else {
            title = getString(R.string.setting_movie_list_top_rated_label);
        }
        // set title of activity
        setTitle(title);
    }

    /**
     * Listener for the adapter to react to movie clicked
     * @param movieResult of the movie clicked
     */
    @Override
    public void onListFragmentInteraction(MovieResult movieResult) {
        mMovieDetailViewModel.setMovieId(movieResult.getMovieID());
        mFragmentManager
                .beginTransaction()
                .replace(R.id.movie_fragment_container_view, new MovieDetailFragment())
                .addToBackStack(DETAILS_FRAGMENT)
                .commit();
    }

    /**
     * Method to process clicks in the review list
     * @param movieDetailReviewResult is the review to process
     */
    @Override
    public void onListFragmentInteraction(MovieDetailReviewResult movieDetailReviewResult) {
        String url = movieDetailReviewResult.getUrl();
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(url));
        startActivity(webIntent);
    }

    /**
     * Method to process clicks on the trailer list
     * @param movieDetailVideo is the trailer to process
     */
    @Override
    public void onListFragmentInteraction(MovieDetailVideo movieDetailVideo) {
        String url;
        if (movieDetailVideo.getSite().equals("YouTube")) {
            url = MovieNetworkUtilities.YOUTUBE_BASE_URL;
        } else {
            url = MovieNetworkUtilities.VIMEO_BASE_URL;
        }
        url += movieDetailVideo.getKey();
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(url));
        startActivity(webIntent);
    }

    /**
     * Inflate the main menu
     * @param menu to inflate
     * @return true to indicate menu inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate main movie menu
        getMenuInflater().inflate(R.menu.main, menu);
        // set more movie menu item
        mMoreMovieMenuItem = menu.getItem(0);
        return true;
    }

    /**
     * Method to process items clicked on menu
     * @param item to process
     * @return whether item is handled by this method or super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get menu item id
        int menuItemThatWasSelected = item.getItemId();
        // take action depending on item selected
        switch (menuItemThatWasSelected) {
            case R.id.action_settings:
                // Get fragment manager and open settings fragment
                mFragmentManager
                        .beginTransaction()
                        .replace(R.id.movie_fragment_container_view, new MovieSettingsFragment())
                        .addToBackStack(SETINGS_FRAGMENT)
                        .commit();
                return true;
            case R.id.action_about:
                // Get fragment manager and open about fragment
                mFragmentManager
                        .beginTransaction()
                        .replace(R.id.movie_fragment_container_view, new MovieAboutFragment())
                        .addToBackStack(ABOUT_FRAGMENT)
                        .commit();
               // Intent aboutIntent = new Intent(this, MovieAboutActivity.class);
                //startActivity(aboutIntent);
                return true;
            case android.R.id.home: {
                if (mFragmentManager.getBackStackEntryCount() > 0) {
                    mFragmentManager.popBackStack();
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Getter for movie activity action bar
     * @return movie activity action bar
     */
    public ActionBar getmActionBar() {return mActionBar;}
}
