package com.larsonapps.popularmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.larsonapps.popularmovies.data.MovieDetailInfo;
import com.larsonapps.popularmovies.data.MovieDetails;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

/**
 * Activity class for movie details
 */
public class MovieDetailsActivity extends AppCompatActivity {
    // declare variables
    MovieDetails mMovieDetailInfo;
    private MovieDetailViewModel mMovieDetailViewModel;
    int mWidth;
    int mMovieId;
    TextView mErrorMessageTextView;
    ProgressBar mLoadingIndicatorProgressBar;
    TextView mTitleTextView;
    ImageView mMovieImageView;

    /**
     * Method to create movie detail activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Get width of screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // adjust screen width depending on screen size and orientation
        mWidth = (displayMetrics.widthPixels /
                getResources().getInteger(R.integer.backdrop_divisor) *
                getResources().getInteger(R.integer.backdrop_multiplier));
        setTitle("MovieDetail");
        // initialize variables
        // Get View model
        mMovieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        // TODO Relace with binding
        mLoadingIndicatorProgressBar = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTextView = findViewById(R.id.tv_error_message);
        mTitleTextView = findViewById(R.id.title_text_view);
        mMovieImageView = findViewById(R.id.poster_image_view);
        //get the intent in the target activity
        Intent intent = getIntent();
        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();
        // Retrieve the Movie Id from bundle
        mMovieId = extras.getInt(MovieActivity.DETAIL_MOVIE_ID_KEY);

        // Setup observer for Live data
        final Observer<MovieDetailInfo> movieDetailInfoObserver = new Observer<MovieDetailInfo>() {
            @Override
            public void onChanged(@Nullable final MovieDetailInfo newMovieDetailInfo) {
                // Test for data
                if (newMovieDetailInfo == null) {
                    // if no error message use default message
                    showErrorMessage();
                    // test for actual error message
                } else if (newMovieDetailInfo.getErrorMessage() != null &&
                        !newMovieDetailInfo.getErrorMessage().equals("")) {
                    // Display error message
                    mErrorMessageTextView.setText(newMovieDetailInfo.getErrorMessage());
                    // set visability to see error message
                    showErrorMessage();
                } else {
                    // Update the UI.
                    // Retrieve Title and display
                    mTitleTextView.setText(newMovieDetailInfo.getTitle());
                    // Retrieve Backdrop path and display
                    String backDropPath = newMovieDetailInfo.getBackdropPath();
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
                }
            }
        };
        // Initialize observing of live data
        mMovieDetailViewModel.getMovieDetailInfo(mMovieId).observe(this, movieDetailInfoObserver);
    }

    /**
     * Method to show error message and hide other information
     */
    private void showErrorMessage() {
        // TODO adjust for fragments
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mLoadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mTitleTextView.setVisibility(View.INVISIBLE);
        mMovieImageView.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to hide error message and show other information
     */
    private void showDetails() {
        // TODO adjust for fragments
        mErrorMessageTextView.setVisibility(View.GONE);
        mLoadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mTitleTextView.setVisibility(View.VISIBLE);
        mMovieImageView.setVisibility(View.VISIBLE);
    }
}
