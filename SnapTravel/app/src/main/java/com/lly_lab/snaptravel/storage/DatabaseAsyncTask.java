package com.lly_lab.snaptravel.storage;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.lly_lab.snaptravel.os.IAsyncTaskResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DatabaseAsyncTask extends AsyncTask<String,Void,String> {
    private TaskType mTaskType;
    private ArrayList<String> mParamNameList;
    private String mTaskName;

    public IAsyncTaskResponse mAsyncTaskResponse;

    private final String DB_ASYNC_TASK_DEBUG_TAG="DB ASYNC TASK";
    public final String DB_TASK_FAIL_MSG="FAILURE";

    private static final int TIMEOUT=15000;
    private static final String CHAR_ENCODE="UTF-8";
    private static final String SERVER_URL="http://snaptravel.royalwebhosting.net/QueryHandler.php";

    public static enum TaskType    {
        READ, WRITE, READ_WRITE
    }

    public DatabaseAsyncTask(TaskType taskType) {
        mTaskType=taskType;
        mParamNameList=new ArrayList<>();
        if (taskType==TaskType.READ)
            mAsyncTaskResponse=new DatabaseReadAsyncTaskResponse();
        else mAsyncTaskResponse=new DatabaseWriteAsyncTaskResponse();
    }

    public DatabaseAsyncTask(TaskType taskType, ArrayList<String> paramNameList) {
        mTaskType=taskType;
        mParamNameList =paramNameList;
        if (taskType==TaskType.READ)
            mAsyncTaskResponse=new DatabaseReadAsyncTaskResponse();
        else mAsyncTaskResponse=new DatabaseWriteAsyncTaskResponse();
    }

    /*called when the task is executed*/
    @Override
    protected String doInBackground(String... params)   {
        try {
            //save the first param (function name) for identification
            mTaskName=params[0];

            //open connection
            URL url=new URL(SERVER_URL);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();

            //set request properties
            conn.setRequestMethod("POST");
            conn.setReadTimeout(TIMEOUT);
            conn.setConnectTimeout(TIMEOUT);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //convert the request params into string, format similar to GET request
            //paramName1=paramValue1&paramName2=paramValue2...
            Uri.Builder builder=new Uri.Builder();
            for (int i=0;i<mParamNameList.size();i++) {
                builder.appendQueryParameter(mParamNameList.get(i),params[i]);
            }
            String queryParam=builder.build().getEncodedQuery();

            //write the params in OutputStream so the data could be send along with the request
            OutputStream os=conn.getOutputStream();
            BufferedWriter writer=new BufferedWriter(
                    new OutputStreamWriter(os,CHAR_ENCODE));
            writer.write(queryParam);
            writer.flush();
            writer.close();
            os.close();

            //read the result and return
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result="",line;
            while ((line=reader.readLine())!=null)  {
                result+=line;
            }
            return result;
        } catch (Exception e)   {
            DatabaseOperationResult dbFailureResult=new DatabaseOperationResult(mTaskName,"SERVER_CONN_ERROR",e.toString());
            return dbFailureResult.toJSON();
        }
    }

    /*called after doInBackground method*/
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d(DB_ASYNC_TASK_DEBUG_TAG,"DB task '"+mTaskName+"' executed");
        Log.d(DB_ASYNC_TASK_DEBUG_TAG,"Result= "+result);

        //read task: parse query result to JSON object, handle data
        //write take: notify user about the database operation result

        //update the result object to include task name
        DatabaseOperationResult updatedResult=DatabaseOperationResult.JSONtoObj(mTaskName,result);

        mAsyncTaskResponse.onAsyncTaskCompleted(this, updatedResult.toJSON());
    }
}
