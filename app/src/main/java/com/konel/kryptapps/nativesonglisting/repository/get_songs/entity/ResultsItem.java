package com.konel.kryptapps.nativesonglisting.repository.get_songs.entity;

import android.support.annotation.Nullable;

public class ResultsItem {
    @Nullable
    private String artWorkUrl;
    private int trackTimeMillis;
    @Nullable
    private String country;
    @Nullable
    private String previewUrl;
    @Nullable
    private String releaseDate;
    private double collectionPrice;
    @Nullable
    private String trackName;
    @Nullable
    private String primaryGenreName;
    @Nullable
    private String collectionName;
    private double trackPrice;
    @Nullable
    private String artistName;
    @Nullable
    private String currency;
    private boolean isStreamable;

    @Nullable
    public String getArtWorkUrl() {
        return artWorkUrl;
    }

    public void setArtWorkUrl(@Nullable String artWorkUrl) {
        this.artWorkUrl = artWorkUrl;
    }

    public int getTrackTimeMillis() {
        return trackTimeMillis;
    }

    public void setTrackTimeMillis(int trackTimeMillis) {
        this.trackTimeMillis = trackTimeMillis;
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    public void setCountry(@Nullable String country) {
        this.country = country;
    }

    @Nullable
    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(@Nullable String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Nullable
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(@Nullable String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getCollectionPrice() {
        return collectionPrice;
    }

    public void setCollectionPrice(double collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    @Nullable
    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(@Nullable String trackName) {
        this.trackName = trackName;
    }

    @Nullable
    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(@Nullable String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    @Nullable
    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(@Nullable String collectionName) {
        this.collectionName = collectionName;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(double trackPrice) {
        this.trackPrice = trackPrice;
    }

    @Nullable
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(@Nullable String artistName) {
        this.artistName = artistName;
    }

    @Nullable
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@Nullable String currency) {
        this.currency = currency;
    }

    public boolean isStreamable() {
        return isStreamable;
    }

    public void setStreamable(boolean streamable) {
        isStreamable = streamable;
    }

    @Override
    public String toString() {
        return
                "ResultsItem{" +
                        "artWorkUrl = '" + artWorkUrl + '\'' +
                        ",trackTimeMillis = '" + trackTimeMillis + '\'' +
                        ",country = '" + country + '\'' +
                        ",previewUrl = '" + previewUrl + '\'' +
                        ",releaseDate = '" + releaseDate + '\'' +
                        ",collectionPrice = '" + collectionPrice + '\'' +
                        ",trackName = '" + trackName + '\'' +
                        ",primaryGenreName = '" + primaryGenreName + '\'' +
                        ",collectionName = '" + collectionName + '\'' +
                        ",trackPrice = '" + trackPrice + '\'' +
                        ",artistName = '" + artistName + '\'' +
                        ",currency = '" + currency + '\'' +
                        ",isStreamable = '" + isStreamable + '\'' +
                        "}";
    }
}
