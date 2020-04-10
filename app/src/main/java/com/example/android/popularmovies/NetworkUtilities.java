package com.example.android.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

final class NetworkUtilities {
    // Constants for requests (BASE + type + API_KEY_QUERY + apiKey + [PAGE_QUERY + page #])
    final private static String TMDB_REQUEST_BASE_HTTPS_URL = "https://api.themoviedb.org/3/movie/";
    final private static String TMDB_REQUEST_BASE_HTTP_URL = "http://api.themoviedb.org/3/movie/";
    final static String TMDB_POPULAR_REQUEST_URL = "popular";
    final static String TMDB_HIGHEST_RATED_REQUEST_URL = "top_rated";
    final private static String TMDB_API_KEY_QUERY_URL = "?api_key=";
    final private static String TMDB_PAGE_QUERY_URL = "&page=";

    // Constants for poster requests (BASE + SIZE + movieId)
    final static String TMDB_POSTER_BASE_HTTPS_URL = "https://image.tmdb.org/t/p/";
    final static String TMDB_POSTER_BASE_HTTP_URL = "http://image.tmdb.org/t/p/";
    final static String TMDB_POSTER_SIZE = "w185/";

    /**
     * Builds the URL used to talk to the TMDB server using an api key, type, and page number.
     *
     * @param apiKey assigned by TMDB.
     * @param type of query (popular or highest rated)
     * @param page to retrieve
     * @return The URL to use to query the TMDB server.
     */
    static URL buildUrl(String apiKey, String type, String page) {
        String urlString;
        // Set whether or not to use ssl based on API build
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            urlString = TMDB_REQUEST_BASE_HTTPS_URL +
                    type + TMDB_API_KEY_QUERY_URL + apiKey +
                    TMDB_PAGE_QUERY_URL + page;
        } else{
            urlString = TMDB_REQUEST_BASE_HTTP_URL +
                    type + TMDB_API_KEY_QUERY_URL + apiKey +
                    TMDB_PAGE_QUERY_URL + page;
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
    static String getResponseFromHttpUrl(URL url) throws IOException {
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
}
