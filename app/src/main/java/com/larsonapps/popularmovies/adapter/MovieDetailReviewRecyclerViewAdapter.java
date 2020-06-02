package com.larsonapps.popularmovies.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larsonapps.popularmovies.MovieDetailReviewFragment.OnListFragmentInteractionListener;
import com.larsonapps.popularmovies.data.MovieDetailReview;
import com.larsonapps.popularmovies.data.MovieDetailReviewResult;
import com.larsonapps.popularmovies.databinding.FragmentMovieDetailReviewBinding;


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
    private FragmentMovieDetailReviewBinding binding;

    public MovieDetailReviewRecyclerViewAdapter(List<MovieDetailReviewResult> items, OnListFragmentInteractionListener listener) {
        mReviewResults = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = FragmentMovieDetailReviewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // Test if Poster Path is populated
        if(mReviewResults.get(position) != null ) {
            holder.mReviewResult = mReviewResults.get(position);
            String temp = String.format("By: %s", mReviewResults.get(position).getAuthor());
            binding.author.setText(temp);
            binding.content.setText(mReviewResults.get(position).getContent());

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
        public MovieDetailReviewResult mReviewResult;


        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + binding.content.getText() + "'";
        }
    }
}
