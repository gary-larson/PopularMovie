package com.larsonapps.popularmovies.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.larsonapps.popularmovies.MovieDetailVideoFragment.OnListFragmentInteractionListener;
import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.data.MovieDetailVideo;
import com.larsonapps.popularmovies.databinding.FragmentMovieDetailVideoBinding;


import java.util.List;

/**
 * Class to hold the movie detail video list
 *
 */
public class MovieDetailVideoRecyclerViewAdapter extends
        RecyclerView.Adapter<MovieDetailVideoRecyclerViewAdapter.ViewHolder> {

    private final List<MovieDetailVideo> mMovieDetailVideoList;
    private final OnListFragmentInteractionListener mListener;
    private FragmentMovieDetailVideoBinding binding;

    public MovieDetailVideoRecyclerViewAdapter(List<MovieDetailVideo> items,
                                               OnListFragmentInteractionListener listener) {
        mMovieDetailVideoList = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = FragmentMovieDetailVideoBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mMovieDetailVideo = mMovieDetailVideoList.get(position);
        binding.name.setText(mMovieDetailVideoList.get(position).getName());

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
        public MovieDetailVideo mMovieDetailVideo;

        public ViewHolder(View view) {
            super(view);
            mView = view;

        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + binding.name.getText() + "'";
        }
    }
}
