package com.larsonapps.popularmovies;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.larsonapps.popularmovies.data.MovieDetailInfo;
import com.larsonapps.popularmovies.databinding.FragmentMovieDetailBinding;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

/**
 * Class to deal with movie details
 */
public class MovieDetailFragment extends Fragment {
    //Declare variables
    FragmentMovieDetailBinding binding;
    MovieActivity mMovieActivity;
    MovieDetailViewModel mMovieDetailViewModel;
    int mWidth;
    int mMovieId;
    MovieDetailSummaryFragment mSummaryFragment;
    MovieDetailReviewFragment mReviewFragment;
    MovieDetailVideoFragment mVideoFragment;
    MenuItem mMoreReviewMenuItem;
    boolean isNextEnabled;
    // TODO fix menus

    /**
     * Default constructor
     */
    public MovieDetailFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mMovieActivity = (MovieActivity) getActivity();
        // Get width of screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mMovieActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // adjust screen width depending on screen size and orientation
        mWidth = (displayMetrics.widthPixels /
                getResources().getInteger(R.integer.backdrop_divisor) *
                getResources().getInteger(R.integer.backdrop_multiplier));
        mMovieActivity.setTitle("MovieDetail");
        // initialize variables
        // Get View model
        mMovieDetailViewModel = new ViewModelProvider(requireActivity())
                .get(MovieDetailViewModel.class);
        mMovieId = mMovieActivity.getMovieId();
        mSummaryFragment = new MovieDetailSummaryFragment();
        mVideoFragment = new MovieDetailVideoFragment();
        mReviewFragment = new MovieDetailReviewFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.detail_summary_fragment, mSummaryFragment)
                .replace(R.id.detail_video_fragment, mVideoFragment)
                .replace(R.id.detail_review_fragment, mReviewFragment)
                .commit();
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
                    binding.tvErrorMessage.setText(newMovieDetailInfo.getErrorMessage());
                    // set visability to see error message
                    showErrorMessage();
                } else if (newMovieDetailInfo.getmMovieId() == mMovieDetailViewModel.getMovieId()) {
                    // Update the UI.
                    // Retrieve Title and display
                    binding.titleTextView.setText(newMovieDetailInfo.getTitle());
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
                                .into(binding.posterImageView);
                    }
                    binding.overviewTextView.setText(newMovieDetailInfo.getOverview());
                    showDetails();
                } else {
                    showLoadingIndicator();
                }
            }
        };
        // Initialize observing of live data
        mMovieDetailViewModel.getMovieDetailInfo(mMovieId).observe(getViewLifecycleOwner(), movieDetailInfoObserver);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Get Activity
        MovieActivity activity = (MovieActivity) getActivity();
        if (activity != null) {
            // reset title
            activity.convertTypeToTitleAndSet(activity.mMovieListViewModel.getType());
            // disable up button
            activity.getmActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Get activity
        MovieActivity movieActivity = (MovieActivity) getActivity();
        // set title and enable up button
        if (movieActivity != null) {
            movieActivity.setTitle(getString(R.string.movie_details));
            movieActivity.getmActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Method to show error message and hide other information
     */
    private void showErrorMessage() {
        binding.tvErrorMessage.setVisibility(View.VISIBLE);
        binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        binding.titleTextView.setVisibility(View.INVISIBLE);
        binding.posterImageView.setVisibility(View.INVISIBLE);
        binding.overviewTextView.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to hide error message and show other information
     */
    private void showDetails() {
        binding.tvErrorMessage.setVisibility(View.GONE);
        binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        binding.titleTextView.setVisibility(View.VISIBLE);
        binding.posterImageView.setVisibility(View.VISIBLE);
        binding.overviewTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Method to show loading indicator and hide other information
     */
    private void showLoadingIndicator() {
        binding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        binding.tvErrorMessage.setVisibility(View.INVISIBLE);
        binding.titleTextView.setVisibility(View.INVISIBLE);
        binding.posterImageView.setVisibility(View.INVISIBLE);
        binding.overviewTextView.setVisibility(View.INVISIBLE);
    }
}