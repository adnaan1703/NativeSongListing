package com.konel.kryptapps.nativesonglisting.repository.get_songs.entity;

import android.support.annotation.Nullable;

import com.konel.kryptapps.nativesonglisting.repository.ResponseBaseModel;

import java.util.List;

public class GetSongsResponse extends ResponseBaseModel {
    private int resultCount;
    @Nullable
    private List<ResultsItem> results;

    public GetSongsResponse(int responseCode, boolean isError) {
        super(responseCode, isError);
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public void setResults(@Nullable List<ResultsItem> results) {
        this.results = results;
    }

    @Nullable
    public List<ResultsItem> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return
                "GetSongsResponse{" +
                        "resultCount = '" + resultCount + '\'' +
                        ",results = '" + results + '\'' +
                        "}";
    }
}