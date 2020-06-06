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
    // Declare variables
    private List<MovieDetailVideo> mMovieDetailVideoList;
    private final OnListFragmentInteractionListener mListener;
    private FragmentMovieDetailVideoBinding binding;

    /**
     * Constructor for the adapter
     * @param listener for the view holders
     */
    public MovieDetailVideoRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    /**
     * Method to create the view holder
     * @param parent of the view holder
     * @param viewType of the view holder
     * @return vieww holder that is created
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = FragmentMovieDetailVideoBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    /**
     * Method to bind data to current view holder
     * @param holder to bind to
     * @param position of the list
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mMovieDetailVideo = mMovieDetailVideoList.get(position);
        holder.mName.setText(mMovieDetailVideoList.get(position).getName());

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

    /**
     * Method to set List for adapter and notify adapter of the change
     * @param list to set
     */
    public void setList(List<MovieDetailVideo> list) {
        mMovieDetailVideoList = list;
        notifyDataSetChanged();
    }

    /**
     * Method to get list count or 0 if no items
     * @return size of the list
     */
    @Override
    public int getItemCount() {
        if (mMovieDetailVideoList == null) {
            return 0;
        }
        return mMovieDetailVideoList.size();
    }

    /**
     * Class to deal with the view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public MovieDetailVideo mMovieDetailVideo;
        public TextView mName;

        /**
         * Constructor for the view holder
         * @param view to use
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = binding.name;
        }

        /**
         * Default to string class
         * @return string representing class
         */
        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + binding.name.getText() + "'";
        }
    }
}
