package com.larsonapps.popularmovies.adapter;

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
import com.larsonapps.popularmovies.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MovieDetailReviewRecyclerViewAdapter extends RecyclerView.Adapter<MovieDetailReviewRecyclerViewAdapter.ViewHolder> {
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_detail_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Declare a variable of the movie results
        final List<MovieDetailReviewResult> detailReviewResults = mReviewResults;
        // Test if Poster Path is populated
        if(detailReviewResults.get(position) != null ) {
            holder.mReviewResult = mReviewResults.get(position);
            holder.mIdView.setText(mReviewResults.get(position).getAuthor());
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
        public final TextView mIdView;
        public final TextView mContentView;
        public MovieDetailReviewResult mReviewResult;
        //public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
