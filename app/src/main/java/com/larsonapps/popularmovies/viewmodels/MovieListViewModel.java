package com.larsonapps.popularmovies.viewmodels;

import android.app.Application;
import android.os.AsyncTask;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.larsonapps.popularmovies.adapter.MovieItemRecyclerViewAdapter;
import com.larsonapps.popularmovies.data.MovieListRepository;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.NetworkUtilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieListViewModel extends AndroidViewModel {
    // Declare Variables
    private Application mApplication;
    MovieListRepository mMovieListRepository;



    private MutableLiveData<List<String>> mPosterUrls;

    public MovieListViewModel(Application application) {
        super(application);
        this.mApplication = application;
        mMovieListRepository = new MovieListRepository(mApplication);
        mMovieListRepository.getPosterUrls().observeForever(posterUrlsObserver);
    }

    @Override
    protected void onCleared() {
        mMovieListRepository.getPosterUrls().removeObserver(posterUrlsObserver);
        super.onCleared();
    }

//    public List<MovieResult> getMovieResultList () {
//        if (mMovieMain.getMovieList() == null) {
//            getMovies();
//        }
//        return mMovieMain.getMovieList();
//    }

    public MutableLiveData<List<String>> getPosterUrls() {
        if (mPosterUrls == null) {
            mPosterUrls = new MutableLiveData<List<String>>();
        }
        return mPosterUrls;
    }

    final Observer<List<String>> posterUrlsObserver = new Observer<List<String>>() {
        @Override
        public void onChanged(@Nullable final List<String> newPosterUrls) {
            // Update the UI, in this case, an adapter.
            mPosterUrls.setValue(newPosterUrls);
        }
    };

}


 // TODO Put posterurls in room