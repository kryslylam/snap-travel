package com.lly_lab.snaptravel.os;

import android.os.AsyncTask;

public interface IAsyncTaskResponse {
    void onAsyncTaskCompleted(AsyncTask task, String result);
}
