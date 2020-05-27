package com.larsonapps.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Class to hold data of each movie
 */
public class MovieResult implements Parcelable {
    // Declare member variables
    private String mPosterPath;
    private int movieID;


    /**
     * No Argument Constructor
     */
    public MovieResult() {
    }

    /**
     * Method to read data from parcel
     * @param in parcel to read from
     */
    private MovieResult(Parcel in) {
        mPosterPath = in.readString();
        movieID = in.readInt();
    }

    /**
     * Method to provide a description code of the parcel
     * @return default
     */
    @Override
    public int describeContents() {return 0;}

    /**
     * Method to data to the parcel
     * @param parcel to write to
     * @param i position of data
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPosterPath);
        parcel.writeInt(movieID);
    }

    /**
     * Creator to initialize parcel
     */
    public final Parcelable.Creator<MovieResult> CREATOR = new Parcelable.Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel parcel) {return new MovieResult(parcel);}

        @Override
        public MovieResult[] newArray(int i) {return new MovieResult[i];}
    };

    /**
     * Method to get the poster Path
     * @return the Poster path or null
     */
    String getPosterPath() {
        return mPosterPath;
    }

    /**
     * Method to set the Poster Path
     * @param mPosterPath of the movie
     */
    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    /**
     * Getter for movie id
     * @return movie id
     */
    public int getMovieID() {
        return movieID;
    }

    /**
     * Setter for movie id
     * @param movieID to set
     */
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

}
