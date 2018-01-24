package com.konel.kryptapps.nativesonglisting.repository;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 19 Jan 2018 5:10 PM
 */


class APIHandlerThread extends HandlerThread {

    private static final String USER_AGENT = "User-Agent";
    private static final String USER_AGENT_VALUE = "Mozilla/5.0";
    @NonNull
    private Handler apiHandler;
    @NonNull
    private Handler mainHandler;

    private static APIHandlerThread instance;

    static APIHandlerThread getInstance() {
        if (instance == null)
            instance = new APIHandlerThread();
        return instance;
    }

    private APIHandlerThread() {
        super(APIHandlerThread.class.getSimpleName(), Process.THREAD_PRIORITY_URGENT_DISPLAY);
        start();
        this.apiHandler = new Handler(getLooper());
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    void makeApiCall(@NetworkManager.MethodType final String type,
                     @NonNull final String urlStr,
                     @NonNull final NetworkManager.CallCompleteCallback callback) {

        apiHandler.post(new Runnable() {
            Pair<Integer, String> response;

            @Override
            public void run() {
                try {
                    response = apiCall(type, urlStr);
                } catch (IOException e) {
                    e.printStackTrace();
                    response = Pair.create(500, "");
                } finally {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onCallCompleted(response);
                        }
                    });
                }
            }
        });
    }


    private Pair<Integer, String> apiCall(@NetworkManager.MethodType String type, @NonNull String urlStr) throws IOException {

        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(type);
        connection.setRequestProperty(USER_AGENT, USER_AGENT_VALUE);
        int responseCode = connection.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }

        br.close();
        Log.d(getClass().getSimpleName(), response.toString());
        return Pair.create(responseCode, response.toString());
    }

}
