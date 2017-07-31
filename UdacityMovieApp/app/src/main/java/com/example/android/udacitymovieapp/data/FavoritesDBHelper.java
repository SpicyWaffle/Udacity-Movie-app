package com.example.android.udacitymovieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rafa2093 on 7/11/2017.
 */

public class FavoritesDBHelper extends SQLiteOpenHelper{

    // The name of the database
    private static final String DATABASE_NAME = "favoritesDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    FavoritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + FavoritesContract.FavoritesEntry.TABLE_NAME + " (" +
                FavoritesContract.FavoritesEntry.COLUMN_ID                + " INTEGER PRIMARY KEY, " +
                FavoritesContract.FavoritesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_POSTERPATH + " TEXT, " +
                FavoritesContract.FavoritesEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_VOTE_COUNT + " REAL NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_BACKDROP + " TEXT, " +
                FavoritesContract.FavoritesEntry.COLUMN_IMAGE + " BLOB, " +
                FavoritesContract.FavoritesEntry.COLUMN_SYNOPSIS    + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoritesContract.FavoritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
