package com.example.android.udacitymovieapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by rafa2093 on 4/3/2017.
 */

public class MovieJSONParser {

    public static String RESULTS = "results";

    public static final String POSTER_PATH_STRING = "poster_path";
    public static final String BACKDROP_PATH_STRING = "backdrop_path";
    public static final String ID_STRING = "id";
    public static final String POP_STRING = "popularity";
    public static final String VOTE_STRING = "vote_average";
    public static final String RELEASE_STRING = "release_date";
    public static final String TITLE_STRING = "title";
    public static final String SYNOPSIS_STRING = "overview";


    public static ArrayList<MovieItemListModel> getMovieListFromJSONString(String json) throws JSONException
    {
        ArrayList<MovieItemListModel> res = new ArrayList<>();
        JSONObject movieListJSON = new JSONObject(json);

        JSONArray weatherArray = movieListJSON.getJSONArray(RESULTS);

        for (int i = 0; i < weatherArray.length(); i++)
        {
            JSONObject movie = weatherArray.getJSONObject(i);

            String movieTitle = movie.getString(TITLE_STRING);
            int movieId = movie.getInt(ID_STRING);
            double movieVotes = movie.getDouble(VOTE_STRING);
            double moviePop = movie.getDouble(POP_STRING);
            String movieRelease = movie.getString(RELEASE_STRING);
            String moviePoster = movie.getString(POSTER_PATH_STRING);
            String backdrop = movie.getString(BACKDROP_PATH_STRING);
            String synopsis = movie.getString(SYNOPSIS_STRING);

            res.add(new MovieItemListModel(movieTitle,synopsis, movieId, movieRelease, moviePoster, backdrop, moviePop, (float)movieVotes));
            Log.d("Tag", "Test");
        }
        Log.d("test", movieListJSON.toString());

        return res;
    }
}
