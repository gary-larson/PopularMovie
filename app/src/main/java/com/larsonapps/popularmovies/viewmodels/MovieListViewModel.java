package com.larsonapps.popularmovies.viewmodels;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.larsonapps.popularmovies.data.MovieListRepository;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;



public class MovieListViewModel extends AndroidViewModel {
    // Declare Variables
    private Application mApplication;
    private MovieListRepository mMovieListRepository;
    private int mPage;
    private String mType;
    // Declare LiveData variables
    private MutableLiveData<MovieMain> mMovieMain;

    public MovieListViewModel(Application application) {
        super(application);
        this.mApplication = application;
        mMovieListRepository = new MovieListRepository(mApplication);
        mPage = 1;
        mType = NetworkUtilities.POPULAR_REQUEST_URL;
        mMovieListRepository.getMovieMain(mType, mPage).observeForever(movieMainObserver);
    }

    @Override
    protected void onCleared() {
        mMovieListRepository.getMovieMain(mType, mPage).removeObserver(movieMainObserver);
        super.onCleared();
    }

    public MutableLiveData<MovieMain> getMovieMain() {
        if (mMovieMain == null) {
            mMovieMain = new MutableLiveData<>();
        }
        return mMovieMain;
    }

    public void retrieveMovieMain() {
        mMovieListRepository.getMovieMain(mType, mPage);
    }

    final private Observer<MovieMain> movieMainObserver = new Observer<MovieMain>() {
        @Override
        public void onChanged(@Nullable final MovieMain newMovieMain) {
            // Update the UI, in this case, an adapter.
            mMovieMain.setValue(newMovieMain);
        }
    };

    public int getmPage() {return mPage;}

    public void setmPage(int mPage) {
        this.mPage = mPage;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public int getTotalPages () {
        if (mMovieMain != null && mMovieMain.getValue() != null) {
            return mMovieMain.getValue().getTotalPages();
        } else {
            return 0;
        }
    }
}