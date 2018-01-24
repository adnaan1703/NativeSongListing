package com.konel.kryptapps.nativesonglisting.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 23 Jan 2018 3:54 AM
 */


public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private int width;
    private int height;
    @NonNull
    private WeakReference<BitmapLoadedCallBack> callBackWeakReference;

    public ImageDownloader(int width, int height, @NonNull BitmapLoadedCallBack callBack) {
        this.width = width;
        this.height = height;
        this.callBackWeakReference = new WeakReference<>(callBack);
    }

    @Nullable
    private Bitmap getBitmapFromURL(@NonNull String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private Bitmap getResizedBitmap(@Nullable Bitmap bm, int newHeight, int newWidth) {
        if (bm == null)
            return null;

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return getResizedBitmap(getBitmapFromURL(strings[0]), height, width);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (callBackWeakReference.get() != null)
            callBackWeakReference.get().onBitmapLoaded(bitmap);
    }

    public interface BitmapLoadedCallBack {
        void onBitmapLoaded(@Nullable Bitmap bitmap);
    }
}
