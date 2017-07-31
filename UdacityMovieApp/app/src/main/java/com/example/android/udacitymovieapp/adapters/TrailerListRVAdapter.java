package com.example.android.udacitymovieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.android.udacitymovieapp.ViewModels.TrailerItemListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rafa2093 on 7/30/2017.
 */

public class TrailerListRVAdapter extends RecyclerView.Adapter<TrailerListRVAdapter.TrailerListItemViewHolder>{
    private ArrayList<TrailerItemListModel> mTrailerData;

    public TrailerListRVAdapter()
    {
        mTrailerData = new ArrayList<>();
    }

    @Override
    public TrailerListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final Context appContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(appContext);

        View v = inflater.inflate(R.layout.trailer_item, parent, false);


        return new TrailerListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TrailerListItemViewHolder holder, int position) {
        final TrailerItemListModel trailer = mTrailerData.get(position);

        holder.trailerName.setText(trailer
                .getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext();
                openTrailer(trailer.getKey(), view.getContext());
            }
        });
    }

    private void openTrailer(String key, Context c) {
        String url = "https://www.youtube.com/watch?v=" + key;
        Uri webpage = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(c.getPackageManager()) != null) {
            c.startActivity(intent);
        }
    }


    @Override
    public int getItemCount() {
        return mTrailerData.size();
    }

    public void setMovieItemData(ArrayList<TrailerItemListModel> data)
    {
        mTrailerData = data;
        notifyDataSetChanged();
    }

    public class TrailerListItemViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.trailer_name)
        TextView trailerName;

        TrailerListItemViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
