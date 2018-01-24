package com.konel.kryptapps.nativesonglisting.repository;

import android.support.annotation.NonNull;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 19 Jan 2018 5:37 PM
 */


public interface IConfig<T extends ResponseBaseModel> {
    @NetworkManager.MethodType
    String getMethodType();

    @NonNull
    String getUrl();

    @NonNull
    ResponseParser<T> getParser();
}
