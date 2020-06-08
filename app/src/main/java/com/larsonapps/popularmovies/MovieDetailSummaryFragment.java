package com.larsonapps.popularmovies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.larsonapps.popularmovies.data.MovieDetailInfo;
import com.larsonapps.popularmovies.data.MovieDetailSummary;
import com.larsonapps.popularmovies.databinding.FragmentDetailSummaryBinding;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Class for the Detail summary fragment
 */
public class MovieDetailSummaryFragment extends Fragment {
    // Declare variables
    MovieDetailViewModel mMovieDetailViewModel;
    FragmentDetailSummaryBinding binding;

    /**
     * Default constructor for detail summary fragment
     */
    public MovieDetailSummaryFragment() {}

    /**
     * Methot to create the view for detail summary fragment
     * @param inflater to inflate the xml layout
     * @param container that holds the fragment
     * @param savedInstanceState to restore data through lifecycles
     * @return the view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailSummaryBinding.inflate(inflater, container, false);
        View view =  binding.getRoot();
        // Declare variables
        mMovieDetailViewModel = new ViewModelProvider(requireActivity()).
                get(MovieDetailViewModel.class);
        binding.starImageView.setOnClickListener(v -> {
            // TODO add Favorites
            if (mMovieDetailViewModel.isFavorite()) {
                binding.starImageView.setImageResource(R.drawable.ic_star);
                mMovieDetailViewModel.setFavorite(false);
            } else {
                binding.starImageView.setImageResource(R.drawable.ic_star_outline);
                mMovieDetailViewModel.setFavorite(true);
            }
        });
        hideSummary();

        // Create observer for fragment data
        final Observer<MovieDetailSummary> movieDetailSummaryObserver = newMovieDetailSummary -> {
            if (newMovieDetailSummary != null) {
                // Update the UI.
                // Retrieve Release date and display it
                // Release date is stored as a Date type so we can format the Date
                Date releaseDate = newMovieDetailSummary.getReleaseDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy",
                        Locale.getDefault());
                binding.releaseDateTextView.setText(dateFormat.format(releaseDate));
                // Retrieve runtime and display it
                String tempString = String.format(Locale.getDefault(), "%d Minutes",
                        newMovieDetailSummary.getRuntime());
                binding.runtimeTextView.setText(tempString);
                // Retrieve Voter average and display it
                tempString = String.format(Locale.getDefault(), "%.1f/10",
                        newMovieDetailSummary.getVoteAverage());
                binding.voterRatingTextView.setText(tempString);
                if (mMovieDetailViewModel.isFavorite()) {
                    binding.starImageView.setImageResource(R.drawable.ic_star);
                } else {
                    binding.starImageView.setImageResource(R.drawable.ic_star_outline);
                }
                showSummary();
            }
        };
        // setup live data observing
        mMovieDetailViewModel.getMovieDetailSummary().observe(getViewLifecycleOwner(),
                movieDetailSummaryObserver);

        return view;
    }

    /**
     * Method to make all views invisible
     */
    private void hideSummary() {
        binding.releaseDateTextView.setVisibility(View.INVISIBLE);
        binding.runtimeTextView.setVisibility(View.INVISIBLE);
        binding.voterRatingTextView.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to make all views visible
     */
    private void showSummary () {
        binding.releaseDateTextView.setVisibility(View.VISIBLE);
        binding.runtimeTextView.setVisibility(View.VISIBLE);
        binding.voterRatingTextView.setVisibility(View.VISIBLE);
    }
}
