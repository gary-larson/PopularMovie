package com.example.android.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

final class MovieJsonUtilities {

    /**
     * Method to convert a Json string to a list of movie results
     * @param movieJsonStr is the string to be converted
     * @return the list of movie results
     * @throws JSONException in case of error
     */
    static MovieInformation getMovieResults (String movieJsonStr) throws JSONException {
        // Declare and initialize variables to return results
        MovieInformation movies = new MovieInformation();
        List<MovieResults> movieResults = new ArrayList<>();

        // Check if there are actual results
        JSONObject moviesJson = new JSONObject(movieJsonStr);
        if (!moviesJson.has("page")) {
            movies.setmStatusMessage(moviesJson.getString("status_message"));
            movies.setmStatusCode(moviesJson.getInt("status_code"));
            return movies;
        }

        movies.setmPage(moviesJson.getInt("page"));
        movies.setmTotalResults(moviesJson.getInt("total_results"));
        movies.setmTotalPages(moviesJson.getInt("total_pages"));
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
                currentMovie.setmPosterPath(currentMovieJson.getString("poster_path"));
            }
            currentMovie.setmAdult(currentMovieJson.getBoolean("adult"));
            currentMovie.setmOverview(currentMovieJson.getString("overview"));
            currentMovie.setmReleaseDate(currentMovieJson.getString("release_date"));

            // Get the array of genre ids
            JSONArray genreArrayJson = currentMovieJson.getJSONArray("genre_ids");
            int[] genreArray = new int[genreArrayJson.length()];
            for (int j = 0; j < genreArrayJson.length(); j++) {
                genreArray[j] = genreArrayJson.getInt(j);
            }
            currentMovie.setmGenreIds(genreArray);
            currentMovie.setmId(currentMovieJson.getInt("id"));
            currentMovie.setmOriginalTitle(currentMovieJson.getString("original_title"));
            currentMovie.setmOriginalLanguage(currentMovieJson.getString("original_language"));
            currentMovie.setmTitle(currentMovieJson.getString("title"));

            // Test for null
            if (!currentMovieJson.isNull("backdrop_path")) {
                currentMovie.setmBackdropPath(currentMovieJson.getString("backdrop_path"));
            }
            currentMovie.setmPopularity(currentMovieJson.getDouble("popularity"));
            currentMovie.setmVoteCount(currentMovieJson.getInt("vote_count"));
            currentMovie.setmVideo(currentMovieJson.getBoolean("video"));
            currentMovie.setmVoteAverage(currentMovieJson.getDouble("vote_average"));

            // add this movie to the list
            movieResults.add(currentMovie);
        }
        movies.setmMovieResults(movieResults);
        // return the list of movie results
        return movies;
    }
}
