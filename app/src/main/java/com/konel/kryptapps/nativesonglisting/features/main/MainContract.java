package com.konel.kryptapps.nativesonglisting.features.main;

import android.support.annotation.NonNull;

import com.konel.kryptapps.nativesonglisting.data_store.SongsDataStore;
import com.konel.kryptapps.nativesonglisting.features.entity.SongItem;

import java.util.List;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 22 Jan 2018 6:31 PM
 */


interface MainContract {
    interface Presenter extends SongAdapter.SongSelectListener, SongsDataStore.DataStoreCallbacks {
        void onStart();

        void onQuery(@NonNull String query);

        void onSongSelected(@NonNull SongItem songItem);
    }

    interface View {
        void showSongs(@NonNull List<SongItem> songItems);

        void stopPagination();

        void showEmptyState();

        void clearSongs();

    }
}
