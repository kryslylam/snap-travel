package com.lly_lab.snaptravel.manage;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.lly_lab.snaptravel.AccountFragment;
import com.lly_lab.snaptravel.MainActivity;
import com.lly_lab.snaptravel.R;
import com.lly_lab.snaptravel.account.FacebookUser;
import com.lly_lab.snaptravel.storage.DatabaseOperationResult;
import com.lly_lab.snaptravel.storage.SharedPreferencesHandler;

public class SessionManager {
    /*
    Application settings and data are stored in Shared Preference and managed by this class
     */
    private SharedPreferencesHandler mHandler;
    private FacebookUser mFbUser;
    private AccessTokenTracker mFbAccessTokenTracker;
    private ProfileTracker mFbProfileTracker;

    //all available keys
    private static final String FB_LOGIN="FB_LOGIN";
    private static final String GOOGLE_LOGIN="GOOGLE_LOGIN";

    private final String SESSION_MAN_DEBUG_TAG="SESSION MANAGER";


    public SessionManager(Context context, String prefName)  {
        mHandler=new SharedPreferencesHandler(context, prefName);
        mFbAccessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d(SESSION_MAN_DEBUG_TAG,"FB Access Token changed");
                updateFbLoginValue();
                if (getFbLoginValue() && Profile.getCurrentProfile()==null) {
                    //suppose to be called after first login
                    //if application is reloaded after login, profile would be loaded already
                    mFbProfileTracker=new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.d(SESSION_MAN_DEBUG_TAG,"FB Profile changed, stop tracking, try update user info again");
                            this.stopTracking();
                            updateFbUser();

                            //modify database only if FB is still logged in after token change
                            DatabaseManager.updateFacebookUser(mFbUser);

                            updateAccFragmentFbLoginView();
                        }
                    };
                    //stop the remaining work as profile is still not loaded
                    return;
                }
                updateFbUser();
                updateAccFragmentFbLoginView();
            }
        };
    }

    public void setFbLoginValue(boolean value)   {
        mHandler.setBooleanPref(FB_LOGIN, value);
        Log.d(SESSION_MAN_DEBUG_TAG,"Fb Login set to: "+value);
    }

    public boolean getFbLoginValue() {
        return mHandler.getBooleanPref(FB_LOGIN);
    }

    public boolean updateFbLoginValue()  {
        AccessToken fbAccessToken=AccessToken.getCurrentAccessToken();
        boolean fbLogin;
        if (fbAccessToken==null || fbAccessToken.isExpired())  {
            fbLogin=false;
        }   else    {
            fbLogin=true;
        }
        setFbLoginValue(fbLogin);
        return fbLogin;
    }


    public void updateFbUser()  {
        if (getFbLoginValue()) {
            Profile fbProfile = Profile.getCurrentProfile();
            if (fbProfile==null) {
                Log.d(SESSION_MAN_DEBUG_TAG, "Update FB User called, Profile is null, update failed");
                return;
            }
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
        if (mFbUser==null && getFbLoginValue())
            updateFbUser();
        return mFbUser;
    }

    public void updateAccFragmentFbLoginView()  {
        if (MainActivity.mCurrentTabIndex==MainActivity.INDEX_ACCOUNT) {
            AppCompatActivity act = (AppCompatActivity) mHandler.getContext();
            Fragment frag=act.getSupportFragmentManager().findFragmentById(R.id.container);
            if (frag instanceof AccountFragment)
                ((AccountFragment) frag).updateFbLoginView();
        }
    }

}
