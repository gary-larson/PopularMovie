package com.larsonapps.popularmovies.utilities;

import com.larsonapps.popularmovies.data.MovieDetailInfo;
import com.larsonapps.popularmovies.data.MovieDetailSummary;
import com.larsonapps.popularmovies.data.MovieDetails;
import com.larsonapps.popularmovies.data.MovieMain;
import com.larsonapps.popularmovies.data.MovieResult;
import com.larsonapps.popularmovies.data.MovieDetailReview;
import com.larsonapps.popularmovies.data.MovieDetailVideo;
import com.larsonapps.popularmovies.data.MovieDetailReviewResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class to parse data from JSON objects
 */
final public class MovieJsonUtilities {
    // Declare constants
    private final static String ID = "id";
    private final static String PAGE = "page";
    private final static String STATUS_MESSAGE = "status_message";
    private final static String TOTAL_PAGES = "total_pages";
    private final static String RESULTS = "results";
    private final static String POSTER_PATH = "poster_path";
    private final static String MOVIE_ID = ID;
    private final static String RUNTIME = "runtime";
    private final static String RELEASE_DATE = "release_date";
    private final static String DATE_PATTERN = "yyyy-MM-dd";
    private final static String TITLE = "title";
    private final static String BACKDROP_PATH = "backdrop_path";
    private final static String VOTE_AVERAGE = "vote_average";
    private final static String OVERVIEW = "overview";
    private final static String VIDEOS = "videos";
    private final static String KEY = "key";
    private final static String NAME = "name";
    private final static String SITE = "site";
    private final static String TYPE = "type";
    private final static String AUTHOR = "author";
    private final static String CONTENT = "content";
    private final static String URL = "url";
    private final static String VIDEO_TYPE_TRAILER = "Trailer";


    /**
     * Method to convert a Json string to a list of movie results
     * @param movieJsonStr is the string to be converted
     * @return the list of movie results
     * @throws JSONException in case of error
     */
    public static Result<MovieMain> getMovieResults (String movieJsonStr) throws JSONException {
        // Declare and initialize variables to return results
        MovieMain movieMain = new MovieMain();
        List<MovieResult> movieResults = new ArrayList<>();

        // Check if there are actual results
        JSONObject moviesJson = new JSONObject(movieJsonStr);
        if (!moviesJson.has(PAGE)) {
            return new Result.Error<>(moviesJson.getString(STATUS_MESSAGE));
        }

        movieMain.setTotalPages(moviesJson.getInt(TOTAL_PAGES));
        // extract an array of the results
        JSONArray results = moviesJson.getJSONArray(RESULTS);

        // Loop through the results and build the list of movie results
        for (int i = 0; i < results.length(); i++) {
            // get the current movie
            JSONObject currentMovieJson = results.getJSONObject(i);

            // declare and initialize variable to hold the current movie
            MovieResult currentMovie = new MovieResult();

            // retrieve the individual elements
            // test for null
            if (!currentMovieJson.isNull(POSTER_PATH)) {
                // retrieve poster path
                currentMovie.setPosterPath(currentMovieJson.getString(POSTER_PATH));
            }
            // Retrieve movie id
            currentMovie.setMovieID(currentMovieJson.getInt(MOVIE_ID));

            // add this movie to the list
            movieResults.add(currentMovie);
        }
        movieMain.setMovieList(movieResults);
        // return the movie results
        return new Result.Success<>(movieMain);
    }

    /**
     * Method to parse Movie Details
     * @param movieDetailsJsonStr to parse
     * @return Movie Details
     * @throws JSONException for errors
     */
    public static Result<MovieDetails> getMovieDetails (String movieDetailsJsonStr) throws JSONException {
        // Create JSON object and Check if there are actual results
        JSONObject moviesDetailsJson = new JSONObject(movieDetailsJsonStr);
        // Create object to hold data
        MovieDetails movieDetails = new MovieDetails();
        // Create info
        MovieDetailInfo movieDetailInfo = new MovieDetailInfo();
        // test for null
        if (!moviesDetailsJson.has(RUNTIME)) {
            // Retrieve Error Message
            return new Result.Error<>(moviesDetailsJson.getString(STATUS_MESSAGE));
        }
        // Retrieve Title
        movieDetailInfo.setTitle(moviesDetailsJson.getString(TITLE));
        // Retrieve Backdrop path
        movieDetailInfo.setBackdropPath(moviesDetailsJson.getString(BACKDROP_PATH));
        // Retrieve Overview
        movieDetailInfo.setOverview(moviesDetailsJson.getString(OVERVIEW));
        // add movie detail info to movie details
        movieDetails.setMovieDetailInfo(movieDetailInfo);
        MovieDetailSummary movieDetailSummary = new MovieDetailSummary();
        // Retrieve runtime
        movieDetailSummary.setRuntime(moviesDetailsJson.getInt(RUNTIME));
        // Get release date, format and store it
        String releaseDate = moviesDetailsJson.getString(RELEASE_DATE);
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN,
                Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            movieDetailSummary.setReleaseDate(date);
        }
        // Retrieve Vote Average
        movieDetailSummary.setVoteAverage(moviesDetailsJson.getDouble(VOTE_AVERAGE));
        // Add movie detail summary to movie details
        movieDetails.setMovieDetailSummary(movieDetailSummary);
        // Create List for movie videos
        List<MovieDetailVideo> videos = new ArrayList<>();
        // Create video Json object
        JSONObject movieVideosJson = moviesDetailsJson.getJSONObject(VIDEOS);
        // Create Json Array
        JSONArray results = movieVideosJson.getJSONArray(RESULTS);
        // loop through jsn array
        for (int i = 0; i < results.length(); i++) {
            // get the current video
            JSONObject currentVideoJson = results.getJSONObject(i);

            // declare and initialize variable to hold the current video
            MovieDetailVideo currentVideo = new MovieDetailVideo();
            // extract fields from current json object
            currentVideo.setKey(currentVideoJson.getString(KEY));
            currentVideo.setName(currentVideoJson.getString(NAME));
            currentVideo.setSite(currentVideoJson.getString(SITE));
            // add current video to list
            String type = currentVideoJson.getString(TYPE);
            if (type.equals(VIDEO_TYPE_TRAILER)) {
                videos.add(currentVideo);
            }
        }
        // Add videos to movie details
        movieDetails.setVideoList(videos);
        // Create review json object
        JSONObject movieReviewsJson = moviesDetailsJson.getJSONObject("reviews");
        // Create Movie Review for data
        MovieDetailReview movieDetailReview = new MovieDetailReview();
        // get page from movie reviews json object
        movieDetailReview.setPage(movieReviewsJson.getInt(PAGE));
        // Create Json Array
        results = movieReviewsJson.getJSONArray(RESULTS);

        // Create list for movie reviews
        List<MovieDetailReviewResult> reviews = new ArrayList<>();
        // loop through jsn array
        for (int i = 0; i < results.length(); i++) {
            // Create a review result object
            MovieDetailReviewResult currentReview = new MovieDetailReviewResult();
            // create a current json object
            JSONObject currentReviewJson = results.getJSONObject(i);
            // extract fields from current review json object
            currentReview.setAuthor(currentReviewJson.getString(AUTHOR));
            currentReview.setContent(currentReviewJson.getString(CONTENT));
            currentReview.setUrl(currentReviewJson.getString(URL));
            reviews.add(currentReview);
        }
        movieDetailReview.setReviewList(reviews);
        // get total pages from movie reviews json object
        movieDetailReview.setTotalPages(movieReviewsJson.getInt(TOTAL_PAGES));

        // add movie review info to movie review details
        movieDetails.setMovieDetailInfo(movieDetailInfo);
        // add movie reviews to movie details
        movieDetails.setMovieDetailReview(movieDetailReview);
        return new Result.Success<>(movieDetails);
    }

    /**
     * Method to parse Movie Details
     * @param movieDetailsJsonStr to parse
     * @return Movie Details
     * @throws JSONException for errors
     */
    public static Result<MovieDetailReview> getMovieDetailReview (String movieDetailsJsonStr) throws JSONException {
        // Create JSON object and Check if there are actual results
        JSONObject movieReviewsJson = new JSONObject(movieDetailsJsonStr);
        if (!movieReviewsJson.has(PAGE)) {
            // Retrieve error message
            return new Result.Error<>(movieReviewsJson.getString(STATUS_MESSAGE));
        }
        // Create Movie Review for data
        MovieDetailReview movieDetailReview = new MovieDetailReview();
        // get page from movie reviews json object
        movieDetailReview.setPage(movieReviewsJson.getInt(PAGE));
        // Create Json Array
        JSONArray results = movieReviewsJson.getJSONArray(RESULTS);
        // Create list for movie reviews
        List<MovieDetailReviewResult> reviews = new ArrayList<>();
        // loop through jsn array
        for (int i = 0; i < results.length(); i++) {
            // Create a review result object
            MovieDetailReviewResult currentReview = new MovieDetailReviewResult();
            // create a current json object
            JSONObject currentReviewJson = results.getJSONObject(i);
            // extract fields from current review json object
            currentReview.setAuthor(currentReviewJson.getString(AUTHOR));
            currentReview.setContent(currentReviewJson.getString(CONTENT));
            currentReview.setUrl(currentReviewJson.getString(URL));
            reviews.add(currentReview);
        }
        movieDetailReview.setReviewList(reviews);
        // get total pages from movie reviews json object
        movieDetailReview.setTotalPages(movieReviewsJson.getInt(TOTAL_PAGES));
        return new Result.Success<>(movieDetailReview);
    }
}
