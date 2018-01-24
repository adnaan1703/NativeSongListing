package com.konel.kryptapps.nativesonglisting.features.details;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.konel.kryptapps.nativesonglisting.NavigationUtil;
import com.konel.kryptapps.nativesonglisting.R;
import com.konel.kryptapps.nativesonglisting.repository.ImageDownloader;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    private ImageView ivSong;
    private TextView tvName;
    private TextView tvArtist;
    private TextView tvGenre;
    private TextView tvPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ivSong = findViewById(R.id.ivSong);
        tvName = findViewById(R.id.tvName);
        tvArtist = findViewById(R.id.tvArtist);
        tvGenre = findViewById(R.id.tvGenre);
        tvPrice = findViewById(R.id.tvPrice);

        generateView((DetailsActivityModel) getIntent().getParcelableExtra(NavigationUtil.KEY_DATA));
    }

    private void generateView(@NonNull DetailsActivityModel model) {
        tvName.setText(model.getTrackName());
        tvArtist.setText(model.getArtistName());
        tvGenre.setText(model.getPrimaryGenreName());
        tvPrice.setText(String.format(
                Locale.getDefault(),
                "%s %.2f",
                model.getCurrency(), model.getTrackPrice()
        ));
        new ImageDownloader(
                ivSong.getDrawable().getIntrinsicWidth(),
                ivSong.getDrawable().getIntrinsicHeight(),
                new ImageDownloader.BitmapLoadedCallBack() {
                    @Override
                    public void onBitmapLoaded(@Nullable Bitmap bitmap) {
                        ivSong.setImageBitmap(bitmap);
                    }
                }
        ).execute(model.getArtWorkUrl());
    }
}
