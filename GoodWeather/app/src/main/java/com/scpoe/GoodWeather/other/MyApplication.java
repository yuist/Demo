package com.scpoe.GoodWeather.other;

import android.app.Application;
import android.content.Context;

import com.thinkland.sdk.android.JuheSDKInitializer;


public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        JuheSDKInitializer.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}