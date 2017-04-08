package com.example.android.udacitymovieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.PersistableBundle;
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

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final String SAVED_STATE_STRING = "data";

    private RecyclerView mRecyclerView;
    private MovieListRVAdapter mMovieAdapter;

    private RelativeLayout mLoadingView;
    private TextView mErrorMessage;
    private ArrayList<MovieItemListModel> mData;

    private int mCurrentState = MovieNetworkUtils.MOST_POPULAR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);

        mLoadingView = (RelativeLayout) findViewById(R.id.activity_main_loading_view);

        mErrorMessage = (TextView) findViewById(R.id.activity_main_error_message);

        mMovieAdapter = new MovieListRVAdapter();
        mRecyclerView.setAdapter(mMovieAdapter);

        GridLayoutManager gm = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gm);

        if(savedInstanceState!=null)
        {
            Log.d("TAG", "We had a saved instance");

            mData = savedInstanceState.getParcelableArrayList(SAVED_STATE_STRING);

            if(mData != null && mData.size() > 0)
            {
                mLoadingView.setVisibility(View.VISIBLE);
                mErrorMessage.setVisibility(View.INVISIBLE);

                mMovieAdapter.setMovieItemData(mData);
            }
        }
        else
        {
            loadDefaultMovieWeatherData();
        }
    }


    public void loadDefaultMovieWeatherData()
    {
        int type = mCurrentState;
        int type_sort = MovieNetworkUtils.DESCENDING;
        int page = 1;

        Integer[] list = new Integer[3];
        list[0] = type;
        list[1] = type_sort;
        list[2] = page;

        mLoadingView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);

        MovieLoader mv = new MovieLoader();
        mv.execute(list);
    }

    public class MovieLoader extends AsyncTask<Integer, Void, ArrayList<MovieItemListModel> >
    {


        @Override
        protected ArrayList<MovieItemListModel> doInBackground(Integer... integers) {

            if(integers.length == 3)
            {
                URL movieRequestUrl = MovieNetworkUtils.buildUrl(integers[0], integers[1], integers[2]);

                try {
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
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItemListModel> movieItemListModels) {
            if(movieItemListModels != null)
            {
                mLoadingView.setVisibility(View.INVISIBLE);
                mErrorMessage.setVisibility(View.INVISIBLE);

                mData = movieItemListModels;
                mMovieAdapter.setMovieItemData(mData);
            }
            else
            {
                mLoadingView.setVisibility(View.INVISIBLE);
                mErrorMessage.setVisibility(View.VISIBLE);
                mErrorMessage.setText(R.string.error_message);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(SAVED_STATE_STRING, mData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mCurrentState == MovieNetworkUtils.MOST_POPULAR)
        {
            mCurrentState = MovieNetworkUtils.HIGHEST_RATED;
            item.setTitle(R.string.popular_menu_string);
        }
        else
        {
            mCurrentState = MovieNetworkUtils.MOST_POPULAR;
            item.setTitle(R.string.rating_menu_string);
        }

        loadDefaultMovieWeatherData();

        return super.onOptionsItemSelected(item);
    }
}
