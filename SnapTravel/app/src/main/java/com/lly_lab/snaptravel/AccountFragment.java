package com.lly_lab.snaptravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.lly_lab.snaptravel.account.FacebookUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class AccountFragment extends BaseFragment {
    private CallbackManager mFbCallbackManager;
    private TextView mFbUserNameTextView;

    private final String ACC_FRAG_DEBUG_TAG="ACCOUNT FRAGMENT";

    public static AccountFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFbCallbackManager= CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mFbUserNameTextView=(TextView) view.findViewById(R.id.facebook_user_name);

        //implement Facebook login button
        LoginButton fbLoginButton=(LoginButton) view.findViewById(R.id.facebook_login_button);
        List<String> perm= Arrays.asList("public_profile","user_friends","email");
        fbLoginButton.setReadPermissions(perm);
        //for using login button inside a fragment
        fbLoginButton.setFragment(this);
        fbLoginButton.registerCallback(mFbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(ACC_FRAG_DEBUG_TAG,"FB Login succeed");
                /*
                //send a graph request to Facebook API to get information about the user
                GraphRequest request=GraphRequest.newMeRequest(
                        //1st param: access token to Facebook API
                        loginResult.getAccessToken(),
                        //2nd param: JSON callback function to receive returned data
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d(ACC_FRAG_DEBUG_TAG,"JSONObject: "+object.toString());
                                Log.d(ACC_FRAG_DEBUG_TAG,"GraphResponse: "+response.toString());
                                try {
                                    String id = object.getString("id");
                                    String name=object.getString("name");
                                    String email=object.getString("email");
                                    MainActivity.mSessionManager.setFbUser(new FacebookUser(id,name,email));
                                    updateFbLoginView();
                                }   catch (JSONException e) {
                                    Log.d(ACC_FRAG_DEBUG_TAG,"JSON Exception: "+e.toString());
                                }
                            }
                        });
                Bundle param=new Bundle();
                param.putString("fields","id,name,email");
                request.setParameters(param);
                request.executeAsync();
                */
            }

            @Override
            public void onCancel() {
                Log.d(ACC_FRAG_DEBUG_TAG,"FB Login cancelled");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(ACC_FRAG_DEBUG_TAG,"FB Login failed - "+error.toString());
                MainActivity.mSessionManager.setFbLogin(false);
            }
        });

        updateFbLoginView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)   {
        //pass the login result to FB callback manager
        mFbCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void updateFbLoginView()  {
        //if Facebook is logined in this session, hide the button, show account info
        //LoginButton fbLoginButton=(LoginButton) getView().findViewById(R.id.facebook_login_button);

        Log.d(ACC_FRAG_DEBUG_TAG,"Update FB Login view called");
        if (MainActivity.mSessionManager.getFbLogin()) {
            Log.d(ACC_FRAG_DEBUG_TAG,"FB logined, updating view");
            mFbUserNameTextView.setVisibility(View.VISIBLE);
            String fbUserName=MainActivity.mSessionManager.getFbUser().getName();
            mFbUserNameTextView.setText(fbUserName);
        } else  {
            mFbUserNameTextView.setVisibility(View.INVISIBLE);
        }
    }
}