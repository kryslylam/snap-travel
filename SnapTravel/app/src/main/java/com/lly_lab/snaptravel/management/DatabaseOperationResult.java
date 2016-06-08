package com.lly_lab.snaptravel.management;

import android.util.Log;

import org.json.JSONObject;

public class DatabaseOperationResult {
    private String mTaskName;
    private QueryStatus mQueryStatus; //"SUCCESS"/"FAILURE"
    private String mQueryError;
    private String mQueryErrorMsg;
    private String mResult; //result of the query (in JSON string)

    private static enum QueryStatus   {
        SUCCESS,FAILURE
    }

    private final static String TASK_NAME_IN_JSONOBJ="task_name";
    private final static String QUERY_STATUS_IN_JSONOBJ="query_status";
    private final static String QUERY_ERROR_IN_JSONOBJ="query_error";
    private final static String QUERY_ERROR_MSG_IN_JSONOBJ="query_error_msg";
    private final static String RESULT_IN_JSONOBJ="result";

    private final static String DB_OPS_RESULT_DEBUG_TAG="DB_OPS_RESULT";

    public DatabaseOperationResult(String taskName) {
        mTaskName=taskName;
        mQueryStatus=QueryStatus.SUCCESS;
        mQueryError="";
        mQueryErrorMsg="";
        mResult="";
    }

    public DatabaseOperationResult(String taskName, String result)  {
        mTaskName=taskName;
        mQueryStatus=QueryStatus.SUCCESS;
        mQueryError="";
        mQueryErrorMsg="";
        mResult=result;
    }

    public DatabaseOperationResult(String taskName,String queryError,String queryErrorMsg)  {
        mTaskName=taskName;
        mQueryStatus=QueryStatus.FAILURE;
        mQueryError=queryError;
        mQueryErrorMsg=queryErrorMsg;
        mResult="";
    }

    public DatabaseOperationResult(String taskName,String queryError,String queryErrorMsg,String result)  {
        mTaskName=taskName;
        mQueryStatus=QueryStatus.FAILURE;
        mQueryError=queryError;
        mQueryErrorMsg=queryErrorMsg;
        mResult=result;
    }

    public boolean isSuccessful()   {
        if (mQueryStatus==QueryStatus.SUCCESS)
            return true;
        else return false;
    }

    public String toJSON()  {
        try {
            JSONObject jsonObj=new JSONObject();
            jsonObj.put(TASK_NAME_IN_JSONOBJ,mTaskName);
            jsonObj.put(QUERY_STATUS_IN_JSONOBJ,queryStatusToString(mQueryStatus));
            jsonObj.put(QUERY_ERROR_IN_JSONOBJ,mQueryError);
            jsonObj.put(QUERY_ERROR_MSG_IN_JSONOBJ,mQueryErrorMsg);
            jsonObj.put(RESULT_IN_JSONOBJ,mResult);
            return jsonObj.toString();
        } catch (Exception e)   {
            Log.e(DB_OPS_RESULT_DEBUG_TAG,e.toString());
            return null;
        }
    }

    public static DatabaseOperationResult JSONtoObj(String jsonStrWithTaskName) {
        try {
            JSONObject jsonObj=new JSONObject(jsonStrWithTaskName);
            String taskName=jsonObj.getString(TASK_NAME_IN_JSONOBJ);
            return JSONtoObj(taskName,jsonStrWithTaskName);
        } catch (Exception e)   {
            Log.e(DB_OPS_RESULT_DEBUG_TAG,e.toString());
            return null;
        }
    }

    public static DatabaseOperationResult JSONtoObj(String taskName, String jsonStr)   {
        try {
            DatabaseOperationResult dbResult;
            JSONObject jsonObj=new JSONObject(jsonStr);
            String queryStatus=jsonObj.getString(QUERY_STATUS_IN_JSONOBJ);
            if (queryStatus.equals("SUCCESS"))  {
                if (jsonObj.has(RESULT_IN_JSONOBJ)) {
                    String result=jsonObj.getString(RESULT_IN_JSONOBJ);
                    dbResult=new DatabaseOperationResult(taskName, result);
                }   else    {
                    dbResult=new DatabaseOperationResult(taskName);
                }
            }   else    {
                String queryError=jsonObj.getString(QUERY_ERROR_IN_JSONOBJ);
                String queryErrorMsg=jsonObj.getString(QUERY_ERROR_MSG_IN_JSONOBJ);
                if (jsonObj.has(RESULT_IN_JSONOBJ)) {
                    String result=jsonObj.getString(RESULT_IN_JSONOBJ);
                    dbResult=new DatabaseOperationResult(taskName,queryError,queryErrorMsg,result);
                }   else    {
                    dbResult=new DatabaseOperationResult(taskName,queryError,queryErrorMsg);
                }
            }
            return dbResult;
        } catch (Exception e)   {
            Log.e(DB_OPS_RESULT_DEBUG_TAG,e.toString());
            return null;
        }
    }


    public static String queryStatusToString(QueryStatus status)    {
        switch (status) {
            case SUCCESS:
                return "SUCCESS";
            case FAILURE:
                return "FAILURE";
            default:
                return null;
        }
    }
}
