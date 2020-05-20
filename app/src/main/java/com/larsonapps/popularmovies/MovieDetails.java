package com.larsonapps.popularmovies;

public class MovieDetails {
    private int mRuntime;

    public MovieDetails (int runtime) {
        this.mRuntime = runtime;
    }

    int getRuntime() {
        return mRuntime;
    }

    public void setRuntime(int mRuntime) {
        this.mRuntime = mRuntime;
    }
}
