package com.larsonapps.popularmovies.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.larsonapps.popularmovies.MovieActivity;
import com.larsonapps.popularmovies.MovieItemFragment.OnListFragmentInteractionListener;
import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Class to display movie list items
 */
public class MovieItemRecyclerViewAdapter extends RecyclerView.Adapter<MovieItemRecyclerViewAdapter.ViewHolder> {
    // Declare member variables
    private int mWidth;
    private int mHeight;
    private ViewModel mViewModel;
    private List<MovieResult> mMovieData;
    private Context context;

    // Variable for listener
    private final OnListFragmentInteractionListener mListener;

    /**
     * Constructor for adapter
     * @param items
     * @param listener
     */
    public MovieItemRecyclerViewAdapter(List<MovieResult> items,
                                        OnListFragmentInteractionListener listener) {
        mMovieData = items;
        mListener = listener;
    }

    /**
     * Method to create individual view holders
     * @param parent of the view holders MovieItemFragment
     * @param viewType to use
     * @return the completed view holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        // get visible width and height of the recyclerview
        mWidth  = parent.getMeasuredWidth();
        mHeight = parent.getMeasuredHeight();

        // Inflate layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Method to bind data to the view holder
     * @param holder to bind the data to
     * @param position of the item in the holder
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        // Declare a variable of the movie results
        final List<MovieResult> movieResults = mMovieData;
        // Test if Poster Path is populated
        if(movieResults.get(position) !=null ){

            String urlString;
            // Set whether or not to use ssl based on API build
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                urlString = NetworkUtilities.POSTER_BASE_HTTPS_URL;
            } else{
                urlString = NetworkUtilities.POSTER_BASE_HTTP_URL;
            }
            // Utilize Picasso to load the poster into the image view
            // resize images based on height, width and orientation of phone
            Picasso.get().load(urlString +
                     MovieActivity.mPosterSize +
                    movieResults.get(position).getPosterPath())
                    .error(R.mipmap.error)
                    .noPlaceholder()
                    .resize(mWidth / MovieActivity.mNumberHorizontalImages,
                            mHeight / MovieActivity.mNumberVerticalImages)
                    .into(holder.mMovieImageView);
        }

        // set up on click listener
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.getAdapterPosition(),
                            movieResults.get(holder.getAdapterPosition()).getMovieID());
                }
            }
        });
    }

    /**
     * Method to get the number of items in the list
     * @return the number of items in the list
     */
    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.size();
    }

    /**
     * Class for the view holders
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        // Declare variables
        final View mView;
        final ImageView mMovieImageView;

        /**
         * Constructor for the view holder class
         * @param view
         */
        ViewHolder(View view) {
            super(view);
            mView = view;
            // TODO Convert to binding
            mMovieImageView = view.findViewById(R.id.iv_list_item);
        }

    }

    /**
     * Method to set the movie data when it changes and notify the RecyclerView when this happens
     * @param movieData to set
     */
    public void setMovieData(List<MovieResult> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
