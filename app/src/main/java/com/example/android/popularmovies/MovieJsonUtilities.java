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
            movies.setStatusMessage(moviesJson.getString("status_message"));
            movies.setStatusCode(moviesJson.getInt("status_code"));
            return movies;
        }

        movies.setPage(moviesJson.getInt("page"));
        movies.setTotalResults(moviesJson.getInt("total_results"));
        movies.setTotalPages(moviesJson.getInt("total_pages"));
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
            currentMovie.setAdult(currentMovieJson.getBoolean("adult"));
            currentMovie.setOverview(currentMovieJson.getString("overview"));
            currentMovie.setReleaseDate(currentMovieJson.getString("release_date"));

            // Get the array of genre ids
            JSONArray genreArrayJson = currentMovieJson.getJSONArray("genre_ids");
            int[] genreArray = new int[genreArrayJson.length()];
            for (int j = 0; j < genreArrayJson.length(); j++) {
                genreArray[j] = genreArrayJson.getInt(j);
            }
            currentMovie.setGenreIds(genreArray);
            currentMovie.setId(currentMovieJson.getInt("id"));
            currentMovie.setOriginalTitle(currentMovieJson.getString("original_title"));
            currentMovie.setOriginalLanguage(currentMovieJson.getString("original_language"));
            currentMovie.setTitle(currentMovieJson.getString("title"));

            // Test for null
            if (!currentMovieJson.isNull("backdrop_path")) {
                currentMovie.setBackdropPath(currentMovieJson.getString("backdrop_path"));
            }
            currentMovie.setPopularity(currentMovieJson.getDouble("popularity"));
            currentMovie.setVoteCount(currentMovieJson.getInt("vote_count"));
            currentMovie.setVideo(currentMovieJson.getBoolean("video"));
            currentMovie.setVoteAverage(currentMovieJson.getDouble("vote_average"));

            // add this movie to the list
            movieResults.add(currentMovie);
        }
        movies.setMovieResults(movieResults);
        // return the list of movie results
        return movies;
    }
}
