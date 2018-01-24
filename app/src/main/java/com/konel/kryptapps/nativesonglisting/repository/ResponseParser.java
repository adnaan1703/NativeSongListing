package com.konel.kryptapps.nativesonglisting.repository;

import android.support.annotation.NonNull;

import org.json.JSONException;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 19 Jan 2018 5:42 PM
 */


public interface ResponseParser<T extends ResponseBaseModel> {

    @NonNull
    T getParsedModel(int statusCode, @NonNull String jsonStr) throws JSONException;
}
