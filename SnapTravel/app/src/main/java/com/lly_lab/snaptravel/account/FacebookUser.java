package com.lly_lab.snaptravel.account;

import android.util.Log;

public class FacebookUser {
    private String mId;
    private String mName;
    private String mEmail;

    private final String FB_USER_DEBUG_TAG="FB USER";

    public FacebookUser(String id,String name)  {
        mId=id;
        mName=name;
        Log.d(FB_USER_DEBUG_TAG,"FB User created: "+id+","+name);
    }

    public FacebookUser(String id,String name,String email) {
        mId=id;
        mName=name;
        mEmail=email;
        Log.d(FB_USER_DEBUG_TAG,"FB User created: "+id+","+name+","+email);
    }

    public String getId()   {
        return mId;
    }

    public String getName() {
        return mName;
    }


}
