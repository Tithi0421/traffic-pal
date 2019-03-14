package com.afinal.android.trafficpal1;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by Administrator on 03-May-17.
 */

public class SharedPreferenceUtils {

    private static SharedPreferenceUtils sInstance;

    public SharedPreferenceUtils() {
        // initialization
    }

    public static SharedPreferenceUtils getInstance() {
        if (sInstance == null) {
            sInstance = new SharedPreferenceUtils();
        }
        return sInstance;
    }

    public void setString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, String.valueOf(value));
        editor.apply();
    }

    public String getString(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return settings.getString(key,null);
    }


    public void setLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return settings.getLong(key,-1);
    }

    public void setBoolean(Context context, String key, Boolean value) {
        SharedPreferences settings = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }

    public void logout(Context context) {
        SharedPreferences settings = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }
}