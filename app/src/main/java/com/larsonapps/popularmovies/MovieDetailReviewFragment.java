package com.larsonapps.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.larsonapps.popularmovies.adapter.MovieDetailReviewRecyclerViewAdapter;
import com.larsonapps.popularmovies.data.MovieDetailReview;
import com.larsonapps.popularmovies.data.MovieDetailReviewResult;
import com.larsonapps.popularmovies.databinding.FragmentMovieDetailReviewListBinding;
import com.larsonapps.popularmovies.databinding.FragmentMovieItemListBinding;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;


/**
 * Class to hold the Review list
 */
public class MovieDetailReviewFragment extends Fragment {
    // Declare variavles
    MovieDetailViewModel mMovieDetailViewModel;
    FragmentMovieDetailReviewListBinding binding;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieDetailReviewListBinding.inflate(inflater, container,
                false);
        View view = binding.getRoot();

        // Initialize movie list view model from activity
        mMovieDetailViewModel = new ViewModelProvider(requireActivity())
                .get(MovieDetailViewModel.class);
        hideReview();

        // get the context
        final Context context = view.getContext();

        // Create the observer which updates the UI and sets the adapter
        final Observer<MovieDetailReview> movieDetailReviewObserver =
                new Observer<MovieDetailReview>() {
            @Override
            public void onChanged(@Nullable final MovieDetailReview newMovieDetailReview) {
                // test if data is available
                if (newMovieDetailReview != null &&
                        newMovieDetailReview.getReviewList().size() > 0) {
                    // Update the UI, in this case, an adapter.
                    showRecyclerView();
                    binding.rvMovieDetailReviewList.setLayoutManager(
                            new LinearLayoutManager(context));
                    // TODO fix more reviews menu
//                    // if menu exists set its state
//                    if (mMovieDetailActivity.getMoreReviewsMenuItem() != null) {
//                        if (newMovieDetailReview.getPage() ==
//                                newMovieDetailReview.getTotalPages()) {
//                            mMovieDetailActivity.getMoreReviewsMenuItem().setEnabled(false);
//                        } else {
//                            mMovieDetailActivity.getMoreReviewsMenuItem().setEnabled(true);
//                        }
//                    }
                    // indicate all reviews are the same size
                    binding.rvMovieDetailReviewList.setHasFixedSize(false);
                    // setup Movie adapter for RecyclerView
                    mAdapter = new MovieDetailReviewRecyclerViewAdapter(
                            newMovieDetailReview.getReviewList(), mListener);
                    binding.rvMovieDetailReviewList.setAdapter(mAdapter);
                    //showRecyclerView();
                } else {
                    showNoneMessage();
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
            throw new RuntimeException(context.toString());
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
        binding.rvMovieDetailReviewList.setVisibility(View.INVISIBLE);
        binding.tvMovieDetailReviewNone.setVisibility(View.INVISIBLE);
        binding.reviewDivider.setVisibility(View.INVISIBLE);
        binding.tvReview.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to show all views except none message
     */
    private void showRecyclerView() {
        binding.rvMovieDetailReviewList.setVisibility(View.VISIBLE);
        binding.tvMovieDetailReviewNone.setVisibility(View.GONE);
        binding.reviewDivider.setVisibility(View.VISIBLE);
        binding.tvReview.setVisibility(View.VISIBLE);
    }

    /**
     * Method to show all views except recyclerview
     */
    private void showNoneMessage() {
        binding.rvMovieDetailReviewList.setVisibility(View.GONE);
        binding.tvMovieDetailReviewNone.setVisibility(View.VISIBLE);
        //binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        binding.reviewDivider.setVisibility(View.VISIBLE);
        binding.tvReview.setVisibility(View.VISIBLE);
    }
}
