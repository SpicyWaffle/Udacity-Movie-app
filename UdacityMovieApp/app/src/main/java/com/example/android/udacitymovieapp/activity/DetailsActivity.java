package com.example.android.udacitymovieapp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.udacitymovieapp.MovieJSONParser;
import com.example.android.udacitymovieapp.MovieNetworkUtils;
import com.example.android.udacitymovieapp.R;
import com.example.android.udacitymovieapp.ViewModels.MovieItemListModel;
import com.example.android.udacitymovieapp.ViewModels.ReviewItemListModel;
import com.example.android.udacitymovieapp.ViewModels.TrailerItemListModel;
import com.example.android.udacitymovieapp.adapters.MovieListRVAdapter;
import com.example.android.udacitymovieapp.adapters.ReviewListRVAdapter;
import com.example.android.udacitymovieapp.adapters.TrailerListRVAdapter;
import com.example.android.udacitymovieapp.data.FavoritesContract;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity{

    @BindView(R.id.movie_details_page_trailers_layout)
    ImageView mBackdropImage;

    @BindView(R.id.movie_details_page_tv_movie_poster)
    ImageView mPosterImageView;

    @BindView(R.id.movie_details_page_tv_movie_title)
    TextView mMovieTitle;

    @BindView(R.id.movie_details_page_tv_movie_release_date)
    TextView mReleaseDateTV;

    @BindView(R.id.movie_details_page_plot)
    TextView mMovieSynopsis;

    @BindView(R.id.favorite_button)
    LikeButton mFavoriteButton;

    @BindView(R.id.movie_details_page_rating)
    RatingBar mRatingBar;

    @BindView(R.id.reviews_rv)
    RecyclerView mReviewsRV;

    @BindView(R.id.trailers_rv)
    RecyclerView mTrailersRV;



    private MovieItemListModel mMovie;

    private ReviewListRVAdapter reviewAdapter;
    private TrailerListRVAdapter trailerAdapter;

    private ArrayList<ReviewItemListModel> mReviewItems;
    private ArrayList<TrailerItemListModel> mTrailerItems;

    public final int TRAILERS_LOADER_ID = 100;
    public final int REVIEWS_LOADER_ID = 101;

    public static final String BUNDLE_ID = "ID";

    LoaderManager.LoaderCallbacks<ArrayList<ReviewItemListModel>> mAsyncTaskLoaderReviews =
            new LoaderManager.LoaderCallbacks<ArrayList<ReviewItemListModel>>() {
                @Override
                public Loader<ArrayList<ReviewItemListModel>> onCreateLoader(int id, final Bundle args) {
                    return new AsyncTaskLoader<ArrayList<ReviewItemListModel>>(getApplicationContext()) {
                        @Override
                        public ArrayList<ReviewItemListModel> loadInBackground() {
                            try {

                                int id = args.getInt(BUNDLE_ID);

                                URL movieRequestUrl = MovieNetworkUtils.buildReviewUrl(id);

                                String jsonMovieResponse =  MovieNetworkUtils
                                        .getResponseFromHttpUrl(movieRequestUrl);


                                ArrayList<ReviewItemListModel> data = MovieJSONParser.getReviewListFromJSONString(jsonMovieResponse);
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
                public void onLoadFinished(Loader<ArrayList<ReviewItemListModel>> loader, ArrayList<ReviewItemListModel> data) {
                    //Set the data to the adapter
                    if(data != null)
                    {
                        mReviewItems = data;
                        reviewAdapter.setMovieItemData(mReviewItems);
                    }
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<ReviewItemListModel>> loader) {

                }
            };

    LoaderManager.LoaderCallbacks<ArrayList<TrailerItemListModel>> mAsyncTaskLoaderTrailers =
            new LoaderManager.LoaderCallbacks<ArrayList<TrailerItemListModel>>() {
                @Override
                public Loader<ArrayList<TrailerItemListModel>> onCreateLoader(int id, final Bundle args) {
                    return new AsyncTaskLoader<ArrayList<TrailerItemListModel>>(getApplicationContext()) {
                        @Override
                        public ArrayList<TrailerItemListModel> loadInBackground() {
                            try {

                                int id = args.getInt(BUNDLE_ID);

                                URL movieRequestUrl = MovieNetworkUtils.buildTrailerUrl(id);

                                String jsonMovieResponse =  MovieNetworkUtils
                                        .getResponseFromHttpUrl(movieRequestUrl);

                                ArrayList<TrailerItemListModel> data = MovieJSONParser.getTrailerListFromJSONString(jsonMovieResponse);
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
                public void onLoadFinished(Loader<ArrayList<TrailerItemListModel>> loader, ArrayList<TrailerItemListModel> data) {
                    //Set the data to the adapter
                    if(data != null)
                    {
                        mTrailerItems = data;
                        trailerAdapter.setMovieItemData(mTrailerItems);
                    }

                }

                @Override
                public void onLoaderReset(Loader<ArrayList<TrailerItemListModel>> loader) {

                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        reviewAdapter = new ReviewListRVAdapter();
        trailerAdapter = new TrailerListRVAdapter();

        mReviewsRV.setAdapter(reviewAdapter);
        mTrailersRV.setAdapter(trailerAdapter);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);

        mReviewsRV.setLayoutManager(lm);
        mTrailersRV.setLayoutManager(lm2);

        Intent intent = getIntent();
        if(intent.hasExtra(MovieListRVAdapter.PARCELABLE_EXTRA_STRING))
        {
            mMovie = intent.getParcelableExtra(MovieListRVAdapter.PARCELABLE_EXTRA_STRING);

            Picasso
                    .with(this)
                    .load("http://image.tmdb.org/t/p/w500" + mMovie.getPosterPath())
                    .placeholder(R.drawable.placeholder)
                    .into(mPosterImageView);

            Picasso
                    .with(this)
                    .load("http://image.tmdb.org/t/p/w500" + mMovie.getBackdrop())
                    .placeholder(R.drawable.placeholder)
                    .into(mBackdropImage);

            mMovieTitle.setText(mMovie.getOriginalTitle());
            mReleaseDateTV.setText(mMovie.getReleaseDate());
            mMovieSynopsis.setText(mMovie.getSynopsis());
            mRatingBar.setRating(mMovie.getVote_count());

            Bundle b = new Bundle();
            b.putInt(BUNDLE_ID, mMovie.getId());

            Loader<String> asyncLoader = getSupportLoaderManager()
                    .getLoader(REVIEWS_LOADER_ID);
            if (asyncLoader == null) {
                getSupportLoaderManager().initLoader(REVIEWS_LOADER_ID, b, mAsyncTaskLoaderReviews);
            } else {
                getSupportLoaderManager().restartLoader(REVIEWS_LOADER_ID, b, mAsyncTaskLoaderReviews);
            }

            Loader<String> trailersLoader = getSupportLoaderManager()
                    .getLoader(TRAILERS_LOADER_ID);
            if (trailersLoader == null) {
                getSupportLoaderManager().initLoader(TRAILERS_LOADER_ID, b, mAsyncTaskLoaderTrailers);
            } else {
                getSupportLoaderManager().restartLoader(TRAILERS_LOADER_ID, b, mAsyncTaskLoaderTrailers);
            }


            try {
                Cursor mCursor = getContentResolver().query(
                        FavoritesContract.FavoritesEntry.CONTENT_URI,  // The content URI of the words table
                        null,                    // The columns to return for each row
                        FavoritesContract.FavoritesEntry.COLUMN_ID + " = ?",
                        new String[]{mMovie.getId() + ""},                   // Either null, or the word the user entered
                        // Either empty, or the string the user entered
                        FavoritesContract.FavoritesEntry.COLUMN_ID);

                if (null != mCursor && mCursor.getCount() < 1) {
                    Log.d("test2", "test2");
                    mCursor.close();
                }
                else
                {
                    mFavoriteButton.setLiked(true);
                    mCursor.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {

        }
    }

    @OnClick(R.id.favorite_button)
    public void onClickFavorite(LikeButton v)
    {
        if(!v.isLiked())
        {
            // add to content provider
            try {
                Cursor mCursor = getContentResolver().query(
                        FavoritesContract.FavoritesEntry.CONTENT_URI,  // The content URI of the words table
                        null,                    // The columns to return for each row
                        FavoritesContract.FavoritesEntry.COLUMN_ID + " = ?",
                        new String[]{mMovie.getId() + ""},                   // Either null, or the word the user entered
                        // Either empty, or the string the user entered
                        FavoritesContract.FavoritesEntry.COLUMN_ID);

                if(mCursor != null && mCursor.getCount() < 1)
                {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_ID, mMovie.getId()); //NOT NULL
                    contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate()); // NOT NULL
                    contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_POSTERPATH, mMovie.getPosterPath()); //NOT NULL
                    contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_VOTE_COUNT, mMovie.getVote_count()); //NOT NULL
                    contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_BACKDROP, mMovie.getBackdrop());
                    contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_SYNOPSIS, mMovie.getSynopsis()); //NOT NULL
                    contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_IMAGE, new byte[0]);
                    contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_TITLE, mMovie.getOriginalTitle()); //NOT NULL
                    contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_POPULARITY, mMovie.getPopularity()); // NOT NULL
                    Uri uri = getContentResolver().insert(FavoritesContract.FavoritesEntry.CONTENT_URI, contentValues);

                    if( uri != null ) {
                        Log.d("", "Added item to list: " + uri.getPath());
                        v.setLiked(true);
                    }
                }

                if(mCursor != null)
                {
                    mCursor.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            int removed = getContentResolver().delete(
                    FavoritesContract.FavoritesEntry.CONTENT_URI,
                    FavoritesContract.FavoritesEntry.COLUMN_ID + " = ?",
                    new String[] { mMovie.getId() + "" }                      // the value to compare to
            );

            if(removed > 0)
            {
                v.setLiked(false);
            }

            Log.d("", "Removed: " + removed + " items from the list");
        }
    }

    //Check if exists
}
