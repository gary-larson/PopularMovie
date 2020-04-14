package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailsActivity extends AppCompatActivity {
    private String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setTitle("MovieDetail");
        //get the intent in the target activity
        Intent intent = getIntent();
        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();
        //Retrieve Title from the bundle and displaying it
        TextView mTitleTextView = findViewById(R.id.title_text_view);
        // Lint is producing a warning of a possible null pointer exception
        // I believe it is an error but am covering it anyway.
        try {
            temp = extras.getString("TITLE");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        mTitleTextView.setText(temp);
        // Retrieve Poster path from bundle and display it
        ImageView mMovieImageView = findViewById(R.id.poster_image_view);
        String posterPath = extras.getString("POSTER_PATH");
        if(posterPath != null){
            String urlString;
            // Set whether or not to use ssl based on API build
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                urlString = NetworkUtilities.POSTER_BASE_HTTPS_URL;
            } else{
                urlString = NetworkUtilities.POSTER_BASE_HTTP_URL;
            }
            // Utilize Picasso to load the poster into the image view
            // using noPlaceHolder because picasso had blank spaces on some emulators
            // and this fixed the issue
            Picasso.get().load(urlString +
                    NetworkUtilities.POSTER_SIZE + posterPath)
                    .noPlaceholder()
                    .error(R.mipmap.error)
                    .into(mMovieImageView);
        }
        // Retrieve Release date and display it
        // Release date is stored as a Date type so we can format the Date
        TextView mReleaseDateTextView = findViewById(R.id.release_date_text_view);
        Date releaseDate = new Date(extras.getLong("RELEASE_DATE"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy",
                Locale.getDefault());
        mReleaseDateTextView.setText(dateFormat.format(releaseDate));
        // Retrieve the Original date from bundle and display it
        TextView mOriginalTitleTextView = findViewById(R.id.original_title_text_view);
        mOriginalTitleTextView.setText(extras.getString("ORIGINAL_TITLE"));
        // Retrieve Voter average from bundle and display it
        TextView mVoteAverageTextView = findViewById(R.id.voter_rating_text_view);
        String tempString = String.format(Locale.getDefault(), "%.1f/10", extras.getDouble("VOTE_AVERAGE"));
        mVoteAverageTextView.setText(tempString);
        // Retrieve overview from bundle and display it
        TextView mOverviewTextView = findViewById(R.id.overview_text_view);
        mOverviewTextView.setText(extras.getString("OVERVIEW"));
    }
}
