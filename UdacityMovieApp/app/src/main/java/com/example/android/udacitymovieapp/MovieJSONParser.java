package com.example.android.udacitymovieapp;

import android.util.Log;

import com.example.android.udacitymovieapp.ViewModels.MovieItemListModel;
import com.example.android.udacitymovieapp.ViewModels.ReviewItemListModel;
import com.example.android.udacitymovieapp.ViewModels.TrailerItemListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rafa2093 on 4/3/2017.
 */

public class MovieJSONParser {

    private static String RESULTS = "results";

    private static final String POSTER_PATH_STRING = "poster_path";
    private static final String BACKDROP_PATH_STRING = "backdrop_path";
    private static final String ID_STRING = "id";
    private static final String POP_STRING = "popularity";
    private static final String VOTE_STRING = "vote_average";
    private static final String RELEASE_STRING = "release_date";
    private static final String TITLE_STRING = "title";
    private static final String SYNOPSIS_STRING = "overview";

    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String URL = "url";
    private static final String KEY = "key";
    private static final String SITE = "site";
    private static final String NAME = "name";


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

    public static ArrayList<ReviewItemListModel> getReviewListFromJSONString(String json) throws JSONException
    {
        ArrayList<ReviewItemListModel> res = new ArrayList<>();
        JSONObject movieListJSON = new JSONObject(json);

        JSONArray weatherArray = movieListJSON.getJSONArray(RESULTS);

        for (int i = 0; i < weatherArray.length(); i++)
        {
            JSONObject movie = weatherArray.getJSONObject(i);

            String id = movie.getString(ID_STRING);
            String author = movie.getString(AUTHOR);
            String content = movie.getString(CONTENT);
            String url = movie.getString(URL);

            res.add(new ReviewItemListModel(id, author, content, url));
            Log.d("Tag", "Test");
        }
        Log.d("test", movieListJSON.toString());

        return res;
    }

    public static ArrayList<TrailerItemListModel> getTrailerListFromJSONString(String json) throws JSONException
    {
        ArrayList<TrailerItemListModel> res = new ArrayList<>();
        JSONObject movieListJSON = new JSONObject(json);

        JSONArray weatherArray = movieListJSON.getJSONArray(RESULTS);

        for (int i = 0; i < weatherArray.length(); i++)
        {
            JSONObject movie = weatherArray.getJSONObject(i);

            String id = movie.getString(ID_STRING);
            String key = movie.getString(KEY);
            String site = movie.getString(SITE);
            String name = movie.getString(NAME);

            res.add(new TrailerItemListModel(id, key, site, name));
            Log.d("Tag", "Test");
        }
        Log.d("test", movieListJSON.toString());

        return res;
    }
}
