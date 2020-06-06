package com.larsonapps.popularmovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * Class to handle about fragment
 */
public class MovieAboutFragment extends Fragment {

    /**
     *
     * @param inflater to convert xml
     * @param container to hold fragment
     * @param savedInstanceState to maintain state
     * @return view created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_about, container, false);
        // get activity
        MovieActivity movieActivity = (MovieActivity) getActivity();
        // set title and enable up button
        if (movieActivity != null) {
            movieActivity.setTitle(getString(R.string.about));
            movieActivity.getmActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return view;
    }

    /**
     * Method reset fragment settings
     */
    @Override
    public void onPause() {
        super.onPause();
        // Get Activity
        MovieActivity activity = (MovieActivity) getActivity();
        if (activity != null) {
            // reset title
            activity.convertTypeToTitleAndSet(activity.mMovieListViewModel.getType());
            // disable up button
            activity.getmActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     * Method to set up fragment for display
     */
    @Override
    public void onResume() {
        super.onResume();
        // Get activity
        MovieActivity movieActivity = (MovieActivity) getActivity();
        // set title and enable up button
        if (movieActivity != null) {
            movieActivity.setTitle(getString(R.string.about));
            movieActivity.getmActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}