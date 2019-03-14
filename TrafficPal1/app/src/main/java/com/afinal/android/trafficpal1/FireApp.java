package com.afinal.android.trafficpal1;

/**
 * Created by DHIREN on 01-02-2018.
 */

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Tithi on 25-06-2017.
 */
public class FireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}