package com.lly_lab.snaptravel.session;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.lly_lab.snaptravel.AccountFragment;
import com.lly_lab.snaptravel.MainActivity;
import com.lly_lab.snaptravel.R;
import com.lly_lab.snaptravel.account.FacebookUser;
import com.lly_lab.snaptravel.storage.SharedPreferencesHandler;

public class SessionManager {
    /*
    Application settings and data are stored in Shared Preference and managed by this class
     */
    private SharedPreferencesHandler mHandler;
    private FacebookUser mFbUser;
    private AccessTokenTracker mFbAccessTokenTracker;

    //all available keys
    private static final String FB_LOGIN="FB_LOGIN";
    private static final String GOOGLE_LOGIN="GOOGLE_LOGIN";

    private final String SESSION_MAN_DEBUG_TAG="SESSION MANAGER";


    public SessionManager(Context context, String prefName)  {
        mHandler=new SharedPreferencesHandler(context, prefName);
        mFbAccessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d(SESSION_MAN_DEBUG_TAG,"FB Access Token Changed");
                updateFbLogin();
                if (MainActivity.mCurrentTabIndex==MainActivity.INDEX_ACCOUNT) {
                    AppCompatActivity act = (AppCompatActivity) mHandler.getContext();
                    Fragment frag=act.getSupportFragmentManager().findFragmentById(R.id.container);
                    if (frag instanceof AccountFragment)
                        ((AccountFragment) frag).updateFbLoginView();
                }
            }
        };
        Log.d(SESSION_MAN_DEBUG_TAG,"Session Manager created");
    }

    public void setFbLogin(boolean value)   {
        mHandler.setBooleanPref(FB_LOGIN, value);
        Log.d(SESSION_MAN_DEBUG_TAG,"Fb Login set to: "+value);
    }

    public boolean updateFbLogin()  {
        AccessToken fbAccessToken=AccessToken.getCurrentAccessToken();
        boolean fbLogin;
        if (fbAccessToken==null || fbAccessToken.isExpired())  {
            fbLogin=false;
        }   else    {
            fbLogin=true;
        }
        setFbLogin(fbLogin);
        updateFbUser();
        return fbLogin;
    }

    public boolean getFbLogin() {
        return mHandler.getBooleanPref(FB_LOGIN);
    }

    public void setFbUser(FacebookUser fbUser) {
        mFbUser=fbUser;
    }

    public void updateFbUser()  {
        if (getFbLogin()) {
            Profile fbProfile = Profile.getCurrentProfile();
            if (fbProfile==null)
                Log.d(SESSION_MAN_DEBUG_TAG,"Update FB User called, Profile is null");
            Log.d(SESSION_MAN_DEBUG_TAG, "Update FB User called, Profile: " + fbProfile.toString());
            String fbCurrentId = fbProfile.getId();
            if (mFbUser == null || mFbUser.getId() != fbCurrentId) {
                mFbUser = new FacebookUser(fbCurrentId, fbProfile.getName());
            }
        }   else    {
            mFbUser=null;
        }
    }

    public FacebookUser getFbUser() {
        if (mFbUser==null && getFbLogin())
            updateFbUser();
        return mFbUser;
    }

}
