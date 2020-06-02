package com.larsonapps.popularmovies;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larsonapps.popularmovies.adapter.MovieDetailVideoRecyclerViewAdapter;
import com.larsonapps.popularmovies.data.MovieDetailVideo;

import com.larsonapps.popularmovies.databinding.FragmentMovieDetailVideoListBinding;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;

import java.util.List;

/**
 * Class to hold the Trailer list
 */
public class MovieDetailVideoFragment extends Fragment {
    // Declare variables
    MovieDetailViewModel mMovieDetailViewModel;
    FragmentMovieDetailVideoListBinding binding;
    private OnListFragmentInteractionListener mListener;

    /**
     * Default constructor
     */
    public MovieDetailVideoFragment() {}

    /**
     * Class that deals with activity creation
     * @param savedInstanceState for state items
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Method to create movie detail video fragment view
     * @param inflater to use to convert xml
     * @param container that holds this fragment
     * @param savedInstanceState for state changes
     * @return the view created
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieDetailVideoListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize movie list view model from activity
        mMovieDetailViewModel = new ViewModelProvider(requireActivity())
                .get(MovieDetailViewModel.class);
        hideVideo();

        // get the context
        final Context context = view.getContext();

        // Create the observer which updates the UI and sets the adapter
        final Observer<List<MovieDetailVideo>> movieDetailVideoObserver =
                new Observer<List<MovieDetailVideo>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetailVideo> newMovieDetailVideoList) {
                // test if data is available
                if (newMovieDetailVideoList != null && newMovieDetailVideoList.size() > 0) {
                    // Update the UI, in this case, an adapter.
                    showRecyclerView();
                    binding.rvMovieDetailVideoList.setLayoutManager(
                            new LinearLayoutManager(context));
                    binding.rvMovieDetailVideoList.setHasFixedSize(false);
                    // setup Movie adapter for RecyclerView
                    RecyclerView.Adapter<MovieDetailVideoRecyclerViewAdapter.ViewHolder>
                            mAdapter = new MovieDetailVideoRecyclerViewAdapter(
                            newMovieDetailVideoList, mListener);
                    binding.rvMovieDetailVideoList.setAdapter(mAdapter);
                    //showRecyclerView();
                } else {
                    showNoneMessage();
                }
            }
        };
        mMovieDetailViewModel.getMovieDetailVideo().observe(getViewLifecycleOwner(),
                movieDetailVideoObserver);
        return view;
    }

    /**
     * Class that initializes the listener
     * @param context to use
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    /**
     * Class that removes the listener
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface for the click listener in the activity containing the fragment
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MovieDetailVideo movieDetailVideo);
    }

    /**
     * Method to hide all views
     */
    private void hideVideo() {
        binding.rvMovieDetailVideoList.setVisibility(View.INVISIBLE);
        binding.tvMovieDetailVideoNone.setVisibility(View.INVISIBLE);
        binding.divider.setVisibility(View.INVISIBLE);
        binding.tvTrailer.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to show all views except none message
     */
    private void showRecyclerView() {
        binding.rvMovieDetailVideoList.setVisibility(View.VISIBLE);
        binding.tvMovieDetailVideoNone.setVisibility(View.GONE);
        binding.divider.setVisibility(View.VISIBLE);
        binding.tvTrailer.setVisibility(View.VISIBLE);
    }

    /**
     * Method to show all views except recyclerview
     */
    private void showNoneMessage() {
        binding.rvMovieDetailVideoList.setVisibility(View.GONE);
        binding.tvMovieDetailVideoNone.setVisibility(View.VISIBLE);
        binding.divider.setVisibility(View.VISIBLE);
        binding.tvTrailer.setVisibility(View.VISIBLE);
    }
}
