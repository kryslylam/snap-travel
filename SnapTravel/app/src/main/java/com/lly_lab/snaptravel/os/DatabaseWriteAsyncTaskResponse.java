package com.lly_lab.snaptravel.os;

import android.os.AsyncTask;

import com.lly_lab.snaptravel.management.DatabaseOperationResult;
import com.lly_lab.snaptravel.os.IAsyncTaskResponse;

public class DatabaseWriteAsyncTaskResponse implements IAsyncTaskResponse {
    /* for database write task, parse the result into JSON object
    notify user about the operation result (success / failure)
     */
    public void onAsyncTaskCompleted(AsyncTask task, String result) {
        DatabaseOperationResult dbResult=DatabaseOperationResult.JSONtoObj(result);
    }
}
