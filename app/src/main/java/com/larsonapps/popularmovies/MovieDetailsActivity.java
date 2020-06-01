package com.larsonapps.popularmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.larsonapps.popularmovies.data.MovieDetailInfo;
import com.larsonapps.popularmovies.data.MovieDetailReview;
import com.larsonapps.popularmovies.data.MovieDetailReviewResult;
import com.larsonapps.popularmovies.data.MovieDetailVideo;
import com.larsonapps.popularmovies.data.MovieDetails;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

/**
 * Activity class for movie details
 */
public class MovieDetailsActivity extends AppCompatActivity implements
        MovieDetailVideoFragment.OnListFragmentInteractionListener,
        MovieDetailReviewFragment.OnListFragmentInteractionListener {
    // declare variables
    MovieDetails mMovieDetailInfo;
    MovieDetailViewModel mMovieDetailViewModel;
    int mWidth;
    int mMovieId;
    Fragment mReviewFragment;
    Fragment mSummaryFragment;
    Fragment mVideoFragment;
    TextView mErrorMessageTextView;
    ProgressBar mLoadingIndicatorProgressBar;
    TextView mTitleTextView;
    ImageView mMovieImageView;
    TextView mOverviewTextView;
    View mReviewDividerView;
    View mTrailerDividerView;
    TextView mTrailerTitleTextView;
    TextView mReviewTitleTextView;
    MenuItem mMoreReviewMenuItem;
    boolean isNextEnabled;

    /**
     * Method to create movie detail activity
     * @param savedInstanceState to hold saved state
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
        mReviewFragment = getSupportFragmentManager().findFragmentById(R.id.detail_review_fragment);
        mSummaryFragment = getSupportFragmentManager().findFragmentById(R.id.detail_summary_fragment);
        mVideoFragment = getSupportFragmentManager().findFragmentById(R.id.detail_video_fragment);
        // TODO Relace with binding
        mLoadingIndicatorProgressBar = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTextView = findViewById(R.id.tv_error_message);
        mTitleTextView = findViewById(R.id.title_text_view);
        mMovieImageView = findViewById(R.id.poster_image_view);
        mOverviewTextView = findViewById(R.id.overview_text_view);
        mTrailerDividerView = findViewById(R.id.divider);
        mTrailerTitleTextView = findViewById(R.id.tv_trailer);
        mReviewDividerView = findViewById(R.id.review_divider);
        mReviewTitleTextView = findViewById(R.id.tv_review);
        //get the intent in the target activity
        Intent intent = getIntent();
        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();
        // Retrieve the Movie Id from bundle
        if (extras != null) {
            mMovieId = extras.getInt(MovieActivity.DETAIL_MOVIE_ID_KEY);
        }
        // display loading indicator until there is data in fields
        showLoadingIndicator();
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
                } else if (newMovieDetailInfo.getmMovieId() == mMovieDetailViewModel.getMovieId()) {
                    // Update the UI.
                    // Retrieve Title and display
                    mTitleTextView.setText(newMovieDetailInfo.getTitle());
                    // Retrieve Backdrop path and display
                    String backDropPath = newMovieDetailInfo.getBackdropPath();
                    if(backDropPath != null){
                        String urlString;
                        // Set whether or not to use ssl based on API build
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                            urlString = MovieNetworkUtilities.POSTER_BASE_HTTPS_URL;
                        } else{
                            urlString = MovieNetworkUtilities.POSTER_BASE_HTTP_URL;
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
                    mOverviewTextView.setText(newMovieDetailInfo.getOverview());
                    showDetails();
                } else {
                    showLoadingIndicator();
                }
            }
        };
        // Initialize observing of live data
        mMovieDetailViewModel.getMovieDetailInfo(mMovieId).observe(this, movieDetailInfoObserver);
    }

    /**
     * Inflate the main menu
     * @param menu to inflate
     * @return true to indicate menu inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        mMoreReviewMenuItem =  menu.getItem(0);
        mMoreReviewMenuItem.setEnabled(true);
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
            case R.id.action_more_reviews:
                // Get Next Page of Reviews
                // TODO fix add to reviews instead of replace reviews use room
                mMovieDetailViewModel.setReviewPage(mMovieDetailViewModel.getReviewPage() + 1);
                if (mMovieDetailViewModel.getReviewPage() == mMovieDetailViewModel.getTotalPages()) {
                    isNextEnabled = false;
                    item.setEnabled(isNextEnabled);
                }
                mMovieDetailViewModel.getMovieDetailReviewNextPage(
                        mMovieDetailViewModel.getReviewPage());
                return true;
            case R.id.action_settings:
                // Show settings activity
                Intent settingsIntent = new Intent(this, MovieSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_about:
                // Show about activity
                Intent aboutIntent = new Intent(this, MovieAboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Getter for movie details more reviews menu item
     * @return movie details more reviews menu item
     */
    public MenuItem getMoreReviewsMenuItem () {
        return mMoreReviewMenuItem;
    }

    /**
     * Method to show error message and hide other information
     */
    private void showErrorMessage() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mLoadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mTitleTextView.setVisibility(View.INVISIBLE);
        mMovieImageView.setVisibility(View.INVISIBLE);
        mOverviewTextView.setVisibility(View.INVISIBLE);
        mTrailerDividerView.setVisibility(View.INVISIBLE);
        mTrailerTitleTextView.setVisibility(View.INVISIBLE);
        mReviewDividerView.setVisibility(View.INVISIBLE);
        mReviewTitleTextView.setVisibility((View.INVISIBLE));
    }

    /**
     * Method to hide error message and show other information
     */
    private void showDetails() {
        mErrorMessageTextView.setVisibility(View.GONE);
        mLoadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mTitleTextView.setVisibility(View.VISIBLE);
        mMovieImageView.setVisibility(View.VISIBLE);
        mOverviewTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Method to show loading indicator and hide other information
     */
    private void showLoadingIndicator() {
        mLoadingIndicatorProgressBar.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mTitleTextView.setVisibility(View.INVISIBLE);
        mMovieImageView.setVisibility(View.INVISIBLE);
        mOverviewTextView.setVisibility(View.INVISIBLE);
        mTrailerDividerView.setVisibility(View.INVISIBLE);
        mTrailerTitleTextView.setVisibility(View.INVISIBLE);
        mReviewDividerView.setVisibility(View.INVISIBLE);
        mReviewTitleTextView.setVisibility((View.INVISIBLE));
    }

    /**
     * Method to process clicks in the review list
     * @param movieDetailReviewResult is the review to process
     */
    @Override
    public void onListFragmentInteraction(MovieDetailReviewResult movieDetailReviewResult) {
        String url = movieDetailReviewResult.getUrl();
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(url));
        startActivity(webIntent);
    }

    /**
     * Method to process clicks on the trailer list
     * @param movieDetailVideo is the trailer to process
     */
    @Override
    public void onListFragmentInteraction(MovieDetailVideo movieDetailVideo) {
        String url;
        if (movieDetailVideo.getSite().equals("YouTube")) {
            url = MovieNetworkUtilities.YOUTUBE_BASE_URL;
        } else {
            url = MovieNetworkUtilities.VIMEO_BASE_URL;
        }
        url += movieDetailVideo.getKey();
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(url));
        startActivity(webIntent);
    }

    /**
     * Getter for movie detail review divider
     * @return movie detail review divider
     */
    public View getReviewDividerView() {
        return mReviewDividerView;
    }

    /**
     * Getter for movie detail trailer divider
     * @return movie detail trailer divider
     */
    public View getTrailerDividerView() {
        return mTrailerDividerView;
    }

    /**
     * Getter for movie detail trailer title
     * @return movie detail trailer title
     */
    public TextView getTrailerTitleTextView() {
        return mTrailerTitleTextView;
    }

    /**
     * Getter for movie detail review title
     * @return movie detail review title
     */
    public TextView getReviewTitleTextView() {
        return mReviewTitleTextView;
    }

    /**
     * Getter for movie detail overview
     * @return movie detail overview
     */
    public TextView getOverviewTextView() {
        return mOverviewTextView;
    }
}
