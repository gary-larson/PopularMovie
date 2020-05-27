package com.larsonapps.popularmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.larsonapps.popularmovies.adapter.MovieItemRecyclerViewAdapter;
import com.larsonapps.popularmovies.data.MovieDetails;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieDetailsActivity extends AppCompatActivity {
    // declare variables
    //private String temp = "";
    MovieDetails mMovieDetails;
    private MovieDetailViewModel mMovieDetailViewModel;
    int mWidth;
    int mMovieId;
    TextView mErrorMessageTextView;
    ProgressBar mLoadingIndicatorProgressBar;
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
        mMovieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        mLoadingIndicatorProgressBar = findViewById(R.id.pb_loading_indicator);
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
        // Retrieve the Movie Id from bundle
        mMovieId = extras.getInt(MovieActivity.DETAIL_MOVIE_ID_KEY);

        final Observer<MovieDetails> movieDetailsObserver = new Observer<MovieDetails>() {
            @Override
            public void onChanged(@Nullable final MovieDetails newMovieDetails) {
                if (newMovieDetails == null) {
                    showErrorMessage();
                } else if (newMovieDetails.getErrorMessage() != null &&
                        !newMovieDetails.getErrorMessage().equals("")) {
                    mErrorMessageTextView.setText(newMovieDetails.getErrorMessage());
                    showErrorMessage();
                } else {
                    // Update the UI.
                    // Retrieve Title and display
                    mTitleTextView.setText(newMovieDetails.getTitle());
                    // Retrieve Backdrop path and display
                    String backDropPath = newMovieDetails.getBackdropPath();
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
                    Date releaseDate = newMovieDetails.getReleaseDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy",
                            Locale.getDefault());
                    mReleaseDateTextView.setText(dateFormat.format(releaseDate));
                    // Retrieve runtime and display it
                    String tempString = String.format(Locale.getDefault(), "%d Minutes",
                            newMovieDetails.getRuntime());
                    mRuntimeTextView.setText(tempString);
                    // Retrieve Voter average and display it
                    tempString = String.format(Locale.getDefault(), "%.1f/10",
                            newMovieDetails.getVoteAverage());
                    mVoteAverageTextView.setText(tempString);
                    // Retrieve overview and display it
                    mOverviewTextView.setText(newMovieDetails.getOverview());
                }
            }
        };
        mMovieDetailViewModel.getMovieDetails(mMovieId).observe(this, movieDetailsObserver);
    }

    private void showErrorMessage() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mLoadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mTitleTextView.setVisibility(View.INVISIBLE);
        mMovieImageView.setVisibility(View.INVISIBLE);
        mReleaseDateTextView.setVisibility(View.INVISIBLE);
        mRuntimeTextView.setVisibility(View.INVISIBLE);
        mVoteAverageTextView.setVisibility(View.INVISIBLE);
        mOverviewTextView.setVisibility(View.INVISIBLE);
    }

    private void showDetails() {
        mErrorMessageTextView.setVisibility(View.GONE);
        mLoadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mTitleTextView.setVisibility(View.VISIBLE);
        mMovieImageView.setVisibility(View.VISIBLE);
        mReleaseDateTextView.setVisibility(View.VISIBLE);
        mRuntimeTextView.setVisibility(View.VISIBLE);
        mVoteAverageTextView.setVisibility(View.VISIBLE);
        mOverviewTextView.setVisibility(View.VISIBLE);
    }
}
