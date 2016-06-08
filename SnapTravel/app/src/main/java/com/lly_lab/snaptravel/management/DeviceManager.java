package com.lly_lab.snaptravel.management;

import android.net.NetworkInfo;

import com.lly_lab.snaptravel.MainActivity;

public class DeviceManager {
    public static boolean isNetworkConnected()    {
        NetworkInfo networkInfo= MainActivity.mConnManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected())
            return true;
        else return false;
    }
}
