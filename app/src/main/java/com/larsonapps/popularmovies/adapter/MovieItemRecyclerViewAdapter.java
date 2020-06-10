package com.larsonapps.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.larsonapps.popularmovies.MovieActivity;
import com.larsonapps.popularmovies.MovieItemFragment.OnListFragmentInteractionListener;
import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.databinding.FragmentMovieItemBinding;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Class to display movie list items
 */
public class MovieItemRecyclerViewAdapter extends RecyclerView.Adapter<MovieItemRecyclerViewAdapter.ViewHolder> {
    // Declare member variables
    private int mWidth;
    private int mHeight;
    private FragmentMovieItemBinding binding;
    private List<MovieResult> mMovieData;
    Context context;
    String mType;

    // Variable for listener
    private final OnListFragmentInteractionListener mListener;

    /**
     * Constructor for adapter
     * @param listener to process taps
     * @param type of movie list
     */
    public MovieItemRecyclerViewAdapter(OnListFragmentInteractionListener listener, String type) {
        mListener = listener;
        mType = type;
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
        //mViewModel = new ViewModelProvider(requireActivity())
        //        .get(MovieListViewModel.class);
        // get visible width and height of the recyclerview
        mWidth  = parent.getMeasuredWidth();
        mHeight = parent.getMeasuredHeight();

        // Inflate layout
        binding = FragmentMovieItemBinding.inflate(LayoutInflater.from(context), parent,
                false);
        View view = binding.getRoot();
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
        if(movieResults.get(position) != null ) {
            holder.mMovieResult = mMovieData.get(position);
            String urlString;
            // Set whether or not to use ssl based on API build
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                urlString = MovieNetworkUtilities.POSTER_BASE_HTTPS_URL;
            } else{
                urlString = MovieNetworkUtilities.POSTER_BASE_HTTP_URL;
            }
            // Utilize Picasso to load the poster into the image view
            // resize images based on height, width and orientation of phone
            if (movieResults.get(position).getImagePath() != null) {
                Picasso.get().load(new File(movieResults.get(position).getImagePath()))
                        .error(R.mipmap.error)
                        .noPlaceholder()
                        .resize(mWidth / MovieActivity.mNumberHorizontalImages,
                                mHeight / MovieActivity.mNumberVerticalImages)
                        .into(holder.mImageView);
            } else {
                Picasso.get().load(urlString +
                        MovieActivity.mPosterSize +
                        movieResults.get(position).getPosterPath())
                        .error(R.mipmap.error)
                        .noPlaceholder()
                        .resize(mWidth / MovieActivity.mNumberHorizontalImages,
                                mHeight / MovieActivity.mNumberVerticalImages)
                        .into(holder.mImageView);
            }
        }

        // set up on click listener
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mMovieResult);
            }
        });
    }

    /**
     * Method to set list data and notify adapter
     * @param list to set
     */
    public void setList(List<MovieResult> list) {
        mMovieData = list;
        notifyDataSetChanged();
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
        public MovieResult mMovieResult;
        public ImageView mImageView;

        /**
         * Constructor for the view holder class
         * @param view to use
         */
        ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = binding.ivListItem;
        }
    }
}
