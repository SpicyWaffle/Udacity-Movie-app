package com.example.android.udacitymovieapp.ViewModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rafa2093 on 7/27/2017.
 */

public class TrailerItemListModel implements Parcelable {
    /*
    "id": "533ec672c3a368544800228f",
      "iso_639_1": "en",
      "iso_3166_1": "US",
      "key": "0YPmRMipnSM",
      "name": "The Five Obstructions (2004)",
      "site": "YouTube",
      "size": 360,
      "type": "Trailer"
     */

    private String id;
    private String key;
    private String site;
    private String name;

    public TrailerItemListModel(String id, String key, String site, String name)
    {
        this.id = id;
        this.key = key;
        this.site = site;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.key);
        dest.writeString(this.site);
        dest.writeString(this.name);
    }

    protected TrailerItemListModel(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.site = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<TrailerItemListModel> CREATOR = new Parcelable.Creator<TrailerItemListModel>() {
        @Override
        public TrailerItemListModel createFromParcel(Parcel source) {
            return new TrailerItemListModel(source);
        }

        @Override
        public TrailerItemListModel[] newArray(int size) {
            return new TrailerItemListModel[size];
        }
    };
}
