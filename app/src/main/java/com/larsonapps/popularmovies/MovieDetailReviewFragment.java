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
import com.larsonapps.popularmovies.adapter.MovieItemRecyclerViewAdapter;
import com.larsonapps.popularmovies.data.MovieDetailReview;
import com.larsonapps.popularmovies.data.MovieDetailReviewResult;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

/**
 * Class to hold the Review list
 */
public class MovieDetailReviewFragment extends Fragment {
    // Declare variavles
    private MovieDetailViewModel mMovieDetailViewModel;
    private MovieDetailsActivity mMovieDetailActivity;
    RecyclerView mMovieDetailReviewRecyclerView;
    TextView mMovieDetailReviewNoneTextView;
    RecyclerView.Adapter<MovieDetailReviewRecyclerViewAdapter.ViewHolder> mAdapter;
    private OnListFragmentInteractionListener mListener;

    /**
     * Default constructor
     */
    public MovieDetailReviewFragment() {
    }

    /**
     * Method to create movie detail review fragment view
     * @param inflater to use to convert xml
     * @param container that holds this fragment
     * @param savedInstanceState for state changes
     * @return the view created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail_review_list, container, false);

        // Initialize movie list view model from activity
        mMovieDetailViewModel = new ViewModelProvider(requireActivity()).get(MovieDetailViewModel.class);
        mMovieDetailActivity = (MovieDetailsActivity) getActivity();
        // TODO replace with binding
        mMovieDetailReviewRecyclerView = view.findViewById(R.id.rv_movie_detail_review_list);
        mMovieDetailReviewNoneTextView = view.findViewById(R.id.tv_movie_detail_review_none);
        hideReview();

        // get the context
        final Context context = view.getContext();

        // Create the observer which updates the UI and sets the adapter
        final Observer<MovieDetailReview> movieDetailReviewObserver = new Observer<MovieDetailReview>() {
            @Override
            public void onChanged(@Nullable final MovieDetailReview newMovieDetailReview) {
            // test if recyclerview exists
            if (mMovieDetailReviewRecyclerView != null) {
                // test if data is available
                if (newMovieDetailReview != null && newMovieDetailReview.getReviewList().size() > 0) {
                    // Update the UI, in this case, an adapter.
                    showRecyclerView();
                    mMovieDetailReviewRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    // if menu exists set its state
                    if (mMovieDetailActivity.getMoreReviewsMenuItem() != null) {
                        if (newMovieDetailReview.getPage() ==
                                newMovieDetailReview.getTotalPages()) {
                            mMovieDetailActivity.getMoreReviewsMenuItem().setEnabled(false);
                        } else {
                            mMovieDetailActivity.getMoreReviewsMenuItem().setEnabled(true);
                        }
                    }
                    // indicate all reviews are the same size
                    mMovieDetailReviewRecyclerView.setHasFixedSize(false);
                    // setup Movie adapter for RecyclerView
                    mAdapter = new MovieDetailReviewRecyclerViewAdapter(
                            newMovieDetailReview.getReviewList(), mListener);
                    mMovieDetailReviewRecyclerView.setAdapter(mAdapter);
                    //showRecyclerView();
                } else {
                    showNoneMessage();
                }
            }
            }
        };
        mMovieDetailViewModel.getMovieDetailReview().observe(getViewLifecycleOwner(), movieDetailReviewObserver);

        return view;
    }

    /**
     * Method to initializes the listener
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
        void onListFragmentInteraction(MovieDetailReviewResult movieDetailReviewResult);
    }

    /**
     * Method to hide all views
     */
    private void hideReview() {
        mMovieDetailReviewRecyclerView.setVisibility(View.INVISIBLE);
        mMovieDetailReviewNoneTextView.setVisibility(View.INVISIBLE);
        if (mMovieDetailActivity.getReviewDividerView() != null) {
            mMovieDetailActivity.getReviewDividerView().setVisibility(View.INVISIBLE);
        }
        mMovieDetailActivity.getReviewTitleTextView().setVisibility(View.INVISIBLE);
    }

    /**
     * Method to show all views except none message
     */
    private void showRecyclerView() {
        mMovieDetailReviewRecyclerView.setVisibility(View.VISIBLE);
        mMovieDetailReviewNoneTextView.setVisibility(View.GONE);
        if (mMovieDetailActivity.getReviewDividerView() != null) {
            mMovieDetailActivity.getReviewDividerView().setVisibility(View.VISIBLE);
        }
        mMovieDetailActivity.getReviewTitleTextView().setVisibility(View.VISIBLE);
    }

    /**
     * Method to show all views except recyclerview
     */
    private void showNoneMessage() {
        mMovieDetailReviewRecyclerView.setVisibility(View.GONE);
        mMovieDetailReviewNoneTextView.setVisibility(View.VISIBLE);
        if (mMovieDetailActivity.getReviewDividerView() != null) {
            mMovieDetailActivity.getReviewDividerView().setVisibility(View.VISIBLE);
        }
        mMovieDetailActivity.getReviewTitleTextView().setVisibility(View.VISIBLE);
    }
}
