package com.larsonapps.popularmovies;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.larsonapps.popularmovies.adapter.MovieItemRecyclerViewAdapter;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.databinding.FragmentMovieItemListBinding;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

import java.util.List;

/**
 * Class to hold the movie list
 */
public class MovieItemFragment extends Fragment {
    // Declare variables
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private MovieListViewModel mMovieListViewModel;
    private FragmentMovieItemListBinding binding;
    private MovieItemRecyclerViewAdapter mAdapter;
    private MovieActivity mMovieActivity;

    /**
     * Default constructor for movie item fragment
     */
    public MovieItemFragment() {}

    /**
     * Method to create movie item fragment view
     * @param inflater to use to convert xml
     * @param container that holds this fragment
     * @param savedInstanceState for state changes
     * @return the view created
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieItemListBinding.inflate(inflater, container, false);
        View mView = binding.getRoot();
        // Initialize movie list view model from activity
        mMovieListViewModel = new ViewModelProvider(requireActivity())
                .get(MovieListViewModel.class);
        mMovieActivity = (MovieActivity) getActivity();
        // set the column based on screen size and orientation
        mColumnCount = getResources().getInteger(R.integer.number_horizontal_posters);
        // get the context
        final Context context = mView.getContext();

        // Create the observer which updates the UI and sets the adapter
        final Observer<MovieMain> movieMainObserver = new Observer<MovieMain>() {
            @Override
            public void onChanged(@Nullable final MovieMain newMovieMain) {
                // test if data is available
                if (newMovieMain == null) {
                    showErrorMessage();
                    // test if there is an error
                } else if (newMovieMain.getErrorMessage() != null) {
                    binding.tvErrorMessage.setText(newMovieMain.getErrorMessage());
                    showErrorMessage();
                } else {
                    // Update the UI, in this case, an adapter.
                    // set title of activity
                    mMovieActivity.convertTypeToTitleAndSet(
                            mMovieActivity.mMovieListViewModel.getType());
                    // Setup layout manager
                    if (mColumnCount <= 1) {
                        binding.rvList.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        binding.rvList.setLayoutManager(new GridLayoutManager(context,
                                mColumnCount));
                    }
                    // if menu exists set its state
                    if (mMovieActivity.getMoreMovieMenuItem() != null) {
                        if (mMovieListViewModel.getPage() == newMovieMain.getTotalPages()) {
                            mMovieActivity.getMoreMovieMenuItem().setEnabled(false);
                        } else {
                            mMovieActivity.getMoreMovieMenuItem().setEnabled(true);
                        }
                    }
                    // indicate all poster are the same size
                    binding.rvList.setHasFixedSize(true);
                    // setup Movie adapter for RecyclerView
                    mAdapter = new MovieItemRecyclerViewAdapter(newMovieMain.getMovieList(),
                            mListener);
                    binding.rvList.setAdapter(mAdapter);
                    showRecyclerView();
                }
            }
        };
        mMovieListViewModel.getMovieMain().observe(getViewLifecycleOwner(), movieMainObserver);
        return mView;
    }

    /**
     * Method that initializes the listener
     * @param context to use
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() );
        }
    }

    /**
     * Method to remove listener
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
        // set arguments type and name
        void onListFragmentInteraction(MovieResult movieResult);
    }

    /**
     * Method to show error message while hiding loading indicator and recyclerview
     */
    private void showErrorMessage() {
        binding.tvErrorMessage.setVisibility(View.VISIBLE);
        binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        binding.rvList.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to Show recyclerview while hiding error message and loading indicator
     */
    private void showRecyclerView() {
        binding.tvErrorMessage.setVisibility(View.GONE);
        binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        binding.rvList.setVisibility(View.VISIBLE);
    }
}
