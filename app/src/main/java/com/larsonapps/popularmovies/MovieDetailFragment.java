package com.larsonapps.popularmovies;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
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
import com.larsonapps.popularmovies.utilities.Result;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Class to deal with movie details
 */
public class MovieDetailFragment extends Fragment {
    //Declare variables
    FragmentMovieDetailBinding binding;
    MovieActivity mMovieActivity;
    MovieDetailViewModel mMovieDetailViewModel;
    int mWidth;
    MovieDetailSummaryFragment mSummaryFragment;
    MovieDetailReviewFragment mReviewFragment;
    MovieDetailVideoFragment mVideoFragment;

    /**
     * Default constructor
     */
    public MovieDetailFragment() {}

    /**
     * Method to create fragment
     * @param inflater to create views
     * @param container of the fragment
     * @param savedInstanceState for maintaining state
     * @return view constructed
     */
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
        final Observer<Result<MovieDetailInfo>> movieDetailInfoObserver = newMovieDetailInfo -> {
            // Test for data
            if (newMovieDetailInfo instanceof Result.Error) {
                Result.Error<MovieDetailInfo> resultError =
                        (Result.Error<MovieDetailInfo>) newMovieDetailInfo;
                // Display error message
                binding.tvErrorMessage.setText(resultError.mErrorMessage);
                // set visibility to see error message
                showErrorMessage();
            } else {
                Result.Success<MovieDetailInfo> resultSuccess =
                        (Result.Success<MovieDetailInfo>) newMovieDetailInfo;
                if (resultSuccess != null && resultSuccess.data != null) {
                    if (resultSuccess.data.getMovieId() == mMovieDetailViewModel.getMovieId()) {
                        // Update the UI.
                        movieDetailInfoUpdateUI(resultSuccess.data);
                    } else {
                        showLoadingIndicator();
                    }
                }
            }
        };
        // Initialize observing of live data
        mMovieDetailViewModel.getMovieDetailInfo().observe(getViewLifecycleOwner(), movieDetailInfoObserver);
        return view;
    }

    /**
     * Method to update the user interface
     * @param newMovieDetailInfo data to populate the user interface
     */
    private void movieDetailInfoUpdateUI(@Nullable MovieDetailInfo newMovieDetailInfo) {
        if (newMovieDetailInfo == null) {
            return;
        }
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
            // resize images based on height, width and orientation of phone
            if (newMovieDetailInfo.getImagePath() != null) {
                Picasso.get().load(new File(newMovieDetailInfo.getImagePath()))
                        .error(R.mipmap.error)
                        .noPlaceholder()
                        .error(R.mipmap.error)
                        .resize(mWidth, (mWidth * 9) / 16)
                        .into(binding.posterImageView);
            } else {
                Picasso.get().load(urlString +
                        getResources().getString(R.string.backdrop_size) + backDropPath)
                        .noPlaceholder()
                        .error(R.mipmap.error)
                        .resize(mWidth, (mWidth * 9) / 16)
                        .into(binding.posterImageView);
            }
        }
        binding.overviewTextView.setText(newMovieDetailInfo.getOverview());
        showDetails();
    }

    /**
     * Method to set up fragment
     */
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

    /**
     * Method to reset fragment
     */
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