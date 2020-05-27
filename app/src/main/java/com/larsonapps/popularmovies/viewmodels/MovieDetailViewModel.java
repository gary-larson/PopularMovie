package com.larsonapps.popularmovies.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.larsonapps.popularmovies.data.MovieDetailRepository;
import com.larsonapps.popularmovies.data.MovieDetails;
import com.larsonapps.popularmovies.data.MovieListRepository;


public class MovieDetailViewModel extends AndroidViewModel {
    // Declare variables
    private Application mApplication;
    private MovieDetailRepository mMovieDetailRepository;
    private LiveData<MovieDetails> mMovieDetails;

    public MovieDetailViewModel(Application application) {
        super(application);
        this.mApplication = application;
        mMovieDetailRepository = new MovieDetailRepository(mApplication);
    }

    public LiveData<MovieDetails> getMovieDetails(int movieId) {
        if (mMovieDetails == null) {
            //mMovieMain = new MutableLiveData<>();
            mMovieDetails = mMovieDetailRepository.getMovieDetails(movieId);
        }
        return mMovieDetails;
    }
}
