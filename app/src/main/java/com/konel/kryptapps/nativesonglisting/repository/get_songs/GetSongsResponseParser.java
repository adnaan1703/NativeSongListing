package com.konel.kryptapps.nativesonglisting.repository.get_songs;

import android.support.annotation.NonNull;

import com.konel.kryptapps.nativesonglisting.repository.ResponseParser;
import com.konel.kryptapps.nativesonglisting.repository.get_songs.entity.GetSongsResponse;
import com.konel.kryptapps.nativesonglisting.repository.get_songs.entity.ResultsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 19 Jan 2018 6:05 PM
 */


public class GetSongsResponseParser implements ResponseParser<GetSongsResponse> {

    GetSongsResponseParser() {
    }

    @NonNull
    @Override
    public GetSongsResponse getParsedModel(int statusCode, @NonNull String jsonStr) throws JSONException {
        GetSongsResponse response;
        if (statusCode != 200) {
            response = new GetSongsResponse(statusCode, true);
            return response;
        }

        response = new GetSongsResponse(statusCode, false);
        JSONObject jsonResponse = new JSONObject(jsonStr);
        JSONArray jsonArray = jsonResponse.getJSONArray("results");
        List<ResultsItem> resultsItems = new ArrayList<>();
        for (int i = 0; i < jsonResponse.getInt("resultCount"); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ResultsItem resultsItem = new ResultsItem();
            if (jsonObject.has("artistName"))
                resultsItem.setArtistName(jsonObject.getString("artistName"));

            if (jsonObject.has("collectionName"))
                resultsItem.setCollectionName(jsonObject.getString("collectionName"));

            if (jsonObject.has("trackName"))
                resultsItem.setTrackName(jsonObject.getString("trackName"));

            if (jsonObject.has("previewUrl"))
                resultsItem.setPreviewUrl(jsonObject.getString("previewUrl"));

            if (jsonObject.has("artworkUrl100"))
                resultsItem.setArtWorkUrl(jsonObject.getString("artworkUrl100"));

            if (jsonObject.has("collectionPrice"))
                resultsItem.setCollectionPrice(jsonObject.getDouble("collectionPrice"));

            if (jsonObject.has("trackPrice"))
                resultsItem.setTrackPrice(jsonObject.getDouble("trackPrice"));

            if (jsonObject.has("releaseDate"))
                resultsItem.setReleaseDate(jsonObject.getString("releaseDate"));

            if (jsonObject.has("trackTimeMillis"))
                resultsItem.setTrackTimeMillis(jsonObject.getInt("trackTimeMillis"));

            if (jsonObject.has("country"))
                resultsItem.setCountry(jsonObject.getString("country"));

            if (jsonObject.has("currency"))
                resultsItem.setCurrency(jsonObject.getString("currency"));

            if (jsonObject.has("primaryGenreName"))
                resultsItem.setPrimaryGenreName(jsonObject.getString("primaryGenreName"));

            if (jsonObject.has("isStreamable"))
                resultsItem.setStreamable(jsonObject.getBoolean("isStreamable"));

            resultsItems.add(resultsItem);
        }

        response.setResultCount(resultsItems.size());
        response.setResults(resultsItems);
        return response;
    }

}
