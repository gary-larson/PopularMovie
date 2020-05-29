package com.larsonapps.popularmovies;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.larsonapps.popularmovies.data.MovieDetailSummary;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Class for the Detail summary fragment
 */
public class MovieDetailSummaryFragment extends Fragment {
    // Declare variables
    private MovieDetailViewModel mMovieDetailViewModel;
    private TextView mReleaseDateTextView;
    private TextView mRuntimeTextView;
    private TextView mVoteAverageTextView;
    private TextView mOverviewTextView;

    /**
     * Defaul constructor for detail summary fragment
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_summary, container, false);
        // Declare variables
        // TODO convert to binding
        mMovieDetailViewModel = new ViewModelProvider(requireActivity()).get(MovieDetailViewModel.class);
        mReleaseDateTextView = view.findViewById(R.id.release_date_text_view);
        mRuntimeTextView = view.findViewById(R.id.runtime_text_view);
        mVoteAverageTextView = view.findViewById(R.id.voter_rating_text_view);
        mOverviewTextView = view.findViewById(R.id.overview_text_view);

        /**
         * Create observer for fragment data
         */
        final Observer<MovieDetailSummary> movieDetailSummaryObserver = new Observer<MovieDetailSummary>() {
            @Override
            public void onChanged(@Nullable final MovieDetailSummary newMovieDetailSummary) {
                if (newMovieDetailSummary != null) {
                    // Update the UI.
                    // Retrieve Release date and display it
                    // Release date is stored as a Date type so we can format the Date
                    Date releaseDate = newMovieDetailSummary.getReleaseDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy",
                            Locale.getDefault());
                    mReleaseDateTextView.setText(dateFormat.format(releaseDate));
                    // Retrieve runtime and display it
                    String tempString = String.format(Locale.getDefault(), "%d Minutes",
                            newMovieDetailSummary.getRuntime());
                    mRuntimeTextView.setText(tempString);
                    // Retrieve Voter average and display it
                    tempString = String.format(Locale.getDefault(), "%.1f/10",
                            newMovieDetailSummary.getVoteAverage());
                    mVoteAverageTextView.setText(tempString);
                    // Retrieve overview and display it
                    mOverviewTextView.setText(newMovieDetailSummary.getOverview());
                }
            }
        };
        // setup live data observing
        mMovieDetailViewModel.getMovieDetailSummary().observe(getViewLifecycleOwner(),
                movieDetailSummaryObserver);

        return view;
    }
}
