package com.larsonapps.popularmovies.utilities;

import com.larsonapps.popularmovies.MainActivity;
import com.larsonapps.popularmovies.data.MovieDetails;
import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.data.MovieReview;
import com.larsonapps.popularmovies.data.MovieVideo;
import com.larsonapps.popularmovies.data.ReviewResult;

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
    public static List<MovieResult> getMovieResults (String movieJsonStr) throws JSONException {
        // Declare and initialize variables to return results
        List<MovieResult> movieResults = new ArrayList<>();

        // Check if there are actual results
        JSONObject moviesJson = new JSONObject(movieJsonStr);
        if (!moviesJson.has("page")) {
            MainActivity.setErrorMessage(moviesJson.getString("status_message"));
            return null;
        }

        MainActivity.setTotalPages(moviesJson.getInt("total_pages"));
        // extract an array of the results
        JSONArray results = moviesJson.getJSONArray("results");

        // Loop through the results and build the list of movie results
        for (int i = 0; i < results.length(); i++) {
            // get the current movie
            JSONObject currentMovieJson = results.getJSONObject(i);

            // declare and initialize variable to hold the current movie
            MovieResult currentMovie = new MovieResult();

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
            currentMovie.setMovieID(currentMovieJson.getInt("id"));
            currentMovie.setOriginalTitle(currentMovieJson.getString("original_title"));
            currentMovie.setTitle(currentMovieJson.getString("title"));
            currentMovie.setVoteAverage(currentMovieJson.getDouble("vote_average"));

            // add this movie to the list
            movieResults.add(currentMovie);
        }
        // return the list of movie results
        return movieResults;
    }

    // COMPLETED create GetMovieDetails for runtime
    public static MovieDetails getMovieDetails (String movieDetailsJsonStr) throws JSONException {
        // Create JSON object and Check if there are actual results
        JSONObject moviesDetailsJson = new JSONObject(movieDetailsJsonStr);
        if (!moviesDetailsJson.has("runtime")) {
            MainActivity.setErrorMessage(moviesDetailsJson.getString("status_message"));
            return null;
        }
        // Get runtime
        int runtime = moviesDetailsJson.getInt("runtime");
        // Create List for movie videos
        List<MovieVideo> videos = new ArrayList<>();
        // Create video Json object
        JSONObject movieVideosJson = moviesDetailsJson.getJSONObject("videos");
        // Create Json Array
        JSONArray results = movieVideosJson.getJSONArray("results");
        // loop through jsn array
        for (int i = 0; i < results.length(); i++) {
            // get the current video
            JSONObject currentVideoJson = results.getJSONObject(i);

            // declare and initialize variable to hold the current video
            MovieVideo currentVideo = new MovieVideo();
            // extract fields from current json object
            currentVideo.setId(currentVideoJson.getString("id"));
            currentVideo.setIso_639_1(currentVideoJson.getString("iso_639_1"));
            currentVideo.setIso_3166_1(currentVideoJson.getString("iso_3166_1"));
            currentVideo.setKey(currentVideoJson.getString("key"));
            currentVideo.setName(currentVideoJson.getString("name"));
            currentVideo.setSite(currentVideoJson.getString("site"));
            currentVideo.setSize(currentVideoJson.getInt("size"));
            currentVideo.setType(currentVideoJson.getString("type"));
            // add current video to list
            videos.add(currentVideo);
        }
        // Create review json object
        JSONObject movieReviewsJson = moviesDetailsJson.getJSONObject("reviews");
        // get page from movie reviews json object
        int page = movieReviewsJson.getInt("page");
        // Create Json Array
        results = movieReviewsJson.getJSONArray("results");

        // Create list for movie reviews
        List<ReviewResult> reviews = new ArrayList<>();
        // loop through jsn array
        for (int i = 0; i < results.length(); i++) {
            // Create a review result object
            ReviewResult currentReview = new ReviewResult();
            // create a current json object
            JSONObject currentReviewJson = results.getJSONObject(i);
            // extract fields from current review json object
            currentReview.setAuthor(currentReviewJson.getString("author"));
            currentReview.setContent(currentReviewJson.getString("content"));
            currentReview.setId(currentReviewJson.getString("id"));
            currentReview.setUrl(currentReviewJson.getString("url"));
            reviews.add(currentReview);
        }
        // get total pages from movie reviews json object
        int totalPages = movieReviewsJson.getInt("total_pages");
        // get total reviews from movie reviews json object
        int totalReviews = movieReviewsJson.getInt("total_results");
        // Create Movie review object and populate
        MovieReview movieReview = new MovieReview (page, reviews, totalPages, totalReviews);
        return new MovieDetails(runtime, videos, movieReview);
    }
}
