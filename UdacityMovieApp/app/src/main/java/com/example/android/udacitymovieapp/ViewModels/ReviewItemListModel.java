package com.example.android.udacitymovieapp.ViewModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rafa2093 on 7/27/2017.
 */

public class ReviewItemListModel implements Parcelable {
    //"id": "529bc23719c2957215011e7b",
    // "author": "BradFlix",
    // "content": "I just plain love this movie!",
    // "url": "https://www.themoviedb.org/review/529bc23719c2957215011e7b"

    private String id;
    private String author;
    private String content;
    private String url;

    public ReviewItemListModel(String id, String author, String content, String url)
    {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    protected ReviewItemListModel(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ReviewItemListModel> CREATOR = new Parcelable.Creator<ReviewItemListModel>() {
        @Override
        public ReviewItemListModel createFromParcel(Parcel source) {
            return new ReviewItemListModel(source);
        }

        @Override
        public ReviewItemListModel[] newArray(int size) {
            return new ReviewItemListModel[size];
        }
    };
}
