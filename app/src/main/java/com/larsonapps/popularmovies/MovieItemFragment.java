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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.larsonapps.popularmovies.adapter.MovieItemRecyclerViewAdapter;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

import java.util.List;

/**
 * Class to deal with a list fragment
 */
public class MovieItemFragment extends Fragment {

    // Declare variables
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private MovieListViewModel mMovieListViewModel;
    private MovieItemRecyclerViewAdapter mAdapter;
    private TextView errorMessageTextView;
    private ProgressBar loadingIndicatorProgressBar;
    private RecyclerView mMovieRecyclerView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_item_list, container, false);
        // Initialize movie list view model from activity
        mMovieListViewModel = new ViewModelProvider(requireActivity()).get(MovieListViewModel.class);
        mMovieActivity = (MovieActivity) getActivity();
        // TODO replace with binding
        errorMessageTextView = view.findViewById(R.id.tv_error_message);
        loadingIndicatorProgressBar = view.findViewById(R.id.pb_loading_indicator);
        mMovieRecyclerView = view.findViewById(R.id.rv_list);
        // set the column based on screen size and orientation
        mColumnCount = getResources().getInteger(R.integer.number_horizontal_posters);
        // get the context
        final Context context = view.getContext();

        // Create the observer which updates the UI and sets the adapter
        final Observer<MovieMain> movieMainObserver = new Observer<MovieMain>() {
            @Override
            public void onChanged(@Nullable final MovieMain newMovieMain) {
                // test if recyclerview exists
                if (mMovieRecyclerView != null) {
                    // test if data is available
                    if (newMovieMain == null) {
                        showErrorMessage();
                        // test if there is an error
                    } else if (newMovieMain.getErrorMessage() != null) {
                        errorMessageTextView.setText(newMovieMain.getErrorMessage());
                        showErrorMessage();
                    } else {
                        // Update the UI, in this case, an adapter.
                        // Setup layout manager
                        if (mColumnCount <= 1) {
                            mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            mMovieRecyclerView.setLayoutManager(new GridLayoutManager(context,
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
                        mMovieRecyclerView.setHasFixedSize(true);
                        // setup Movie adapter for RecyclerView
                        mAdapter = new MovieItemRecyclerViewAdapter(newMovieMain.getMovieList(),
                                mListener);
                        mMovieRecyclerView.setAdapter(mAdapter);
                        showRecyclerView();
                    }
                }
            }
        };
        mMovieListViewModel.getMovieMain().observe(getViewLifecycleOwner(), movieMainObserver);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
        void onListFragmentInteraction(int position, int movieId);
    }

    /**
     * Method to show error message while hiding loading indicator and recyclerview
     */
    private void showErrorMessage() {
        errorMessageTextView.setVisibility(View.VISIBLE);
        loadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mMovieRecyclerView.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to Show recyclerview while hiding error message and loading indicator
     */
    private void showRecyclerView() {
        errorMessageTextView.setVisibility(View.GONE);
        loadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mMovieRecyclerView.setVisibility(View.VISIBLE);
    }
}
