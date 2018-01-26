package com.konel.kryptapps.nativesonglisting.NSLImageDownloader;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 26 Jan 2018 5:08 PM
 */

class MemoryCache {

    private static final String TAG = "MemoryCache";

    private long size;
    private long limit;
    private Map<String, Bitmap> cache;

    MemoryCache() {
        size = 0;
        limit = Runtime.getRuntime().maxMemory() / 4;
        cache = Collections.synchronizedMap(
                new LinkedHashMap<String, Bitmap>(
                        10,
                        1.5f,
                        true
                ));
        Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");

    }

    @Nullable
    Bitmap get(@NonNull String id) {
        if (cache.containsKey(id))
            return cache.get(id);
        return null;
    }

    void put(@NonNull String id, @NonNull Bitmap bitmap) {

        if (cache.containsKey(id))
            size -= getSizeInBytes(cache.get(id));
        cache.put(id, bitmap);
        size += getSizeInBytes(bitmap);
        checkSize();
    }

    private void checkSize() {
        Log.i(TAG, "cache size=" + size + " length=" + cache.size());
        if (size > limit) {
            Iterator<Map.Entry<String, Bitmap>> itr = cache.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Bitmap> entry = itr.next();
                size -= getSizeInBytes(entry.getValue());
                itr.remove();
                if (size <= limit)
                    break;
            }
            Log.i(TAG, "Clean cache. New size " + cache.size());
        }
    }

    void clear() {
        cache.clear();
        size = 0;
    }

    private long getSizeInBytes(@Nullable Bitmap bitmap) {
        if (bitmap == null)
            return 0;
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
