package com.konel.kryptapps.nativesonglisting;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.konel.kryptapps.nativesonglisting.features.details.DetailsActivity;
import com.konel.kryptapps.nativesonglisting.features.details.DetailsActivityModel;

/**
 * @author : Adnaan 'Zohran' Ahmed <adnaanahmed@urbanclap.com>
 * @version : 1.0.0
 * @since : 22 Jan 2018 8:40 PM
 */


public class NavigationUtil {
    public static final String KEY_DATA = "key_data";
    @NonNull
    private Context context;

    public NavigationUtil(@NonNull Context context) {
        this.context = context;
    }

    public void startDetailsActivity(@NonNull DetailsActivityModel detailsActivityModel) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(KEY_DATA, detailsActivityModel);
        context.startActivity(intent);
    }
}
