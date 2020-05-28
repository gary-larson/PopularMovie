package com.larsonapps.popularmovies;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;

import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

/**
 * Class to deal with the settings activity
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Method to deal with activity creation
     * @param savedInstanceState to maintain state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Get fragment manager
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        // get Action bar
        ActionBar actionBar = getSupportActionBar();
        // enable up button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Class for the fragment preferences
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {

        /**
         * Method to create the preferences from xml
         * @param savedInstanceState to save state
         * @param rootKey of the preferences
         */
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}