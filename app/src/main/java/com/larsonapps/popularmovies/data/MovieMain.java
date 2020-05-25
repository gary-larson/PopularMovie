package com.larsonapps.popularmovies.data;

import java.util.ArrayList;
import java.util.List;

public class MovieMain {
    private int mPage;
    private List<MovieResult> mMovieList;
    private int mTotalPages;
    private String mErrorMessage;
    private List<String> mPosterUrls;

    public MovieMain () {
        mPage = 1;
        mErrorMessage = "";
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    List<MovieResult> getMovieList() {
        return mMovieList;
    }

    public void setMovieList(List<MovieResult> mMovieList) {
        this.mMovieList = mMovieList;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int mTotalPages) {
        this.mTotalPages = mTotalPages;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }

    public List<String> getPosterUrls () {
        return mPosterUrls;
    }

    void setPosterUrls (List<String> posterUrls) {
        mPosterUrls = posterUrls;
    }
}
