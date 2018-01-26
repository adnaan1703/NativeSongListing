package com.konel.kryptapps.nativesonglisting.features.main;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konel.kryptapps.nativesonglisting.NSLImageDownloader.ImageLoader;
import com.konel.kryptapps.nativesonglisting.R;
import com.konel.kryptapps.nativesonglisting.features.entity.SongItem;

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
    private ImageLoader imageLoader;
    private boolean stopPagination;

    SongAdapter(@NonNull List<SongItem> songItems,
                @NonNull SongSelectListener listener,
                @NonNull ImageLoader imageLoader) {
        this.songItems = songItems;
        this.listener = listener;
        this.imageLoader = imageLoader;
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

    class ViewHolder extends RecyclerView.ViewHolder implements ImageLoader.ImageLoaderCallback {

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
            ivSong.setImageDrawable(null);
            tvName.setText(songItem.getTrackName());
            tvGenre.setText(songItem.getPrimaryGenreName());
            tvArtist.setText(songItem.getArtistName());
            tvPrice.setText(String.format(
                    Locale.getDefault(),
                    "%s %.2f",
                    songItem.getCurrency(), songItem.getTrackPrice()
            ));
            imageLoader.DisplayImage(songItem.getArtWorkUrl(), this);
        }

        @Override
        public void onPhotoLoaded(@NonNull String url, @NonNull Bitmap bitmap) {
            if (getAdapterPosition() >= 0
                    && getAdapterPosition() < songItems.size()
                    && songItems.get(getAdapterPosition()).getArtWorkUrl().equalsIgnoreCase(url))
                if (ivSong.getDrawable() != null
                        && ivSong.getDrawable() instanceof BitmapDrawable
                        && ((BitmapDrawable) ivSong.getDrawable()).getBitmap().equals(bitmap)) {
                    return;
                }
            ivSong.setImageBitmap(bitmap);
        }
    }

    interface SongSelectListener {
        void onSongSelected(@NonNull SongItem songItem);

        void getNext();
    }
}
