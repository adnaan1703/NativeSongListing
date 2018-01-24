package com.konel.kryptapps.nativesonglisting.repository;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Pair;

import com.konel.kryptapps.nativesonglisting.repository.get_songs.GetSongsConfig;
import com.konel.kryptapps.nativesonglisting.repository.get_songs.entity.GetSongsResponse;

import org.json.JSONException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 19 Jan 2018 5:24 PM
 */


public class NetworkManager {


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({GET, POST})
    @interface MethodType {
    }

    public static final String GET = "GET";
    public static final String POST = "POST";


    public static void getSongs(@NonNull String query,
                                int offset,
                                @NonNull final APICallback<GetSongsResponse> callback) {

        final IConfig<GetSongsResponse> config = new GetSongsConfig(query, offset);
        APIHandlerThread.getInstance().makeApiCall(
                config.getMethodType(),
                config.getUrl(),
                new CallCompleteCallback() {
                    @Override
                    public void onCallCompleted(@NonNull Pair<Integer, String> response) {
                        if (response.first != 200) {
                            callback.onError(response.first);
                        } else {
                            try {
                                callback.onSuccess(config.getParser().getParsedModel(response.first, response.second));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                callback.onError(500);
                            }
                        }
                    }
                }
        );
    }

    interface CallCompleteCallback {
        void onCallCompleted(@NonNull Pair<Integer, String> response);
    }


}
