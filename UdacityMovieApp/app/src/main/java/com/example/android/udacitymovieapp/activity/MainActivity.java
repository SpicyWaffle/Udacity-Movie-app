package com.example.android.udacitymovieapp.activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.udacitymovieapp.MovieJSONParser;
import com.example.android.udacitymovieapp.MovieNetworkUtils;
import com.example.android.udacitymovieapp.R;
import com.example.android.udacitymovieapp.ViewModels.MovieItemListModel;
import com.example.android.udacitymovieapp.adapters.MovieListRVAdapter;
import com.example.android.udacitymovieapp.data.FavoritesContract;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static junit.framework.Assert.assertTrue;

public class MainActivity extends AppCompatActivity {

    public final String SAVED_STATE_STRING = "data";
    public final String SAVED_TOGGLE_STATE_STRING = "toggle";

    private MovieListRVAdapter mMovieAdapter;

    private ArrayList<MovieItemListModel> mData;

    private int mCurrentState = MovieNetworkUtils.MOST_POPULAR;

    public final int ASYNC_TASK_LOADER_ID = 100;
    public final int CURSOR_LOADER_ID = 101;

    public final String BUNDLE_EXTRA_TYPE = "type";
    public final String BUNDLE_EXTRA_TYPE_SORT = "type_sort";
    public final String BUNDLE_EXTRA_TYPE_PAGE = "page";

    /*
    Instantiate Views
     */
    @BindView(R.id.activity_main_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_loading_view) RelativeLayout mLoadingView;
    @BindView(R.id.activity_main_error_message) TextView mErrorMessage;

    LoaderManager.LoaderCallbacks<ArrayList<MovieItemListModel>> mAsyncTaskLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<ArrayList<MovieItemListModel>>() {
        @Override
        public Loader<ArrayList<MovieItemListModel>> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<ArrayList<MovieItemListModel>>(getApplicationContext()) {
                @Override
                public ArrayList<MovieItemListModel> loadInBackground() {
                    try {

                        int type = args.getInt(BUNDLE_EXTRA_TYPE);
                        int page_sort = args.getInt(BUNDLE_EXTRA_TYPE_SORT);
                        int page = args.getInt(BUNDLE_EXTRA_TYPE_PAGE);

                        URL movieRequestUrl = MovieNetworkUtils.buildUrl(type, page_sort, page);


                        String jsonMovieResponse =  MovieNetworkUtils
                                .getResponseFromHttpUrl(movieRequestUrl);


                        ArrayList<MovieItemListModel> data = MovieJSONParser.getMovieListFromJSONString(jsonMovieResponse);
                        Log.d("TAG", jsonMovieResponse);
                        return data;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onStartLoading() {
                    if(args == null)
                    {
                        return;
                    }

                    forceLoad();
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<MovieItemListModel>> loader, ArrayList<MovieItemListModel> data) {
            if(data != null)
            {
                mLoadingView.setVisibility(View.INVISIBLE);
                mErrorMessage.setVisibility(View.INVISIBLE);

                mData = data;
                mMovieAdapter.setMovieItemData(mData);
            }
            else
            {
                mLoadingView.setVisibility(View.INVISIBLE);
                mErrorMessage.setVisibility(View.VISIBLE);
                mErrorMessage.setText(R.string.error_message);
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<MovieItemListModel>> loader) {

        }
    };

    LoaderManager.LoaderCallbacks<Cursor> mCursorLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<Cursor>(getApplicationContext()){
                @Override
                public Cursor loadInBackground() {
                    Cursor cursor = getApplicationContext().getContentResolver().query(
                            FavoritesContract.FavoritesEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                    return cursor;
                }

                @Override
                protected void onStartLoading() {
                    if(args == null)
                    {
                        return;
                    }

                    forceLoad();
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null)
            {
                ArrayList<MovieItemListModel> movies = new ArrayList<MovieItemListModel>();
                for(int i = 0; i < data.getCount(); i++)
                {
                    data.moveToPosition(i);
                    int idIdx = data.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_ID);
                    int releaseDateIdx = data.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE);
                    int posterPathIdx = data.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_POSTERPATH);
                    int voteCountIdx = data.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_VOTE_COUNT);
                    int backDropIdx = data.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_BACKDROP);
                    int synopsisIdx = data.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_SYNOPSIS);
                    int imageIdx = data.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_IMAGE);
                    int titleIdx = data.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_TITLE);
                    int popularityIdx = data.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_POPULARITY);



                    int id = data.getInt(idIdx);
                    String releaseDate = data.getString(releaseDateIdx);
                    String posterPath = data.getString(posterPathIdx);
                    float voteCount = data.getFloat(voteCountIdx);
                    String backDrop = data.getString(backDropIdx);
                    String synopsis = data.getString(synopsisIdx);
                    //int imageIdx =
                    String title = data.getString(titleIdx);
                    double popularity = data.getDouble(popularityIdx);

                    MovieItemListModel movie = new MovieItemListModel(title,
                            synopsis,
                            id,
                            releaseDate,
                            posterPath,
                            backDrop,
                            popularity,
                            voteCount);

                    movies.add(movie);

                    Log.d("Items",
                            " id: " + id +
                            " release: " + releaseDate +
                            " posterp: " + posterPath +
                            " voteCount: " + voteCount +
                            " backdrop: " + backDrop +
                            " synopsis: " + synopsis +
                            " title: " + title +
                            " popularity: " + popularity);
                }
                mMovieAdapter.setMovieItemData(movies);
                mData = movies;
                mLoadingView.setVisibility(View.INVISIBLE);
                mErrorMessage.setVisibility(View.INVISIBLE);
                data.close();
            }
            else
            {
                mLoadingView.setVisibility(View.INVISIBLE);
                mErrorMessage.setVisibility(View.VISIBLE);
                mErrorMessage.setText(R.string.error_message);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        onSaveInstanceState(new Bundle());
        getSupportLoaderManager().destroyLoader(CURSOR_LOADER_ID);
        getSupportLoaderManager().destroyLoader(ASYNC_TASK_LOADER_ID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMovieAdapter = new MovieListRVAdapter();
        mRecyclerView.setAdapter(mMovieAdapter);

        GridLayoutManager gm = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gm);

        if(savedInstanceState == null)
        {
            loadDefaultMovieWeatherData();
        }
    }


    public void loadDefaultMovieWeatherData()
    {
        int type = mCurrentState;
        int type_sort = MovieNetworkUtils.DESCENDING;
        int page = 1;

        mLoadingView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);

        switch (type)
        {
            case MovieNetworkUtils.FAVORITES:
                Bundle favBundle = new Bundle();
                Loader<String> asyncLoader2 = getSupportLoaderManager()
                        .getLoader(CURSOR_LOADER_ID);
                if (asyncLoader2 == null) {
                    getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, favBundle, mCursorLoaderCallbacks);
                } else {
                    getSupportLoaderManager().restartLoader(CURSOR_LOADER_ID, favBundle, mCursorLoaderCallbacks);
                }
                break;
            default:
                Bundle b = new Bundle();
                b.putInt(BUNDLE_EXTRA_TYPE, type);
                b.putInt(BUNDLE_EXTRA_TYPE_PAGE, page);
                b.putInt(BUNDLE_EXTRA_TYPE_SORT, type_sort);

                Loader<String> asyncLoader = getSupportLoaderManager()
                        .getLoader(ASYNC_TASK_LOADER_ID);
                if (asyncLoader == null) {
                    getSupportLoaderManager().initLoader(ASYNC_TASK_LOADER_ID, b, mAsyncTaskLoaderCallbacks);
                } else {
                    getSupportLoaderManager().restartLoader(ASYNC_TASK_LOADER_ID, b, mAsyncTaskLoaderCallbacks);
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(SAVED_STATE_STRING, mData);
        outState.putInt(SAVED_TOGGLE_STATE_STRING,mCurrentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean stateChanged = false;

        int id = item.getItemId();

        if(item.getItemId() == R.id.action_sort_rating
                && mCurrentState != MovieNetworkUtils.HIGHEST_RATED)
        {
            mCurrentState = MovieNetworkUtils.HIGHEST_RATED;
            stateChanged = true;
        }
        else if(item.getItemId() == R.id.action_sort_popularity
                && mCurrentState != MovieNetworkUtils.MOST_POPULAR)
        {
            mCurrentState = MovieNetworkUtils.MOST_POPULAR;
            stateChanged = true;
        }
        else if(item.getItemId() == R.id.action_sort_favorite
                && mCurrentState != MovieNetworkUtils.FAVORITES)
        {
            mCurrentState = MovieNetworkUtils.FAVORITES;
            stateChanged = true;
        }

        if(stateChanged)
        {
            loadDefaultMovieWeatherData();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mCurrentState == MovieNetworkUtils.FAVORITES)
        {
            loadDefaultMovieWeatherData();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d("TAG", "We had a saved instance");

        mData = savedInstanceState.getParcelableArrayList(SAVED_STATE_STRING);
        mCurrentState = savedInstanceState.getInt(SAVED_TOGGLE_STATE_STRING);

        if(mData != null && mData.size() > 0)
        {
            mLoadingView.setVisibility(View.VISIBLE);
            mErrorMessage.setVisibility(View.INVISIBLE);

            mMovieAdapter.setMovieItemData(mData);
        }

        loadDefaultMovieWeatherData();

    }
}
