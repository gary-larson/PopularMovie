package com.larsonapps.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
    // Declare constants
    private final String MORE_REVIEWS_IS_ENABLED_KEY = "more_movies_is_enabled_key";
    private final int MORE_REVIEWS_MENU_ITEM_ID = 222;
    // Declare variavles
    MovieDetailViewModel mMovieDetailViewModel;
    FragmentMovieDetailReviewListBinding binding;
    RecyclerView.Adapter<MovieDetailReviewRecyclerViewAdapter.ViewHolder> mAdapter;
    private OnListFragmentInteractionListener mListener;
    private boolean isMoreReviewsEnabled;
    private MenuItem mMoreReviewsMenuItem;

    /**
     * Default constructor
     */
    public MovieDetailReviewFragment() {
    }

    /**
     * Method to enable onPrepareOptionsMenu
     * @param savedInstanceState to save state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            isMoreReviewsEnabled = false;
        } else {
            isMoreReviewsEnabled = savedInstanceState.getBoolean(MORE_REVIEWS_IS_ENABLED_KEY,
                    false);
        }
        setHasOptionsMenu(true);
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
                    // if menu exists set its state
                    if (mMoreReviewsMenuItem != null) {
                        isMoreReviewsEnabled = newMovieDetailReview.getPage() != newMovieDetailReview.getTotalPages();
                        mMoreReviewsMenuItem.setEnabled(isMoreReviewsEnabled);
                    }
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
     * Method to add more movies menu item
     * @param menu to modify
     */
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        if (menu.findItem(MORE_REVIEWS_MENU_ITEM_ID) == null) {
            mMoreReviewsMenuItem = menu.add(Menu.NONE, MORE_REVIEWS_MENU_ITEM_ID, 1,
                    getString(R.string.more_reviews));
            mMoreReviewsMenuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        mMoreReviewsMenuItem.setEnabled(isMoreReviewsEnabled);
        super.onPrepareOptionsMenu(menu);
    }

    /**
     * Method to respond to options menu clicks
     * @param item to be processed
     * @return true if handled
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == MORE_REVIEWS_MENU_ITEM_ID) {
            // Get Next Page of Movies
            // TODO fix add to list instead of replace list use room
            // Set page number in movie list view model
            mMovieDetailViewModel.setReviewPage(mMovieDetailViewModel.getReviewPage() + 1);
            // If on last page disable more movies menu item
            if (mMovieDetailViewModel.getReviewPage() == mMovieDetailViewModel.getTotalPages()) {
                isMoreReviewsEnabled = false;
                item.setEnabled(isMoreReviewsEnabled);
            }
            // retrieve more movies from view model
            mMovieDetailViewModel.getMovieDetailReviewNextPage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to save instance state
     * @param outState bundle to save
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(MORE_REVIEWS_IS_ENABLED_KEY, isMoreReviewsEnabled);
        super.onSaveInstanceState(outState);
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
