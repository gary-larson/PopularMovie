package com.larsonapps.popularmovies.utilities;

import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Class to deal with url's and retrieving data from the internet
 */
final public class MovieNetworkUtilities {
    // Constants for requests (BASE + type + API_KEY_QUERY + apiKey + [PAGE_QUERY + page #])
    final private static String REQUEST_BASE_HTTPS_URL = "https://api.themoviedb.org/3/movie/";
    final private static String REQUEST_BASE_HTTP_URL = "http://api.themoviedb.org/3/movie/";
    final public static String POPULAR_REQUEST_URL = "popular";
    final public static String HIGHEST_RATED_REQUEST_URL = "top_rated";
    final private static String VIDEO_REQUEST_URL = "/videos";
    final private static String REVIEW_REQUEST_URL = "/reviews";
    final private static String API_KEY_QUERY_URL = "?api_key=";
    final private static String PAGE_QUERY_URL = "&page=";
    final private static String LANGUAGE_QUERY_URL = "&language=en-US";
    final private static String APPEND_VIDEOS_QUERY_URL =
            "&append_to_response=videos,images,reviews";
    final public static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    final public static String VIMEO_BASE_URL = "https://vimeo.com/";

    // Constants for API level requests (BASE + SIZE + movieId)
    final public static String POSTER_BASE_HTTPS_URL = "https://image.tmdb.org/t/p/";
    final public static String POSTER_BASE_HTTP_URL = "http://image.tmdb.org/t/p/";


    /**
     * Builds the URL used to talk to the The Movie Database server using an api key, type,
     *  and page number.
     *
     * @param apiKey assigned by The Movie Database.
     * @param type of query (popular or highest rated)
     * @param page to retrieve
     * @return The URL to use to query the The Movie Database server.
     */
    public static URL buildResultsUrl(String apiKey, String type, String page) {
        String urlString;
        apiKey = prepareApiKey(apiKey);
        // Set whether or not to use ssl based on API build
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            urlString = REQUEST_BASE_HTTPS_URL +
                    type + API_KEY_QUERY_URL + apiKey +
                    PAGE_QUERY_URL + page;
        } else{
            urlString = REQUEST_BASE_HTTP_URL +
                    type + API_KEY_QUERY_URL + apiKey +
                    PAGE_QUERY_URL + page;
        }
        // Build Uri
        Uri builtUri = Uri.parse(urlString).buildUpon().build();
        URL url = null;
        // Convert to URL
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // return url
        return url;
    }

    /**
     * Builds the URL used to talk to the The Movie Database server using an api key, and movie id.
     *
     * @param apiKey assigned by The Movie Database.
     * @param movieId to retrieve
     * @return The URL to use to query the The Movie Database server.
     */
    public static URL buildDetailsUrl(String apiKey, int movieId) {
        String urlString;
        apiKey = prepareApiKey(apiKey);
        // Set whether or not to use ssl based on API build
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            urlString = REQUEST_BASE_HTTPS_URL +
                    movieId +
                    API_KEY_QUERY_URL + apiKey +
                    LANGUAGE_QUERY_URL +
                    APPEND_VIDEOS_QUERY_URL;
        } else{
            urlString = REQUEST_BASE_HTTP_URL +
                    movieId +
                    API_KEY_QUERY_URL + apiKey +
                    LANGUAGE_QUERY_URL +
                    APPEND_VIDEOS_QUERY_URL;
        }
        // Build Uri
        Uri builtUri = Uri.parse(urlString).buildUpon().build();
        URL url = null;
        // Convert to URL
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // return url
        return url;
    }

    /**
     * Builds the URL used to talk to the The Movie Database server using an api key, movie id,
     *  and page number.
     *
     * @param apiKey assigned by The Movie Database.
     * @param movieId to retrieve
     * @param page to retrieve
     * @return The URL to use to query the The Movie Database server.
     */
    public static URL buildReviewsUrl(String apiKey, int movieId, int page) {
        String urlString;
        apiKey = prepareApiKey(apiKey);
        // Set whether or not to use ssl based on API build
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            urlString = REQUEST_BASE_HTTPS_URL +
                    movieId +
                    REVIEW_REQUEST_URL +
                    API_KEY_QUERY_URL + apiKey +
                    LANGUAGE_QUERY_URL +
                    PAGE_QUERY_URL + page;
        } else{
            urlString = REQUEST_BASE_HTTP_URL +
                    movieId +
                    REVIEW_REQUEST_URL +
                    API_KEY_QUERY_URL + apiKey +
                    LANGUAGE_QUERY_URL +
                    PAGE_QUERY_URL + page;
        }
        // Build Uri
        Uri builtUri = Uri.parse(urlString).buildUpon().build();
        URL url = null;
        // Convert to URL
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // return url
        return url;
    }

    /**
     * Method to get the response from the built URL
     * @param url to submit
     * @return the response
     * @throws IOException in case of error
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        // Declare variable for the connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            // Create an input stream
            InputStream in = urlConnection.getInputStream();

            // Use scanner to get response
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            // Test for response
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                // get response
                return scanner.next();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // in case of error return null
            return null;
        } finally {
            // disconnect stream
            urlConnection.disconnect();
        }
    }

    /**
     * Method to convert the api key to a usable form
     * @param apiKey to prepare
     * @return prepared api key
     */
    private static String prepareApiKey (String apiKey) {
        if (!(apiKey.equals(""))) {
            try {
                // Decode
                byte[] base64decodedBytes = Base64.decode(apiKey, Base64.DEFAULT);

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    apiKey = new String(base64decodedBytes, StandardCharsets.UTF_8);
                }else {
                    apiKey = new String(base64decodedBytes, "UTF-8");
                }

            } catch (IllegalArgumentException | UnsupportedEncodingException e) {
                if (e.getMessage() == null) {
                    Log.e ("Decode Error :", "Unknown");
                } else {
                    Log.e("Decode Error :", e.getMessage());
                }
            }
        }
        return apiKey;
    }
}
