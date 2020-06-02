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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.larsonapps.popularmovies.adapter.MovieItemRecyclerViewAdapter;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.databinding.FragmentMovieItemListBinding;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

/**
 * Class to hold the movie list
 */
public class MovieItemFragment extends Fragment {
    // Declare constants
    private final int MORE_MOVIES_MENU_ITEM_ID = 111;
    private final String MORE_MOVIES_IS_ENABLED_KEY = "more_movies_is_enabled";
    // Declare variables
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private MovieListViewModel mMovieListViewModel;
    private FragmentMovieItemListBinding binding;
    private MovieItemRecyclerViewAdapter mAdapter;
    private MovieActivity mMovieActivity;
    private MenuItem mMoreMoviesMenuItem;
    private boolean isMoreMoviesEnabled;

    /**
     * Default constructor for movie item fragment
     */
    public MovieItemFragment() {}

    /**
     * Method to enable onPrepareOptionsMenu
     * @param savedInstanceState to save state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            isMoreMoviesEnabled = false;
        } else {
            isMoreMoviesEnabled = savedInstanceState.getBoolean(MORE_MOVIES_IS_ENABLED_KEY,
                    false);
        }
        setHasOptionsMenu(true);
    }

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
                    if (mMoreMoviesMenuItem != null) {
                        isMoreMoviesEnabled = mMovieListViewModel.getPage() != newMovieMain.getTotalPages();
                        mMoreMoviesMenuItem.setEnabled(isMoreMoviesEnabled);
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
     * Method to add more movies menu item
     * @param menu to modify
     */
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        if (menu.findItem(MORE_MOVIES_MENU_ITEM_ID) == null) {
            mMoreMoviesMenuItem = menu.add(Menu.NONE, MORE_MOVIES_MENU_ITEM_ID, 1,
                    getString(R.string.more_movies));
            mMoreMoviesMenuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        mMoreMoviesMenuItem.setEnabled(isMoreMoviesEnabled);
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
        if (itemId == MORE_MOVIES_MENU_ITEM_ID) {
            // Get Next Page of Movies
            // TODO fix add to list instead of replace list use room
            // Set page number in movie list view model
            mMovieListViewModel.setPage(mMovieListViewModel.getPage() + 1);
            // If on last page disable more movies menu item
            if (mMovieListViewModel.getPage() == mMovieListViewModel.getTotalPages()) {
                isMoreMoviesEnabled = false;
                item.setEnabled(isMoreMoviesEnabled);
            }
            // retrieve more movies from view model
            mMovieListViewModel.retrieveMovieMain();
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
        outState.putBoolean(MORE_MOVIES_IS_ENABLED_KEY, isMoreMoviesEnabled);
        super.onSaveInstanceState(outState);
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
