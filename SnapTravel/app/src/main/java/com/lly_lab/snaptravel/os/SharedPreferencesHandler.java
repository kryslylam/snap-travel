package com.lly_lab.snaptravel.os;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class SharedPreferencesHandler {
    private String mPreferenceName;
    private Context mContext;

    private static String LOG_TAG ="SHARED PREFERENCE";

    public SharedPreferencesHandler(Context context,String name)  {
        mPreferenceName=name;
        mContext=context;
    }

    public Context getContext() {
        return mContext;
    }

    public void setStringPref(String key, String value) {
        SharedPreferences.Editor editor=mContext.getSharedPreferences(mPreferenceName,Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();
    }

    public String getStringPref(String key)   {
        SharedPreferences sharedPref=mContext.getSharedPreferences(mPreferenceName,Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");
    }

    public void setBooleanPref(String key, Boolean value) {
        SharedPreferences.Editor editor=mContext.getSharedPreferences(mPreferenceName,Context.MODE_PRIVATE).edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean getBooleanPref(String key)   {
        SharedPreferences sharedPref=mContext.getSharedPreferences(mPreferenceName,Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key,false);
    }

    public boolean hasKey(String key)   {
        SharedPreferences sharedPref=mContext.getSharedPreferences(mPreferenceName,Context.MODE_PRIVATE);
        return sharedPref.contains(key);
    }

    public void printAll(Context context)  {
        SharedPreferences sharedPref=context.getSharedPreferences(mPreferenceName,Context.MODE_PRIVATE);
        Map<String,?> sharedPrefKeyValue=sharedPref.getAll();
        for(Map.Entry<String,?> entry: sharedPrefKeyValue.entrySet())   {
            Log.d(LOG_TAG,entry.getKey()+": "+entry.getValue().toString());
        }
    }
}
