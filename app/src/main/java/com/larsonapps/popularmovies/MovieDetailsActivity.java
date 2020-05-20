package com.larsonapps.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieDetailsActivity extends AppCompatActivity {
    // declare variables
    private String temp = "";
    int mWidth;
    int mMovieId;
    TextView mErrorMessageTextView;
    ProgressBar mLoadingIndicator;
    TextView mTitleTextView;
    ImageView mMovieImageView;
    TextView mReleaseDateTextView;
    TextView mRuntimeTextView;
    TextView mVoteAverageTextView;
    TextView mOverviewTextView;
    private static String mDetailsErrorMessage;
    private static String mVideosErrorMessage;
    private static String mReviewsErrorMessage;
    private String mApiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int height = displayMetrics.heightPixels;
        mWidth = (displayMetrics.widthPixels /
                getResources().getInteger(R.integer.backdrop_divisor) *
                getResources().getInteger(R.integer.backdrop_multiplier));
        setTitle("MovieDetail");
        // initialize variables
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTextView = findViewById(R.id.tv_error_message);
        mTitleTextView = findViewById(R.id.title_text_view);
        mMovieImageView = findViewById(R.id.poster_image_view);
        mReleaseDateTextView = findViewById(R.id.release_date_text_view);
        mRuntimeTextView = findViewById(R.id.runtime_text_view);
        mVoteAverageTextView = findViewById(R.id.voter_rating_text_view);
        mOverviewTextView = findViewById(R.id.overview_text_view);
        //get the intent in the target activity
        Intent intent = getIntent();
        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();
        //Retrieve Title from the bundle and displaying it
        // Lint is producing a warning of a possible null pointer exception
        // I believe it is an error but am covering it anyway.
        try {
            temp = extras.getString("TITLE");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        mTitleTextView.setText(temp);
        // Retrieve Poster path from bundle and display it
       // mWidth = mMovieImageView.getMeasuredWidth();
        String backDropPath = extras.getString("BACKDROP_PATH");
        if(backDropPath != null){
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
                    getResources().getString(R.string.backdrop_size) + backDropPath)
                    .noPlaceholder()
                    .error(R.mipmap.error)
                    .resize(mWidth, (mWidth * 9) / 16)
                    .into(mMovieImageView);
        }
        // Retrieve Release date and display it
        // Release date is stored as a Date type so we can format the Date
        Date releaseDate = new Date(extras.getLong("RELEASE_DATE"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy",
                Locale.getDefault());
        mReleaseDateTextView.setText(dateFormat.format(releaseDate));
        // Retrieve the Movie Id from bundle
        mMovieId = extras.getInt("MOVIE_ID");
        // Retrieve Voter average from bundle and display it
        String tempString = String.format(Locale.getDefault(), "%.1f/10", extras.getDouble("VOTE_AVERAGE"));
        mVoteAverageTextView.setText(tempString);
        // Retrieve overview from bundle and display it
        mOverviewTextView.setText(extras.getString("OVERVIEW"));
        mApiKey = extras.getString("API_KEY");
        getMovieDetails();
    }

    private void getMovieDetails() {
            String[] myString = {mApiKey, Integer.toString(mMovieId)};
            // start background task
            new FetchMovieDetailsTask(this).execute(myString);
    }

    /**
     * Class to run background task
     */
    static class FetchMovieDetailsTask extends AsyncTask<String, Void, MovieDetails> {
        private final WeakReference<MovieDetailsActivity> activityWeakReference;

        FetchMovieDetailsTask(MovieDetailsActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * Method to show loading indicator
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MovieDetailsActivity activity = activityWeakReference.get();
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
        protected MovieDetails doInBackground(String... params) {

            /* Without information we cannot look up Movies. */
            if (params.length == 0) {
                return null;
            }

            // build url
            int movieId = Integer.parseInt(params[1]);
            URL movieRequestUrl = NetworkUtilities.buildDetailsUrl(params[0],movieId);
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
                        .getMovieDetails(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                // in case of an error return null
                return null;
            }
        }

        /**
         * Method to clean up
         * @param details to process
         */
        @Override
        protected void onPostExecute(MovieDetails details) {
            MovieDetailsActivity activity = activityWeakReference.get();
            // turn off loading indicator
            activity.mLoadingIndicator.setVisibility(View.GONE);
            // if results are good load data to adapter
            if (details != null) {
                activity.mRuntimeTextView.setText(String.format(Locale.getDefault(),
                        "%d Minutes", details.getRuntime()));

            } else {
                // if error display status message from The Movie Database
                if (mDetailsErrorMessage != null) {
                    activity.mRuntimeTextView.setText(mDetailsErrorMessage);
                }
            }
        }

        // if background task is cancelled show error message
        @Override
        protected void onCancelled(MovieDetails movieDetails) {
            super.onCancelled(movieDetails);
            // turn off loading indicator
            MovieDetailsActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.mLoadingIndicator.setVisibility(View.GONE);
            /* Then, show the error */
            activity.mErrorMessageTextView.setVisibility(View.VISIBLE);
        }
    }
}
