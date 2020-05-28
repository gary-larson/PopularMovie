package com.larsonapps.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Class for the about activity
 */
public class AboutActivity extends AppCompatActivity {

    /**
     * Method to create the about activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.about);
    }
}
