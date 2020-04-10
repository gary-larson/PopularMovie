package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Class to extend RecyclerView.Adapter
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    // Declare member variables
    private MovieInformation mMovieData;


    /**
     * Class that extends RecyclerView. ViewHolder
     */
    static class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        final ImageView mMovieImageView;

        /**
         * Constructor to allow the image view to be initialized
         * @param view of list item view holder
         */
        MovieAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = view.findViewById(R.id.iv_list_item);
        }
    }

    /**
     * Method to inflate the image list item in the view holder
     * @param parent View
     * @param viewType of the parent
     * @return list item view holder
     */
    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.image_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    /**
     * Method to load the image in the view holder
     * @param holder to bind to
     * @param position of the current view holder
     */
    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        // Declare a variable of the movie results
        List<MovieResults> movieResults = mMovieData.getMovieResults();
        // Test if Poster Path is populated
        if(movieResults.get(position).getPosterPath()!=null){
            String urlString;
            // Set whether or not to use ssl based on API build
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                urlString = NetworkUtilities.POSTER_BASE_HTTPS_URL;
            } else{
                urlString = NetworkUtilities.POSTER_BASE_HTTP_URL;
            }
            // Utilize Picasso to load the poster into the image view
            Picasso.get().load(urlString +
                    NetworkUtilities.POSTER_SIZE +
                    movieResults.get(position).getPosterPath())
                    .resize(MainActivity.mWidth / 2,MainActivity.mHeight / 2)
                    .into(holder.mMovieImageView);
        }
    }

    /**
     * Method to get the number of movie results
     * @return the number of movie results
     */
    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.getMovieResults().size();
    }

    /**
     * Method to set the movie data when it changes and notify the RecyclerView when this happens
     * @param movieData to set
     */
    void setMovieData(MovieInformation movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
