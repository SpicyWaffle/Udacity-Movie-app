package com.example.android.udacitymovieapp.ViewModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rafa2093 on 4/3/2017.
 */

public class MovieItemListModel implements Parcelable {

    private String originalTitle;
    private int id;
    private String releaseDate;
    private String posterPath;
    private double popularity;
    private float vote_count;
    private String backdrop;
    private String synopsis;

    public MovieItemListModel(String title, String synopsis, int id, String releaseD, String posterPath, String backdropPAth, double popularity, float vote_count)
    {
        setOriginalTitle(title);
        setId(id);
        setReleaseDate(releaseD);
        setPosterPath(posterPath);
        setPopularity(popularity);
        setVote_count(vote_count);
        setBackdrop(backdropPAth);
        setSynopsis(synopsis);
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public float getVote_count() {
        return vote_count;
    }

    public void setVote_count(float vote_count) {
        this.vote_count = vote_count;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }


    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalTitle);
        dest.writeInt(this.id);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeDouble(this.popularity);
        dest.writeFloat(this.vote_count);
        dest.writeString(this.backdrop);
        dest.writeString(this.synopsis);
    }

    protected MovieItemListModel(Parcel in) {
        this.originalTitle = in.readString();
        this.id = in.readInt();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.popularity = in.readDouble();
        this.vote_count = in.readFloat();
        this.backdrop = in.readString();
        this.synopsis = in.readString();
    }

    public static final Creator<MovieItemListModel> CREATOR = new Creator<MovieItemListModel>() {
        @Override
        public MovieItemListModel createFromParcel(Parcel source) {
            return new MovieItemListModel(source);
        }

        @Override
        public MovieItemListModel[] newArray(int size) {
            return new MovieItemListModel[size];
        }
    };
}
