package com.larsonapps.popularmovies.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.larsonapps.popularmovies.data.MovieDetailInfo;
import com.larsonapps.popularmovies.data.MovieDetailRepository;
import com.larsonapps.popularmovies.data.MovieDetailReview;
import com.larsonapps.popularmovies.data.MovieDetailSummary;
import com.larsonapps.popularmovies.data.MovieDetailVideo;

import java.util.List;

/**
 * Class to handle the movie detail data
 */
public class MovieDetailViewModel extends AndroidViewModel {
    // Declare variables
    private Application mApplication;
    private MovieDetailRepository mMovieDetailRepository;
    private LiveData<MovieDetailInfo> mMovieDetailInfo;
    private LiveData<MovieDetailSummary> mMovieDetailSummary;
    private LiveData<List<MovieDetailVideo>> mMovieDetailVideo;
    private LiveData<MovieDetailReview> mMovieDetailReview;

    /**
     * Constructor for movie detail view model
     * @param application to pass to movie detail repository
     */
    public MovieDetailViewModel(Application application) {
        super(application);
        this.mApplication = application;
        mMovieDetailRepository = new MovieDetailRepository(mApplication);
    }

    /**
     * Getter foe movie detail information data through live data from movie detail repository
     * @param movieId of the movie to get data for
     * @return movie detail information data
     */
    public LiveData<MovieDetailInfo> getMovieDetailInfo(int movieId) {
        if (mMovieDetailInfo == null) {
            mMovieDetailInfo = mMovieDetailRepository.getMovieDetailInfo(movieId);
        }
        return mMovieDetailInfo;
    }

    /**
     * Getter foe movie detail summary data through live data from movie detail repository
     * @return movie detail summary data
     */
    public LiveData<MovieDetailSummary> getMovieDetailSummary() {
        if (mMovieDetailSummary == null) {
            mMovieDetailSummary = mMovieDetailRepository.getMovieDetailSummary();
        }
        return mMovieDetailSummary;
    }

    /**
     * Getter foe movie detail summary data through live data from movie detail repository
     * @return movie detail summary data
     */
    public LiveData<List<MovieDetailVideo>> getMovieDetailVideo() {
        if (mMovieDetailVideo == null) {
            mMovieDetailVideo = mMovieDetailRepository.getMovieDetailVideoList();
        }
        return mMovieDetailVideo;
    }

    /**
     * Getter foe movie detail summary data through live data from movie detail repository
     * @return movie detail summary data
     */
    public LiveData<MovieDetailReview> getMovieDetailReview() {
        if (mMovieDetailReview == null) {
            mMovieDetailReview = mMovieDetailRepository.getMovieDetailReview();
        }
        return mMovieDetailReview;
    }
}
