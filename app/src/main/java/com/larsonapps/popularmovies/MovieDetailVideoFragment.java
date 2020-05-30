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
import com.larsonapps.popularmovies.dummy.DummyContent;
import com.larsonapps.popularmovies.dummy.DummyContent.DummyItem;
import com.larsonapps.popularmovies.viewmodels.MovieDetailViewModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MovieDetailVideoFragment extends Fragment {
    // Declare variables
    private MovieDetailViewModel mMovieDetailViewModel;
    private Activity mMovieDetailActivity;
    private RecyclerView mMovieDetailVideoRecyclerView;
    private TextView mMovieDetailVideoNoneTextView;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailVideoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MovieDetailVideoFragment newInstance(int columnCount) {
        MovieDetailVideoFragment fragment = new MovieDetailVideoFragment();
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
        View view = inflater.inflate(R.layout.fragment_movie_detail_video_list, container, false);

        // Initialize movie list view model from activity
        mMovieDetailViewModel = new ViewModelProvider(requireActivity()).get(MovieDetailViewModel.class);
        mMovieDetailActivity = getActivity();
        // TODO replace with binding
        mMovieDetailVideoRecyclerView = view.findViewById(R.id.rv_movie_detail_video_list);
        mMovieDetailVideoNoneTextView = view.findViewById(R.id.tv_movie_detail_video_none);


        // get the context
        final Context context = view.getContext();

        // Create the observer which updates the UI and sets the adapter
        final Observer<List<MovieDetailVideo>> movieDetailVideoObserver = new Observer<List<MovieDetailVideo>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetailVideo> newMovieDetailVideoList) {
                // test if recyclerview exists
                if (mMovieDetailVideoRecyclerView != null) {
                    // test if data is available
                    if (newMovieDetailVideoList != null && newMovieDetailVideoList.size() > 0) {
                        // Update the UI, in this case, an adapter.
                        mMovieDetailVideoRecyclerView.setVisibility(View.VISIBLE);
                        mMovieDetailVideoNoneTextView.setVisibility(View.GONE);
                        mMovieDetailVideoRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                        // if menu exists set its state
//                        if (mMovieActivity.getMoreMovieMenuItem() != null) {
//                            if (mMovieListViewModel.getPage() == newMovieMain.getTotalPages()) {
//                                mMovieActivity.getMoreMovieMenuItem().setEnabled(false);
//                            } else {
//                                mMovieActivity.getMoreMovieMenuItem().setEnabled(true);
//                            }
//                        }
                        // indicate all movie detail videos are the same size
                        mMovieDetailVideoRecyclerView.setHasFixedSize(false);
                        // setup Movie adapter for RecyclerView
                        // TODO if newMovieDetailList is empty hide trailer title and divider
                        // TODO look at Me contro json
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
        // TODO: Update argument type and name
        void onListFragmentInteraction(MovieDetailVideo movieDetailVideo);
    }
}
