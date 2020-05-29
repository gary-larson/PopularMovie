package com.larsonapps.popularmovies.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.larsonapps.popularmovies.MovieDetailVideoFragment.OnListFragmentInteractionListener;
import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.data.MovieDetailVideo;
import com.larsonapps.popularmovies.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MovieDetailVideoRecyclerViewAdapter extends RecyclerView.Adapter<MovieDetailVideoRecyclerViewAdapter.ViewHolder> {

    private final List<MovieDetailVideo> mMovieDetailVideoList;
    private final OnListFragmentInteractionListener mListener;

    public MovieDetailVideoRecyclerViewAdapter(List<MovieDetailVideo> items, OnListFragmentInteractionListener listener) {
        mMovieDetailVideoList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_detail_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mMovieDetailVideo = mMovieDetailVideoList.get(position);
        holder.mIdView.setText(mMovieDetailVideoList.get(position).getType());
        holder.mContentView.setText(mMovieDetailVideoList.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mMovieDetailVideo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieDetailVideoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public MovieDetailVideo mMovieDetailVideo;

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
