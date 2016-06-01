package com.lly_lab.snaptravel;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class SnapTravelApplication extends Application {
    @Override
    public void onCreate()  {
        super.onCreate();

        //initialize Facebook SDK before executing any operations
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
