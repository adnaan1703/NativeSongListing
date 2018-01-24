package com.konel.kryptapps.nativesonglisting.repository.get_songs;

import android.support.annotation.NonNull;

import com.konel.kryptapps.nativesonglisting.repository.IConfig;
import com.konel.kryptapps.nativesonglisting.repository.NetworkManager;
import com.konel.kryptapps.nativesonglisting.repository.ResponseParser;
import com.konel.kryptapps.nativesonglisting.repository.get_songs.entity.GetSongsResponse;

import java.util.Locale;


/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 19 Jan 2018 6:03 PM
 */


public class GetSongsConfig implements IConfig<GetSongsResponse> {

    private static final String BASE_URL = "http://itunes.apple.com/search?term=%s&limit=%d&offset=%d";
    private static final int LIMIT = 20;

    @NonNull
    private String search;
    private int limit;
    private int offset;

    public GetSongsConfig(@NonNull String search, int offset) {
        this.search = search;
        this.limit = LIMIT;
        this.offset = offset;
    }

    @Override
    public String getMethodType() {
        return NetworkManager.GET;
    }

    @NonNull
    @Override
    public String getUrl() {
        return String.format(Locale.getDefault(), BASE_URL, search, limit, offset);
    }

    @NonNull
    @Override
    public ResponseParser<GetSongsResponse> getParser() {
        return new GetSongsResponseParser();
    }
}
