package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private String tmdbApiKey;
    private TextView errorMassageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieve TMDB API Key from assets folder
        tmdbApiKey = getTmdbApiKey();
        errorMassageTextView = findViewById(R.id.tv_error_message);
        if (tmdbApiKey.equals("")) {
            errorMassageTextView.setVisibility(View.VISIBLE);
            errorMassageTextView.setText("API KEY is not available.\nPlease correct and Try again!");
        } else {
            errorMassageTextView.setVisibility(View.GONE);
        }

    }

    private String getTmdbApiKey () {
        String apiKey = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("tmdb_key.txt")));
            apiKey = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        switch (menuItemThatWasSelected) {
            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
