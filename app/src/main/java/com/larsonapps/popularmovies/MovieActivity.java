package com.larsonapps.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

public class MovieActivity extends AppCompatActivity implements
        MovieItemFragment.OnListFragmentInteractionListener {
    public static int mNumberHorizontalImages;
    public static int mNumberVerticalImages;
    public static String mPosterSize;
    ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mNumberVerticalImages = getResources().getInteger(R.integer.number_vertical_posters);
        mNumberHorizontalImages = getResources().getInteger(R.integer.number_horizontal_posters);
        mPosterSize = getResources().getString(R.string.poster_size);
        mViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

    }


    @Override
    public void onListFragmentInteraction(int position) {
        Toast.makeText(this, "You have pressed movie in position: " + position,
                Toast.LENGTH_SHORT).show();
    }
}
