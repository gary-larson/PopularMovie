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
import com.larsonapps.popularmovies.dummy.DummyContent;
import com.larsonapps.popularmovies.dummy.DummyContent.DummyItem;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MovieDetailReviewFragment extends Fragment {
    // Declare variavles
    private MovieDetailViewModel mMovieDetailViewModel;
    private Activity mMovieDetailActivity;
    RecyclerView mMovieDetailReviewRecyclerView;
    TextView mMovieDetailReviewNoneTextView;
    RecyclerView.Adapter<MovieDetailReviewRecyclerViewAdapter.ViewHolder> mAdapter;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailReviewFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MovieDetailReviewFragment newInstance(int columnCount) {
        MovieDetailReviewFragment fragment = new MovieDetailReviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_movie_detail_review_list, container, false);

        // Initialize movie list view model from activity
        mMovieDetailViewModel = new ViewModelProvider(requireActivity()).get(MovieDetailViewModel.class);
        mMovieDetailActivity = getActivity();
        // TODO replace with binding
        mMovieDetailReviewRecyclerView = view.findViewById(R.id.rv_movie_detail_review_list);
        mMovieDetailReviewNoneTextView = view.findViewById(R.id.tv_movie_detail_review_none);

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
                    mMovieDetailReviewRecyclerView.setVisibility(View.VISIBLE);
                    mMovieDetailReviewNoneTextView.setVisibility(View.GONE);
                    mMovieDetailReviewRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    // if menu exists set its state
//                        if (mMovieActivity.getMoreMovieMenuItem() != null) {
//                            if (mMovieListViewModel.getPage() == newMovieMain.getTotalPages()) {
//                                mMovieActivity.getMoreMovieMenuItem().setEnabled(false);
//                            } else {
//                                mMovieActivity.getMoreMovieMenuItem().setEnabled(true);
//                            }
//                        }
                    // indicate all reviews are the same size
                    mMovieDetailReviewRecyclerView.setHasFixedSize(false);
                    // setup Movie adapter for RecyclerView
                    mAdapter = new MovieDetailReviewRecyclerViewAdapter(
                            newMovieDetailReview.getReviewList(), mListener);
                    mMovieDetailReviewRecyclerView.setAdapter(mAdapter);
                    //showRecyclerView();
                } else {
                    mMovieDetailReviewRecyclerView.setVisibility(View.GONE);
                    mMovieDetailReviewNoneTextView.setVisibility(View.VISIBLE);
                }
            }
            }
        };
        mMovieDetailViewModel.getMovieDetailReview().observe(getViewLifecycleOwner(), movieDetailReviewObserver);

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
        void onListFragmentInteraction(MovieDetailReviewResult movieDetailReviewResult);
    }
}
