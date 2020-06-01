package com.larsonapps.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

/**
 * Class for the about activity
 */
public class MovieAboutActivity extends AppCompatActivity {

    /**
     * Method to create the about activity
     * @param savedInstanceState to save state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.about);
    }

    /**
     * Method to return to previous activity
     * @param item to process
     * @return item was processed
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}
