package com.larsonapps.popularmovies.adapter;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.larsonapps.popularmovies.MainActivity;
import com.larsonapps.popularmovies.MovieActivity;
import com.larsonapps.popularmovies.MovieItemFragment.OnListFragmentInteractionListener;
import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a image and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * COMPLETED: Replace the implementation with code for your data type.
 */
public class MovieItemRecyclerViewAdapter extends RecyclerView.Adapter<MovieItemRecyclerViewAdapter.ViewHolder> {
    // Declare member variables
    private int mWidth;
    private int mHeight;
    private ViewModel mViewModel;
    private List<String> mMovieData;

    //private final MovieItemRecyclerViewAdapterOnClickHandler mClickHandler;

   // MovieItemRecyclerViewAdapter(MovieItemRecyclerViewAdapterOnClickHandler clickHandler) {
   //     mClickHandler = clickHandler;
   // }

    //private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MovieItemRecyclerViewAdapter(List<String> items, OnListFragmentInteractionListener listener) {
        mMovieData = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        // get visible width and height of the recyclerview
        mWidth  = parent.getMeasuredWidth();
        mHeight = parent.getMeasuredHeight();

        // Inflate layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      /*  holder.mItem = mMovieData.get(position);
        holder.mIdView.setText(mMovieData.get(position).id);
        holder.mContentView.setText(mMovieData.get(position).content); */

        // Declare a variable of the movie results
        List<String> urlStrings = mMovieData;
        // Test if Poster Path is populated
        if(urlStrings.get(position) !=null ){

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
                    urlStrings.get(position))
                    .noPlaceholder()
                    .error(R.mipmap.error)
                    .resize(mWidth / MovieActivity.mNumberHorizontalImages,
                            mHeight / MovieActivity.mNumberVerticalImages)
                    .into(holder.mMovieImageView);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mMovieImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMovieImageView = view.findViewById(R.id.iv_list_item);
        }

    }

    /**
     * Method to set the movie data when it changes and notify the RecyclerView when this happens
     * @param movieData to set
     */
    public void setMovieData(List<String> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    /**
     * Interface for on click listener
     */
   // public interface MovieItemRecyclerViewAdapterOnClickHandler {

  //      void onClick(MovieResult clickedItemResults);
  //  }
}
