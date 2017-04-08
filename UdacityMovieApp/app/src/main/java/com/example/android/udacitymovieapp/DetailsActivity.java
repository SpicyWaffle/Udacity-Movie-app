package com.example.android.udacitymovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private ImageView mBackdropImage;
    private ImageView mPosterImageView;
    private TextView mMovieTitle;
    private TextView mReleaseDateTV;
    private TextView mMovieSynopsis;

    private RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mBackdropImage = (ImageView) findViewById(R.id.movie_details_page_trailers_layout);
        mPosterImageView = (ImageView) findViewById(R.id.movie_details_page_tv_movie_poster);
        mMovieTitle = (TextView) findViewById(R.id.movie_details_page_tv_movie_title);
        mReleaseDateTV = (TextView) findViewById(R.id.movie_details_page_tv_movie_release_date);
        mMovieSynopsis = (TextView) findViewById(R.id.movie_details_page_plot);
        mRatingBar = (RatingBar) findViewById(R.id.movie_details_page_rating);

        Intent intent = getIntent();
        if(intent.hasExtra(MovieListRVAdapter.PARCELABLE_EXTRA_STRING))
        {
            MovieItemListModel movie = intent.getParcelableExtra(MovieListRVAdapter.PARCELABLE_EXTRA_STRING);

            Picasso
                    .with(this)
                    .load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .placeholder(R.drawable.placeholder)
                    .into(mPosterImageView);

            Picasso
                    .with(this)
                    .load("http://image.tmdb.org/t/p/w500" + movie.getBackdrop())
                    .placeholder(R.drawable.placeholder)
                    .into(mBackdropImage);

            mMovieTitle.setText(movie.getOriginalTitle());
            mReleaseDateTV.setText(movie.getReleaseDate());
            mMovieSynopsis.setText(movie.getSynopsis());
            mRatingBar.setRating(movie.getVote_count());
        }
        else
        {

        }
    }
}
