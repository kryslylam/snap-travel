package com.lly_lab.snaptravel.storage;

import android.os.AsyncTask;

import com.lly_lab.snaptravel.os.IAsyncTaskResponse;

import org.json.JSONObject;

public class DatabaseReadAsyncTaskResponse implements IAsyncTaskResponse {
    /* for database read task, parse the result into JSON object,
    do corresponding action on the data (i.e. list it on the fragment)
     */
    public void onAsyncTaskCompleted(AsyncTask task, String result) {
        DatabaseOperationResult dbResult=DatabaseOperationResult.JSONtoObj(result);
    }
}
