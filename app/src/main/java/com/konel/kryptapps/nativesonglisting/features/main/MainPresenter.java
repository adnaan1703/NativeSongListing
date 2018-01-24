package com.konel.kryptapps.nativesonglisting.features.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.konel.kryptapps.nativesonglisting.NavigationUtil;
import com.konel.kryptapps.nativesonglisting.data_store.SongsDataStore;
import com.konel.kryptapps.nativesonglisting.features.details.DetailsActivityModel;
import com.konel.kryptapps.nativesonglisting.features.entity.SongItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 22 Jan 2018 6:54 PM
 */


class MainPresenter implements MainContract.Presenter {

    @NonNull
    private WeakReference<MainContract.View> viewWeakReference;
    @NonNull
    private NavigationUtil navigationUtil;
    @NonNull
    private List<SongItem> songItems;
    @Nullable
    private SongsDataStore songsDataStore;

    MainPresenter(@NonNull WeakReference<MainContract.View> viewWeakReference,
                  @NonNull NavigationUtil navigationUtil) {
        this.viewWeakReference = viewWeakReference;
        this.navigationUtil = navigationUtil;
        this.songItems = new ArrayList<>();
        this.songsDataStore = null;
    }

    @Override
    public void onStart() {
        if (!checkNotNull())
            return;

        if (songItems.size() == 0) viewWeakReference.get().showEmptyState();
        else viewWeakReference.get().showSongs(songItems);
    }

    @Override
    public void onQuery(@NonNull String query) {
        if (!checkNotNull())
            return;

        viewWeakReference.get().clearSongs();
        songItems.clear();
        viewWeakReference.get().showEmptyState();
        songsDataStore = new SongsDataStore(query);
        songsDataStore.getData(this);
    }

    @Override
    public void onSongSelected(@NonNull SongItem songItem) {
        navigationUtil.startDetailsActivity(new DetailsActivityModel(
                songItem.getArtWorkUrl(),
                songItem.getTrackName(),
                songItem.getPrimaryGenreName(),
                songItem.getArtistName(),
                songItem.getCurrency(),
                songItem.getTrackPrice()
        ));
    }

    @Override
    public void getNext() {
        if (songsDataStore != null)
            songsDataStore.getData(this);
    }

    @Override
    public void onDataReceived(@NonNull List<SongItem> songItems) {
        if (checkNotNull()) {
            this.songItems.addAll(songItems);
            viewWeakReference.get().showSongs(songItems);
        }
    }

    @Override
    public void onDataExhausted() {
        if (checkNotNull())
            viewWeakReference.get().stopPagination();
    }


    private boolean checkNotNull() {
        return viewWeakReference.get() != null;
    }

}
