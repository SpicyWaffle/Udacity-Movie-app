package com.example.android.udacitymovieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.udacitymovieapp.R;
import com.example.android.udacitymovieapp.ViewModels.ReviewItemListModel;
import com.example.android.udacitymovieapp.ViewModels.TrailerItemListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rafa2093 on 7/30/2017.
 */

public class ReviewListRVAdapter extends RecyclerView.Adapter<ReviewListRVAdapter.ReviewListItemViewHolder>{
    private ArrayList<ReviewItemListModel> mTrailerData;

    public ReviewListRVAdapter()
    {
        mTrailerData = new ArrayList<>();
    }

    @Override
    public ReviewListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final Context appContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(appContext);

        View v = inflater.inflate(R.layout.review_item, parent, false);


        return new ReviewListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewListItemViewHolder holder, int position) {
        final ReviewItemListModel review = mTrailerData.get(position);

        holder.author.setText(review
                .getAuthor());
        holder.review.setText(review
                .getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext();
                openTrailer(review.getUrl(), view.getContext());
            }
        });
    }

    private void openTrailer(String url, Context c) {
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

    public void setMovieItemData(ArrayList<ReviewItemListModel> data)
    {
        mTrailerData = data;
        notifyDataSetChanged();
    }

    public class ReviewListItemViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.author_name)
        TextView author;
        @BindView(R.id.review_text)
        TextView review;

        ReviewListItemViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
