package com.larsonapps.popularmovies.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.larsonapps.popularmovies.MovieDetailReviewFragment.OnListFragmentInteractionListener;
import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.data.MovieDetailReview;
import com.larsonapps.popularmovies.data.MovieDetailReviewResult;
import com.larsonapps.popularmovies.data.MovieResult;


import java.util.List;

/**
 * Class to hold the movie detail review list
 *
 */
public class MovieDetailReviewRecyclerViewAdapter extends
        RecyclerView.Adapter<MovieDetailReviewRecyclerViewAdapter.ViewHolder> {
    // Declare variables
    private final MovieDetailReview mMovieDetailReview = new MovieDetailReview();
    private final List<MovieDetailReviewResult> mReviewResults;
    private final OnListFragmentInteractionListener mListener;
    private MovieDetailReviewResult mResult;
    private ViewModel mViewModel;
    //private List<MovieDetailReviewResult> mReviewData;

    public MovieDetailReviewRecyclerViewAdapter(List<MovieDetailReviewResult> items, OnListFragmentInteractionListener listener) {
        mReviewResults = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_detail_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // Declare a variable of the movie results
        //final List<MovieDetailReviewResult> detailReviewResults = mReviewResults;
        // Test if Poster Path is populated
        if(mReviewResults.get(position) != null ) {
            holder.mReviewResult = mReviewResults.get(position);
            String temp = String.format("By: %s", mReviewResults.get(position).getAuthor());
            holder.mAuthorView.setText(temp);
            holder.mContentView.setText(mReviewResults.get(position).getContent());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mReviewResult);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mReviewResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAuthorView;
        public final TextView mContentView;
        public MovieDetailReviewResult mReviewResult;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAuthorView = view.findViewById(R.id.author);
            mContentView = view.findViewById(R.id.content);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
