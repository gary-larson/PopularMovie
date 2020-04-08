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
    final private static String TMDB_REQUEST_BASE_URL = "http://api.themoviedb.org/3/movie/";
    final static String TMDB_POPULAR_REQUEST_URL = "popular";
    final static String TMDB_HIGHEST_RATED_REQUEST_URL = "top_rated";
    final private static String TMDB_API_KEY_QUERY_URL = "?api_key=";
    final private static String TMDB_PAGE_QUERY_URL = "&page=";

    // Constants for poster requests (BASE + SIZE + movieId)
    final private String TMDB_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    final private String TMDB_POSTER_SIZE = "w185/";

    /**
     * Builds the URL used to talk to the TMDB server using an api key, type, and page number.
     *
     * @param apiKey assigned by TMDB.
     * @param type of query (popular or highest rated)
     * @param page to retrieve
     * @return The URL to use to query the TMDB server.
     */
    static URL buildUrl(String apiKey, String type, String page) {

        String urlString = TMDB_REQUEST_BASE_URL +
                type + TMDB_API_KEY_QUERY_URL + apiKey +
                TMDB_PAGE_QUERY_URL + page;
        Uri builtUri = Uri.parse(urlString).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
