package com.konel.kryptapps.nativesonglisting.data_store;

import android.support.annotation.NonNull;

import com.konel.kryptapps.nativesonglisting.features.entity.SongItem;
import com.konel.kryptapps.nativesonglisting.repository.APICallback;
import com.konel.kryptapps.nativesonglisting.repository.NetworkManager;
import com.konel.kryptapps.nativesonglisting.repository.get_songs.entity.GetSongsResponse;
import com.konel.kryptapps.nativesonglisting.repository.get_songs.entity.ResultsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 22 Jan 2018 6:58 PM
 */


public class SongsDataStore {
    private static final int OFFSET_UNIT = 20;

    @NonNull
    private String query;
    private int page;
    private boolean hasNext;

    public SongsDataStore(@NonNull String query) {
        this.query = query;
        this.page = 0;
        this.hasNext = true;
    }

    public void getData(@NonNull final DataStoreCallbacks callbacks) {
        if (hasNext) {
            NetworkManager.getSongs(query, page * OFFSET_UNIT, new APICallback<GetSongsResponse>() {
                @Override
                public void onSuccess(@NonNull GetSongsResponse response) {
                    if (response.getResultCount() == 0) {
                        hasNext = false;
                        callbacks.onDataExhausted();
                    } else {
                        callbacks.onDataReceived(transmute(response.getResults()));
                        page++;
                    }
                }

                @Override
                public void onError(int statusCode) {
                    hasNext = false;
                    callbacks.onDataExhausted();
                }
            });
        }
    }

    @NonNull
    private List<SongItem> transmute(List<ResultsItem> results) {
        List<SongItem> songItems = new ArrayList<>();
        for (ResultsItem resultsItem : results) {
            songItems.add(new SongItem(
                    resultsItem.getArtWorkUrl(),
                    resultsItem.getTrackName(),
                    resultsItem.getPrimaryGenreName(),
                    resultsItem.getArtistName(),
                    resultsItem.getCurrency(),
                    resultsItem.getTrackPrice()
            ));
        }
        return songItems;
    }


    public interface DataStoreCallbacks {
        void onDataReceived(@NonNull List<SongItem> songItems);

        void onDataExhausted();
    }

}
