package com.konel.kryptapps.nativesonglisting.features.details;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 22 Jan 2018 8:36 PM
 */


public class DetailsActivityModel implements Parcelable {

    @NonNull
    private String artWorkUrl;
    @NonNull
    private String trackName;
    @NonNull
    private String primaryGenreName;
    @NonNull
    private String artistName;
    @NonNull
    private String currency;
    private double trackPrice;

    public DetailsActivityModel(@NonNull String artWorkUrl,
                                @NonNull String trackName,
                                @NonNull String primaryGenreName,
                                @NonNull String artistName,
                                @NonNull String currency,
                                double trackPrice) {

        this.artWorkUrl = artWorkUrl;
        this.trackName = trackName;
        this.primaryGenreName = primaryGenreName;
        this.artistName = artistName;
        this.currency = currency;
        this.trackPrice = trackPrice;
    }

    @NonNull
    public String getArtWorkUrl() {
        return artWorkUrl;
    }

    @NonNull
    public String getTrackName() {
        return trackName;
    }

    @NonNull
    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    @NonNull
    public String getArtistName() {
        return artistName;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.artWorkUrl);
        dest.writeString(this.trackName);
        dest.writeString(this.primaryGenreName);
        dest.writeString(this.artistName);
        dest.writeString(this.currency);
        dest.writeDouble(this.trackPrice);
    }

    private DetailsActivityModel(Parcel in) {
        this.artWorkUrl = in.readString();
        this.trackName = in.readString();
        this.primaryGenreName = in.readString();
        this.artistName = in.readString();
        this.currency = in.readString();
        this.trackPrice = in.readDouble();
    }

    public static final Parcelable.Creator<DetailsActivityModel> CREATOR = new Parcelable.Creator<DetailsActivityModel>() {
        @Override
        public DetailsActivityModel createFromParcel(Parcel source) {
            return new DetailsActivityModel(source);
        }

        @Override
        public DetailsActivityModel[] newArray(int size) {
            return new DetailsActivityModel[size];
        }
    };
}
