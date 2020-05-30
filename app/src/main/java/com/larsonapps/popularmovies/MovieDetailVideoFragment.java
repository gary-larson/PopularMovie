package com.larsonapps.popularmovies;

import android.app.Activity;
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
import android.widget.TextView;

import com.larsonapps.popularmovies.adapter.MovieDetailReviewRecyclerViewAdapter;
import com.larsonapps.popularmovies.adapter.MovieDetailVideoRecyclerViewAdapter;
import com.larsonapps.popularmovies.data.MovieDetailReview;
import com.larsonapps.popularmovies.data.MovieDetailVideo;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;

import java.util.List;

/**
 * Class to hold the Trailer list
 */
public class MovieDetailVideoFragment extends Fragment {
    // Declare variables
    MovieDetailViewModel mMovieDetailViewModel;
    private RecyclerView mMovieDetailVideoRecyclerView;
    private TextView mMovieDetailVideoNoneTextView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail_video_list, container,
                false);

        // Initialize movie list view model from activity
        mMovieDetailViewModel = new ViewModelProvider(requireActivity())
                .get(MovieDetailViewModel.class);
        // TODO replace with binding
        mMovieDetailVideoRecyclerView = view.findViewById(R.id.rv_movie_detail_video_list);
        mMovieDetailVideoNoneTextView = view.findViewById(R.id.tv_movie_detail_video_none);


        // get the context
        final Context context = view.getContext();

        // Create the observer which updates the UI and sets the adapter
        final Observer<List<MovieDetailVideo>> movieDetailVideoObserver =
                new Observer<List<MovieDetailVideo>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetailVideo> newMovieDetailVideoList) {
                // test if recyclerview exists
                if (mMovieDetailVideoRecyclerView != null) {
                    // test if data is available
                    if (newMovieDetailVideoList != null && newMovieDetailVideoList.size() > 0) {
                        // Update the UI, in this case, an adapter.
                        mMovieDetailVideoRecyclerView.setVisibility(View.VISIBLE);
                        mMovieDetailVideoNoneTextView.setVisibility(View.GONE);
                        mMovieDetailVideoRecyclerView.setLayoutManager(
                                new LinearLayoutManager(context));
                        mMovieDetailVideoRecyclerView.setHasFixedSize(false);
                        // setup Movie adapter for RecyclerView
                        RecyclerView.Adapter<MovieDetailVideoRecyclerViewAdapter.ViewHolder>
                                mAdapter = new MovieDetailVideoRecyclerViewAdapter(
                                newMovieDetailVideoList, mListener);
                        mMovieDetailVideoRecyclerView.setAdapter(mAdapter);
                        //showRecyclerView();
                    } else {
                        mMovieDetailVideoRecyclerView.setVisibility(View.GONE);
                        mMovieDetailVideoNoneTextView.setVisibility(View.VISIBLE);
                    }
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
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
}
