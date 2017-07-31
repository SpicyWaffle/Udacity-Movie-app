package com.example.android.udacitymovieapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rafa2093 on 7/11/2017.
 */

public class FavoritesContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.udacitymovieapp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_FAVORITES = "favorites";

    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class FavoritesEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();


        // Task table and column names
        public static final String TABLE_NAME = "favorites";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_TITLE = "description";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTERPATH = "poster_path";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_BACKDROP = "back_drop";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_IMAGE = "image";


        /*
        private String originalTitle;
        private int id;
        private String releaseDate;
        private String posterPath;
        private double popularity;
        private float vote_count;
        private String backdrop;
        private String synopsis;
         */

        /*

        Note: Because this implements BaseColumns, the _id column is generated automatically

        tasks
         - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    description     |    priority   |
         - - - - - - - - - - - - - - - - - - - - - -


         */

    }
}
