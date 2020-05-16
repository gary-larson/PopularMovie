package com.larsonapps.popularmovies.utilities;

import com.larsonapps.popularmovies.MainActivity;
import com.larsonapps.popularmovies.MovieResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

final public class MovieJsonUtilities {

    /**
     * Method to convert a Json string to a list of movie results
     * @param movieJsonStr is the string to be converted
     * @return the list of movie results
     * @throws JSONException in case of error
     */
    public static List<MovieResults> getMovieResults (String movieJsonStr) throws JSONException {
        // Declare and initialize variables to return results
        List<MovieResults> movieResults = new ArrayList<>();

        // Check if there are actual results
        JSONObject moviesJson = new JSONObject(movieJsonStr);
        if (!moviesJson.has("page")) {
            MainActivity.setmErrorMessage(moviesJson.getString("status_message"));
            return null;
        }

        MainActivity.setmTotalPages(moviesJson.getInt("total_pages"));
        // extract an array of the results
        JSONArray results = moviesJson.getJSONArray("results");

        // Loop through the results and build the list of movie results
        for (int i = 0; i < results.length(); i++) {
            // get the current movie
            JSONObject currentMovieJson = results.getJSONObject(i);

            // declare and initialize variable to hold the current movie
            MovieResults currentMovie = new MovieResults();

            // retrieve the individual elements
            // test for null
            if (!currentMovieJson.isNull("poster_path")) {
                currentMovie.setPosterPath(currentMovieJson.getString("poster_path"));
            }
            currentMovie.setBackDropPath(currentMovieJson.getString("backdrop_path"));
            currentMovie.setOverview(currentMovieJson.getString("overview"));
            // get release date and convert to a date
            String releaseDate = currentMovieJson.getString("release_date");
            // Save release date as a Date for better formatting capabilities
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.getDefault());
            Date date = null;
            try {
                date = format.parse(releaseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                currentMovie.setReleaseDate(date);
            }
            currentMovie.setOriginalTitle(currentMovieJson.getString("original_title"));
            currentMovie.setTitle(currentMovieJson.getString("title"));
            currentMovie.setVoteAverage(currentMovieJson.getDouble("vote_average"));

            // add this movie to the list
            movieResults.add(currentMovie);
        }
        // return the list of movie results
        return movieResults;
    }
}
