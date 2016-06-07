package com.lly_lab.snaptravel.manage;

import com.lly_lab.snaptravel.account.FacebookUser;
import com.lly_lab.snaptravel.storage.DatabaseAsyncTask;
import com.lly_lab.snaptravel.storage.DatabaseOperationResult;
import com.lly_lab.snaptravel.storage.DatabaseWriteAsyncTaskResponse;

import java.util.ArrayList;
import java.util.LinkedList;

public class DatabaseManager {
    private static LinkedList<DatabaseAsyncTask> dbTaskList=new LinkedList<>();
    private final static String UPDATE_FB_USER_FTN ="updateFbUser";

    public static void addTask(DatabaseAsyncTask dbTask)    {
        dbTaskList.push(dbTask);

        //!!!need to consider how to save the param values
        //!!!need to save the tasks if the next operation
        //!!!retry if internet is connected again
    }

    public static void updateFacebookUser(FacebookUser fbUser)  {
        ArrayList<String> paramNameList=new ArrayList<>();
        paramNameList.add("function");
        paramNameList.add("fbUserID");
        paramNameList.add("fbUserName");

        DatabaseAsyncTask dbTask = new DatabaseAsyncTask(DatabaseAsyncTask.TaskType.WRITE, paramNameList);

        if (DeviceManager.isNetworkConnected()) {
            dbTask.execute(UPDATE_FB_USER_FTN, fbUser.getId(), fbUser.getName());
        }   else    {
            DatabaseOperationResult result=new DatabaseOperationResult(UPDATE_FB_USER_FTN,"SERVER_CONN_ERROR","No Internet connectivity");
            new DatabaseWriteAsyncTaskResponse().onAsyncTaskCompleted(dbTask, result.toJSON());
        }
    }
}
