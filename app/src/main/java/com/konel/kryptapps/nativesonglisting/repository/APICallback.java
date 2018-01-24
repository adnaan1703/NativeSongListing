package com.konel.kryptapps.nativesonglisting.repository;

import android.support.annotation.NonNull;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 19 Jan 2018 5:44 PM
 */


public interface APICallback<T extends ResponseBaseModel> {
    void onSuccess(@NonNull T response);

    void onError(int statusCode);
}
