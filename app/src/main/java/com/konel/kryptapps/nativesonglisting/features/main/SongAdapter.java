package com.konel.kryptapps.nativesonglisting.features.main;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konel.kryptapps.nativesonglisting.R;
import com.konel.kryptapps.nativesonglisting.data_store.SongsDataStore;
import com.konel.kryptapps.nativesonglisting.features.entity.SongItem;
import com.konel.kryptapps.nativesonglisting.repository.ImageDownloader;

import java.util.List;
import java.util.Locale;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 22 Jan 2018 6:36 PM
 */


class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    @NonNull
    private List<SongItem> songItems;
    @NonNull
    private SongSelectListener listener;
    @NonNull
    private SongsDataStore.DataStoreCallbacks dataStoreCallbacks;
    private boolean stopPagination;

    SongAdapter(@NonNull List<SongItem> songItems,
                @NonNull SongSelectListener listener,
                @NonNull SongsDataStore.DataStoreCallbacks dataStoreCallbacks) {
        this.songItems = songItems;
        this.listener = listener;
        this.dataStoreCallbacks = dataStoreCallbacks;
        this.stopPagination = false;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private static final int OFFSET = 5;
            private RecyclerView.OnScrollListener onScrollListener = this;
            private LinearLayoutManager layoutManager = null;

            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (stopPagination) {
                    recyclerView.removeOnScrollListener(onScrollListener);
                    return;
                }

                if (layoutManager == null)
                    layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (layoutManager.findLastVisibleItemPosition() + OFFSET >= getItemCount())
                    listener.getNext();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.song_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateUI(songItems.get(position));
    }

    @Override
    public int getItemCount() {
        return songItems.size();
    }


    void updateList(@NonNull List<SongItem> songItems) {
        this.songItems.addAll(songItems);
        notifyDataSetChanged();
    }

    void removePagination() {
        this.stopPagination = true;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private ImageView ivSong;
        @NonNull
        private TextView tvName;
        @NonNull
        private TextView tvGenre;
        @NonNull
        private TextView tvArtist;
        @NonNull
        private TextView tvPrice;

        @Nullable
        private SongItem songItem;

        ViewHolder(View itemView) {
            super(itemView);
            ivSong = itemView.findViewById(R.id.ivSong);
            tvName = itemView.findViewById(R.id.tvName);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (songItem != null)
                        listener.onSongSelected(songItem);
                }
            });
        }

        void updateUI(@NonNull final SongItem songItem) {
            this.songItem = songItem;
            tvName.setText(songItem.getTrackName());
            tvGenre.setText(songItem.getPrimaryGenreName());
            tvArtist.setText(songItem.getArtistName());
            tvPrice.setText(String.format(
                    Locale.getDefault(),
                    "%s %.2f",
                    songItem.getCurrency(), songItem.getTrackPrice()
            ));
            if (songItem.getBitmap() != null)
                ivSong.setImageBitmap(songItem.getBitmap());
            else {
                new ImageDownloader(
                        ivSong.getDrawable().getIntrinsicWidth(),
                        ivSong.getDrawable().getIntrinsicHeight(),
                        new ImageDownloader.BitmapLoadedCallBack() {
                            @Override
                            public void onBitmapLoaded(@Nullable Bitmap bitmap) {
                                songItem.setBitmap(bitmap);
                                ivSong.setImageBitmap(bitmap);
                            }
                        }).execute(songItem.getArtWorkUrl());
            }
        }
    }

    interface SongSelectListener {
        void onSongSelected(@NonNull SongItem songItem);

        void getNext();
    }
}
