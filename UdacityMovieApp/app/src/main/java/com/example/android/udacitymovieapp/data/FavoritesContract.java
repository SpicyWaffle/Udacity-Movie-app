package com.example.android.udacitymovieapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rafa2093 on 7/11/2017.
 */

public final class FavoritesContract {

    private FavoritesContract(){}
    public static final String AUTHORITY = "com.example.android.udacitymovieapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVORITES = "favorites";

    public static final class FavoritesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();


        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_TITLE = "description";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTERPATH = "poster_path";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_BACKDROP = "back_drop";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_IMAGE = "image";

    }
}
