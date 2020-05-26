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
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MovieItemFragment extends Fragment {

    // COMPLETED: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // COMPLETED: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private MovieListViewModel mMovieListViewModel;
    private MovieItemRecyclerViewAdapter mAdapter;
    private TextView errorMessageTextView;
    private ProgressBar loadingIndicatorProgressBar;
    private RecyclerView mMovieRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieItemFragment() {
    }

    // COMPLETED: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MovieItemFragment newInstance(int columnCount) {
        MovieItemFragment fragment = new MovieItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_item_list, container, false);
        mMovieListViewModel = new ViewModelProvider(requireActivity()).get(MovieListViewModel.class);
        errorMessageTextView = view.findViewById(R.id.tv_error_message);
        loadingIndicatorProgressBar = view.findViewById(R.id.pb_loading_indicator);
        // Set the adapter


        mColumnCount = getResources().getInteger(R.integer.number_horizontal_posters);
        final Context context = view.getContext();
        mMovieRecyclerView = view.findViewById(R.id.rv_list);

        //mMovieRecyclerView.setAdapter(mAdapter);
        // Create the observer which updates the UI.
        final Observer<MovieMain> movieMainObserver = new Observer<MovieMain>() {
            @Override
            public void onChanged(@Nullable final MovieMain newMovieMain) {
                if (mMovieRecyclerView != null) {
                    if (newMovieMain == null) {
                        showErrorMessage();
                    } else if (!newMovieMain.getErrorMessage().equals("")) {
                        errorMessageTextView.setText(newMovieMain.getErrorMessage());
                        showErrorMessage();
                    } else {
                        // Update the UI, in this case, an adapter.
                        // Setup layout manager
                        if (mColumnCount <= 1) {
                            mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            mMovieRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                        }
                        // indicate all poster are the same size
                        mMovieRecyclerView.setHasFixedSize(true);
                        // setup Movie adapter for RecyclerView
                        List<String> posterUrls = newMovieMain.getPosterUrls();
                        mAdapter = new MovieItemRecyclerViewAdapter(posterUrls, mListener);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // COMPLETED: Update argument type and name
        void onListFragmentInteraction(int position);
    }

    private void showErrorMessage() {
        errorMessageTextView.setVisibility(View.VISIBLE);
        loadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mMovieRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showRecyclerView() {
        errorMessageTextView.setVisibility(View.GONE);
        loadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
        mMovieRecyclerView.setVisibility(View.VISIBLE);
    }
}
