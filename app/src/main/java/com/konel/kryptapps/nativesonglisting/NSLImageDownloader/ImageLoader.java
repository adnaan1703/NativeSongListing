package com.konel.kryptapps.nativesonglisting.NSLImageDownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 26 Jan 2018 5:06 PM
 */

public class ImageLoader {

    private MemoryCache memoryCache;
    private ExecutorService executorService;

    public ImageLoader() {
        memoryCache = new MemoryCache();
        executorService = Executors.newFixedThreadPool(5);
    }

    public void DisplayImage(@NonNull String url,
                             @NonNull ImageLoaderCallback callback) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null) {
            sendBitmapToTarget(url, bitmap, callback);
        } else {
            queuePhoto(url, callback);
        }
    }

    private void queuePhoto(@NonNull String url,
                            @NonNull ImageLoaderCallback callback) {
        PhotoToLoad p = new PhotoToLoad(url, callback);
        executorService.submit(new PhotosLoader(p));
    }

    @Nullable
    private Bitmap getBitmap(@NonNull String url) {
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream input = conn.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class PhotoToLoad {
        @NonNull
        private String url;
        @NonNull
        private ImageLoaderCallback callback;

        PhotoToLoad(@NonNull String url, @NonNull ImageLoaderCallback callback) {
            this.url = url;
            this.callback = callback;
        }
    }

    class PhotosLoader implements Runnable {
        @NonNull
        private PhotoToLoad photoToLoad;

        PhotosLoader(@NonNull PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            Bitmap bmp = getBitmap(photoToLoad.url);
            if (bmp != null) {
                memoryCache.put(photoToLoad.url, bmp);
                sendBitmapToTarget(photoToLoad.url, bmp, photoToLoad.callback);
            }
        }
    }

    private void sendBitmapToTarget(@NonNull String url,
                                    @NonNull Bitmap bmp,
                                    @NonNull ImageLoaderCallback callback) {
        new Handler(Looper.getMainLooper()).post(new ImageCallbackRunner(url, bmp, callback));
    }

    class ImageCallbackRunner implements Runnable {
        @NonNull
        private String url;
        @NonNull
        private Bitmap bitmap;
        @NonNull
        private ImageLoaderCallback callback;

        ImageCallbackRunner(@NonNull String url,
                            @NonNull Bitmap bitmap,
                            @NonNull ImageLoaderCallback callback) {
            this.url = url;
            this.bitmap = bitmap;
            this.callback = callback;
        }

        @MainThread
        @Override
        public void run() {
            callback.onPhotoLoaded(url, bitmap);
        }
    }

    public void clearCache() {
        memoryCache.clear();
    }

    public interface ImageLoaderCallback {
        @MainThread
        void onPhotoLoaded(@NonNull String url, @NonNull Bitmap bitmap);
    }

}
