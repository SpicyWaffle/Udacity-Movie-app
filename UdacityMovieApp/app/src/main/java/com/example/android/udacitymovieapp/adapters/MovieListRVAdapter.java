package com.example.android.udacitymovieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.udacitymovieapp.DetailsActivity;
import com.example.android.udacitymovieapp.R;
import com.example.android.udacitymovieapp.ViewModels.MovieItemListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rafa2093 on 4/2/2017.
 */

public class MovieListRVAdapter extends RecyclerView.Adapter<MovieListRVAdapter.MovieListItemViewHolder> {


    private ArrayList<MovieItemListModel> mMovieData;

    public static final String PARCELABLE_EXTRA_STRING = "movie_data";

    public MovieListRVAdapter()
    {
        mMovieData = new ArrayList<>();
    }

    @Override
    public MovieListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final Context appContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(appContext);

        View v = inflater.inflate(R.layout.movie_grid_item, parent, false);


        return new MovieListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieListItemViewHolder holder, int position) {
        final MovieItemListModel movie = mMovieData.get(position);

        Picasso
            .with(holder.itemView.getContext())
            .load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath())
            .placeholder(R.drawable.placeholder)
            .into(holder.mIv);

        holder.mMovieName.setText(movie.getOriginalTitle());
        holder.mMovieRating.setRating(movie.getVote_count());
        holder.mReleaseDate.setText(movie.getReleaseDate());

        final String movieTest = movie.getOriginalTitle();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext();
                //Toast.makeText(view.getContext(), "test: " + movieTest, Toast.LENGTH_LONG).show();
                Log.d("TAg", "Testing Item Click");
                Intent i = new Intent(view.getContext(), DetailsActivity.class);

                i.putExtra(PARCELABLE_EXTRA_STRING, movie);

                view.getContext().startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mMovieData.size();
    }

    public void setMovieItemData(ArrayList<MovieItemListModel> data)
    {
        mMovieData = data;
        notifyDataSetChanged();
    }

    public class MovieListItemViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.movie_grid_item_poster) ImageView mIv;
        @BindView(R.id.movie_grid_item_movie_genre_tv) TextView mReleaseDate;
        @BindView(R.id.movie_grid_item_movie_name_tv) TextView mMovieName;
        @BindView(R.id.movie_grid_item_rating_bar) RatingBar mMovieRating;


        MovieListItemViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
