package com.konel.kryptapps.nativesonglisting.repository;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 19 Jan 2018 5:40 PM
 */


public class ResponseBaseModel {

    private int responseCode;
    private boolean isError;

    public ResponseBaseModel(int responseCode, boolean isError) {
        this.responseCode = responseCode;
        this.isError = isError;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public boolean isError() {
        return isError;
    }
}
