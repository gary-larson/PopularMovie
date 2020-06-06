package com.larsonapps.popularmovies;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

/**
 * Class to handle settings fragment
 */
public class MovieSettingsFragment extends PreferenceFragmentCompat {

    /**
     * Method to create preferences fragment
     * @param savedInstanceState to maintain state
     * @param rootKey of the preferences
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        // get activity
        MovieActivity movieActivity = (MovieActivity) getActivity();
        // set title and enable up button
        if (movieActivity != null) {
            movieActivity.setTitle(getString(R.string.title_fragment_settings));
            movieActivity.getmActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Method to reset fragment and distribute type and get new list
     */
    @Override
    public void onPause() {
        super.onPause();
        // Get Activity
        MovieActivity activity = (MovieActivity) getActivity();
        // Get shared preferences
        if (activity != null) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(activity.getApplicationContext());
            String type = sharedPreferences.getString(
                    getString(R.string.setting_movie_list_key),
                    getString(R.string.setting_movie_list_popular_value));
            // when new type selected get data from view model
            if (!type.equals(activity.mMovieListViewModel.getType())) {
                activity.mMovieListViewModel.setType(type);
                activity.mMovieDetailViewModel.setType(type);
                activity.mMovieListViewModel.setPage(1);
                activity.mMovieListViewModel.retrieveMovieMain();
            }
            // reset title
            activity.convertTypeToTitleAndSet(type);
            // disable up button
            activity.getmActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     * Method to set up fragment
     */
    @Override
    public void onResume() {
        super.onResume();
        // get activity
        MovieActivity movieActivity = (MovieActivity) getActivity();
        // set title and enable up button
        if (movieActivity != null) {
            movieActivity.setTitle(getString(R.string.title_fragment_settings));
            movieActivity.getmActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}