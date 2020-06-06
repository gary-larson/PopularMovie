package com.larsonapps.popularmovies.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.larsonapps.popularmovies.data.MovieDetailInfo;
import com.larsonapps.popularmovies.data.MovieDetailRepository;
import com.larsonapps.popularmovies.data.MovieDetailReview;
import com.larsonapps.popularmovies.data.MovieDetailSummary;
import com.larsonapps.popularmovies.data.MovieDetailVideo;
import com.larsonapps.popularmovies.utilities.Result;

import java.util.List;

/**
 * Class to handle the movie detail data
 */
public class MovieDetailViewModel extends AndroidViewModel {
    // Declare variables
    Application mApplication;
    private MovieDetailRepository mMovieDetailRepository;
    private int mMovieID;
    private int mReviewPage;
    private String mType;
    private LiveData<Result<MovieDetailInfo>> mMovieDetailInfo;
    private LiveData<MovieDetailSummary> mMovieDetailSummary;
    private LiveData<List<MovieDetailVideo>> mMovieDetailVideo;
    private LiveData<Result<MovieDetailReview>> mMovieDetailReview;

    /**
     * Constructor for movie detail view model
     * @param application to pass to movie detail repository
     */
    public MovieDetailViewModel(Application application) {
        super(application);
        this.mApplication = application;
        mMovieDetailRepository = new MovieDetailRepository(mApplication);
        mReviewPage = 1;
    }

    /**
     * Getter foe movie detail information data through live data from movie detail repository
     * @return movie detail information data
     */
    public LiveData<Result<MovieDetailInfo>> getMovieDetailInfo() {
        if (mMovieDetailInfo != null) {
            Result.Success<MovieDetailInfo> resultSuccess =
                    (Result.Success<MovieDetailInfo>) mMovieDetailInfo.getValue();
            if (resultSuccess != null) {
                MovieDetailInfo movieDetailinfo = resultSuccess.data;
                if (movieDetailinfo.getmMovieId() != mMovieID) {
                    mMovieDetailInfo = mMovieDetailRepository.getMovieDetailInfo(mMovieID, mType);
                }
            }
            else {
                mMovieDetailInfo = mMovieDetailRepository.getMovieDetailInfo(mMovieID, mType);
            }
        } else {
            mMovieDetailInfo = mMovieDetailRepository.getMovieDetailInfo(mMovieID, mType);
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
    public LiveData<Result<MovieDetailReview>> getMovieDetailReview() {
        if (mMovieDetailReview == null) {
            mMovieDetailReview = mMovieDetailRepository.getMovieDetailReview();
        }
        return mMovieDetailReview;
    }

    /**
     * Method to retrieve the next page of reviews
     */
    public void getMovieDetailReviewNextPage() {
        mMovieDetailRepository.getMovieDetailReviewNextPage(mReviewPage);
    }

    /**
     * Getter for movie id
     * @return movie id
     */
    public int getMovieId () {return mMovieID;}

    /**
     * Setter for movie id
     * @param movieId to set
     */
    public void setMovieId(int movieId) {mMovieID = movieId;}

    /**
     * Getter for movie detail review page
     * @return movie detail review page
     */
    public int getReviewPage() {
        return mReviewPage;
    }

    /**
     * Setter for movie detail review page
     * @param mReviewPage to set
     */
    public void setReviewPage(int mReviewPage) {
        this.mReviewPage = mReviewPage;
    }

    /**
     * Getter for movie main total pages
     * @return movie type
     */
    public int getTotalPages () {
        if (mMovieDetailReview != null && mMovieDetailReview.getValue() != null) {
            Result.Success<MovieDetailReview> resultSuccess =
                    (Result.Success<MovieDetailReview>) mMovieDetailReview.getValue();
            return resultSuccess.data.getTotalPages();
        } else {
            return 0;
        }
    }

    /**
     * Getter for movie list type
     * @return movie list type
     */
    public String getType() {
        return mType;
    }

    /**
     * Setter for movie list type
     * @param mType to set
     */
    public void setType(String mType) {
        this.mType = mType;
    }
}
