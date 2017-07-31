package com.example.android.udacitymovieapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by rafa2093 on 4/2/2017.
 */

public class MovieNetworkUtils {
    public static final String APIKEY = "";

    public static final String TAG = "MovieNetworkUtils";

    //Types
    public static final int MOST_POPULAR = 1;
    public static final int HIGHEST_RATED = 2;
    public static final int ASCENDING = 3;
    public static final int DESCENDING = 4;
    public static final int FAVORITES = 5;

    public static final String MOST_POPULAR_STRING = "popularity";
    public static final String HIGHEST_RATED_STRING = "vote_average";
    public static final String ASCENDING_STRING = "asc";
    public static final String DESCENDING_STRING = "desc";



    //URLS
    public static final String BASE_URL = "https://api.themoviedb.org/3";


    public static final String DISCOVER = "discover";

    public static final String LANGUAGE_QUERY = "language";
    public static final String LANGUAGE = "en-US";

    public static final String MOVIE = "movie";
    public static final String VIDEOS = "videos";
    public static final String REVIEWS = "reviews";
    public static final String API_KEY_QUERY = "api_key";

    public static final String INCLUDE_ADULT_QUERY = "include_adult";
    public static final String INCLUDE_VIDEO_QUERY = "include_video";


    public static final String PAGE_QUERY = "page";

    public static final String SORT_BY_QUERY = "sort_by";

    public static URL buildUrl(int type, int sort_type, int page) {

        ///&include_video=false&page=1
        String sortParam = "";


        if((type != MOST_POPULAR && type != HIGHEST_RATED) ||
                (sort_type != ASCENDING && sort_type != DESCENDING) ||
                page <= 0)
        {
            //TODO Return default URL
            return null;
        }
        else
        {
            StringBuilder sb = new StringBuilder();

            if(type == MOST_POPULAR)
            {
                sb.append(MOST_POPULAR_STRING);
            }
            else
            {
                sb.append(HIGHEST_RATED_STRING);
            }

            sb.append(".");

            if(sort_type == ASCENDING)
            {
                sb.append(ASCENDING_STRING);
            }
            else
            {
                sb.append(DESCENDING_STRING);
            }

            sortParam = sb.toString();
        }


        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
        .appendPath(DISCOVER)
        .appendPath(MOVIE)
        .appendQueryParameter(API_KEY_QUERY, APIKEY)
        .appendQueryParameter(LANGUAGE_QUERY, LANGUAGE)
        .appendQueryParameter(SORT_BY_QUERY, sortParam)
        .appendQueryParameter(INCLUDE_ADULT_QUERY, "false")
        .appendQueryParameter(INCLUDE_VIDEO_QUERY, "false")
        .appendQueryParameter(PAGE_QUERY, Integer.toString(page))
        .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildTrailerUrl(int movieId) {

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(MOVIE)
                .appendPath(movieId + "")
                .appendPath(VIDEOS)
                .appendQueryParameter(API_KEY_QUERY, APIKEY)
                .appendQueryParameter(LANGUAGE_QUERY, LANGUAGE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildReviewUrl(int movieId) {

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(MOVIE)
                .appendPath(movieId + "")
                .appendPath(REVIEWS)
                .appendQueryParameter(API_KEY_QUERY, APIKEY)
                .appendQueryParameter(LANGUAGE_QUERY, LANGUAGE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
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
