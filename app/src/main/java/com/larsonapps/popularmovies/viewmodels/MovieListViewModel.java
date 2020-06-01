package com.larsonapps.popularmovies.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.data.MovieListRepository;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;

import java.util.List;

/**
 * Class for movie list view model
 */
public class MovieListViewModel extends AndroidViewModel {
    // Declare Variables
    private Application mApplication;
    private MovieListRepository mMovieListRepository;
    private int mPage;
    private String mType;
    private SharedPreferences mSharedPreferences;
    // Declare LiveData variables
    private LiveData<MovieMain> mMovieMain;
    private LiveData<List<MovieResult>> mMovieResultList;

    /**
     * Constructor for movie list view model
     * @param application to set
     */
    public MovieListViewModel(Application application) {
        super(application);
        this.mApplication = application;
        mMovieListRepository = new MovieListRepository(mApplication);
        mPage = 1;
        mType = MovieNetworkUtilities.POPULAR_REQUEST_URL;
    }

    /**
     * Method to clear the view model and observers
     */
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    /**
     * Getter for movie main info through live data from movie list repository
     * @return movie main info
     */
    public LiveData<MovieMain> getMovieMain() {
        if (mMovieMain == null) {
            mMovieMain = mMovieListRepository.getMovieMain(mType, mPage);
        }
        return mMovieMain;
    }

    /**
     * Method to initialize a change in the type of movies from movie list repository
     */
    public void retrieveMovieMain() {
        mMovieListRepository.getMovieMain(mType, mPage);
    }

    /**
     * Getter for movie main page number
     * @return movie main page number
     */
    public int getPage() {return mPage;}

    /**
     * Setter for movie main page number
     * @param mPage to set
     */
    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    /**
     * Getter for movie type
     * @return movie type
     */
    public String getType () {return mType;}

    /**
     * Setter for movie main type
     * @param mType to set
     */
    public void setType(String mType) {
        this.mType = mType;
    }

    /**
     * Getter for movie main total pages
     * @return movie type
     */
    public int getTotalPages () {
        if (mMovieMain != null && mMovieMain.getValue() != null) {
            return mMovieMain.getValue().getTotalPages();
        } else {
            return 0;
        }
    }

    public int getMovieId (int position) {
        if (mMovieResultList.getValue() != null) {
            return mMovieResultList.getValue().get(position).getMovieID();
        }
        return 0;
    }
}