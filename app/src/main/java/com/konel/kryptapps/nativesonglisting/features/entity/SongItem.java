package com.konel.kryptapps.nativesonglisting.features.entity;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 22 Jan 2018 6:17 PM
 */


public class SongItem {

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
    @Nullable
    private Bitmap bitmap;

    public SongItem(@Nullable String artWorkUrl,
                    @Nullable String trackName,
                    @Nullable String primaryGenreName,
                    @Nullable String artistName,
                    @Nullable String currency,
                    double trackPrice) {

        this.artWorkUrl = artWorkUrl == null ? "" : artWorkUrl;
        this.trackName = trackName == null ? "" : trackName;
        this.primaryGenreName = primaryGenreName == null ? "" : primaryGenreName;
        this.artistName = artistName == null ? "" : artistName;
        this.currency = currency == null ? "" : currency;
        this.trackPrice = trackPrice;
        this.bitmap = null;
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

    @Nullable
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(@Nullable Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
