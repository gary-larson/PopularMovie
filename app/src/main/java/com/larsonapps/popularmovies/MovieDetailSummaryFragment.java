package com.larsonapps.popularmovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.larsonapps.popularmovies.data.MovieDetailSummary;
import com.larsonapps.popularmovies.databinding.FragmentDetailSummaryBinding;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Class for the Detail summary fragment
 */
public class MovieDetailSummaryFragment extends Fragment {
    // Declare variables
    MovieDetailViewModel mMovieDetailViewModel;
    MovieListViewModel mMovieListViewModel;
    FragmentDetailSummaryBinding binding;

    /**
     * Default constructor for detail summary fragment
     */
    public MovieDetailSummaryFragment() {}

    /**
     * Method to create the view for detail summary fragment
     * @param inflater to inflate the xml layout
     * @param container that holds the fragment
     * @param savedInstanceState to restore data through life cycles
     * @return the view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailSummaryBinding.inflate(inflater, container, false);
        View view =  binding.getRoot();
        // Declare variables
        mMovieDetailViewModel = new ViewModelProvider(requireActivity())
                .get(MovieDetailViewModel.class);
        mMovieListViewModel = new ViewModelProvider(requireActivity())
                .get(MovieListViewModel.class);
        // set on click listener for favorites
        binding.starImageView.setOnClickListener(v -> {
            // get movie id of this movie
            int movieId = mMovieDetailViewModel.getMovieId();
            // test image path (only favorite has one)
            if (mMovieDetailViewModel.getImagePath() != null) {
                // verify context exists
                if (getContext() != null) {
                    // create alert dialog to verify they want to remove this movie from favorites
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    // set title
                    alertDialog.setTitle("Remove Favorite");
                    // Create message
                    String tempString = String.format(Locale.getDefault(),
                            "Are you sure you want to remove %s from your favorites?",
                            mMovieDetailViewModel.getTitle());
                    // Set message
                    alertDialog.setMessage(tempString);
                    // set ok button and create listener
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            (dialog, which) -> {
                        // remove movie from favorites
                        mMovieDetailViewModel.removeFavorite(movieId,
                                mMovieListViewModel.getImagePath(movieId),
                                mMovieDetailViewModel.getImagePath());
                        // update list to update live data
                        mMovieListViewModel.retrieveMovieMain();
                        // change favorite image to represent no favorite
                        //binding.starImageView.setImageResource(R.drawable.ic_star_outline);
                        // close dialog
                        dialog.dismiss();
                    });
                    // Set cancel button and create listener to close dialog
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "cancel",
                            (dialog, which) -> dialog.dismiss());
                    // show dialog
                    alertDialog.show();
                }
            } else {
                // call view model to add favorite
                mMovieDetailViewModel.addFavorite(movieId,
                        mMovieListViewModel.getPosterPath(movieId),
                        mMovieDetailViewModel.getBackdropPath());
                // change image to represent is a favorite
                //binding.starImageView.setImageResource(R.drawable.ic_star);
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
