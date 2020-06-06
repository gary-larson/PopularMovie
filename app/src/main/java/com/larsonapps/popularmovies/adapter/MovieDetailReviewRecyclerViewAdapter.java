package com.larsonapps.popularmovies.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private List<MovieDetailReviewResult> mReviewResults;
    private final OnListFragmentInteractionListener mListener;
    private MovieDetailReviewResult mResult;
    private ViewModel mViewModel;
    private FragmentMovieDetailReviewBinding binding;

    /**
     * Constructor for the adapter
     * @param listener for the view holders
     */
    public MovieDetailReviewRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    /**
     * Method to create the view holder
     * @param parent of the view holder
     * @param viewType of the view holder
     * @return created view holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = FragmentMovieDetailReviewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    /**
     * Method to bind data to the view holder
     * @param holder to bind to
     * @param position of the list
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // Test if Poster Path is populated
        if(mReviewResults.get(position) != null ) {
            holder.mReviewResult = mReviewResults.get(position);
            String temp = String.format("By: %s", mReviewResults.get(position).getAuthor());
            holder.mAuthor.setText(temp);
            holder.mContent.setText(mReviewResults.get(position).getContent());

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

    /**
     * Method to set List for adapter and notify adapter of the change
     * @param list to set
     */
    public void setList(List<MovieDetailReviewResult> list) {
        mReviewResults = list;
        notifyDataSetChanged();
    }

    /**
     * Method to get list count or 0 if no list
     * @return count
     */
    @Override
    public int getItemCount() {
        if (mReviewResults == null) {
            return 0;
        }
        return mReviewResults.size();
    }

    /**
     * Class to deal with the view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public MovieDetailReviewResult mReviewResult;
        TextView mAuthor;
        TextView mContent;

        /**
         * Constructor for the view holder
         * @param view to use
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAuthor = binding.author;
            mContent = binding.content;
        }

        /**
         * Method to create a string representation of the class
         * @return the string representation of the class
         */
        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + binding.content.getText() + "'";
        }
    }
}
