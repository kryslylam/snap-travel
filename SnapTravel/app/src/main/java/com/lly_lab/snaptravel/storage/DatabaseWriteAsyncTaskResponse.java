package com.lly_lab.snaptravel.storage;

import android.os.AsyncTask;

import com.lly_lab.snaptravel.os.IAsyncTaskResponse;

import org.json.JSONObject;

public class DatabaseWriteAsyncTaskResponse implements IAsyncTaskResponse {
    /* for database write task, parse the result into JSON object
    notify user about the operation result (success / failure)
     */
    public void onAsyncTaskCompleted(AsyncTask task, String result) {
        DatabaseOperationResult dbResult=DatabaseOperationResult.JSONtoObj(result);
    }
}
