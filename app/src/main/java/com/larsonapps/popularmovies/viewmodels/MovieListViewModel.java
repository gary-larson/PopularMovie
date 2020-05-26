package com.larsonapps.popularmovies.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;


import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.data.MovieListRepository;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;



public class MovieListViewModel extends AndroidViewModel {
    // Declare Variables
    private Application mApplication;
    private MovieListRepository mMovieListRepository;
    private int mPage;
    private String mType;
    private SharedPreferences mSharedPreferences;
    // Declare LiveData variables
    private LiveData<MovieMain> mMovieMain;

    public MovieListViewModel(Application application) {
        super(application);
        this.mApplication = application;
        mMovieListRepository = new MovieListRepository(mApplication);
        mPage = 1;
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreference(application.getApplicationContext());
//        mType = mSharedPreferences.getString(
//                application.getString(R.string.setting_movie_list_key),
//                application.getString(R.string.setting_movie_list_popular_value));
        mType = NetworkUtilities.POPULAR_REQUEST_URL;
        //mMovieMain = mMovieListRepository.getMovieMain(mType, mPage);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<MovieMain> getMovieMain() {
        if (mMovieMain == null) {
            //mMovieMain = new MutableLiveData<>();
            mMovieMain = mMovieListRepository.getMovieMain(mType, mPage);
        }
        return mMovieMain;
    }

    public void retrieveMovieMain() {
        if (mType.equals(mApplication.getString(R.string.setting_movie_list_favorite_value))) {
            // TODO process favorite
        }
        mMovieListRepository.getMovieMain(mType, mPage);
    }

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