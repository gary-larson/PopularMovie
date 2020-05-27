package com.larsonapps.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

public class MovieActivity extends AppCompatActivity implements
        MovieItemFragment.OnListFragmentInteractionListener {
    // Declare CONSTANTS
    public final static String DETAIL_MOVIE_ID_KEY = "movie_id";
    // Declare variables
    public static int mNumberHorizontalImages;
    public static int mNumberVerticalImages;
    public static String mPosterSize;
    MovieListViewModel mMovieListViewModel;
    private String mTitle;
    private boolean isPreviousEnabled;
    private boolean isNextEnabled;
    private MenuItem mSortMenuItem;
    private MenuItem mPreviousMenuItem;
    private MenuItem mNextMenuItem;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mNumberVerticalImages = getResources().getInteger(R.integer.number_vertical_posters);
        mNumberHorizontalImages = getResources().getInteger(R.integer.number_horizontal_posters);
        mPosterSize = getResources().getString(R.string.poster_size);
        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        isPreviousEnabled = false;
        isNextEnabled = true;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String type = mSharedPreferences.getString(
                getString(R.string.setting_movie_list_key),
                getString(R.string.setting_movie_list_popular_value));
        if (type.equals(getString(R.string.setting_movie_list_popular_value))) {
            mTitle = getString(R.string.setting_movie_list_popular_label);
        } else if (type.equals(getString(R.string.setting_movie_list_favorite_value))) {
            mTitle = getString(R.string.setting_movie_list_favorite_label);
        } else {
            mTitle = getString(R.string.setting_movie_list_top_rated_label);
        }
        mMovieListViewModel.setType(type);
        setTitle(mTitle);
    }


    @Override
    public void onListFragmentInteraction(int position) {
        Intent detailIntent = new Intent(this, MovieDetailsActivity.class);
        detailIntent.putExtra(DETAIL_MOVIE_ID_KEY, mMovieListViewModel.getMovieId(position));
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
        mPreviousMenuItem = menu.getItem(0);
        mNextMenuItem =  menu.getItem(1);
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
            case R.id.action_previous_page:
                // Get previous page of Movies
                mMovieListViewModel.setPage(mMovieListViewModel.getPage() - 1);
                if (mMovieListViewModel.getPage() == 1) {
                    isPreviousEnabled = false;
                    item.setEnabled(isPreviousEnabled);
                }
                isNextEnabled = true;
                mNextMenuItem.setEnabled(isNextEnabled);
                mMovieListViewModel.retrieveMovieMain();
                return true;
            case R.id.action_next_page:
                // Get Next Page of Movies
                mMovieListViewModel.setPage(mMovieListViewModel.getPage() + 1);
                isPreviousEnabled = true;
                mPreviousMenuItem.setEnabled(isPreviousEnabled);
                if (mMovieListViewModel.getPage() == mMovieListViewModel.getTotalPages()) {
                    isNextEnabled = false;
                    item.setEnabled(isNextEnabled);
                }
                mMovieListViewModel.retrieveMovieMain();
                return true;
            case R.id.action_settings:
                // Show settings activity
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_about:
                // Show about activity
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
