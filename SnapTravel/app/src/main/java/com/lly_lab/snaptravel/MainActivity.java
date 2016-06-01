package com.lly_lab.snaptravel;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

//import com.ncapdevi.fragnav.FragNavController;
import com.lly_lab.snaptravel.lib.FragNavController;
import com.lly_lab.snaptravel.session.SessionManager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation {
    private ActionBar mActionBar;
    private BottomBar mBottomBar;

    public static int mCurrentTabIndex;
    public static FragNavController mNavController;
    public static SessionManager mSessionManager;

    //indice for the tabs
    public final static int INDEX_EXPLORE=FragNavController.TAB1;
    public final static int INDEX_TRIP=FragNavController.TAB2;
    public final static int INDEX_FAVOURITE=FragNavController.TAB3;
    public final static int INDEX_ACCOUNT=FragNavController.TAB4;

    private final String MAIN_ACT_DEBUG_TAG="MAIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragments=new ArrayList<>(4);
        fragments.add(ExploreFragment.newInstance(0));
        fragments.add(TripFragment.newInstance(0));
        fragments.add(FavouriteFragment.newInstance(0));
        fragments.add(AccountFragment.newInstance(0));
        mNavController=new FragNavController(savedInstanceState,getSupportFragmentManager(),R.id.container,fragments);

        String appName=getResources().getString(R.string.app_name);
        mSessionManager=new SessionManager(this,appName);

        createUIComponent(savedInstanceState);
    }

    //Build the framework of UI (tool bar and bottom bar)
    public void createUIComponent(Bundle savedInstanceState)   {
        //Create tool bar widget and set as action bar
        Toolbar toolBar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);
        mActionBar=getSupportActionBar();
        mActionBar.setTitle(R.string.app_name);

        //Create bottom bar and attach
        mCurrentTabIndex=INDEX_EXPLORE;
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottom_bar, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.bottom_bar_explore:
                        mCurrentTabIndex=INDEX_EXPLORE;
                        mNavController.switchTab(INDEX_EXPLORE);
                        break;
                    case R.id.bottom_bar_trip:
                        mCurrentTabIndex=INDEX_TRIP;
                        mNavController.switchTab(INDEX_TRIP);
                        break;
                    case R.id.bottom_bar_favourite:
                        mCurrentTabIndex=INDEX_FAVOURITE;
                        mNavController.switchTab(INDEX_FAVOURITE);
                        break;
                    case R.id.bottom_bar_account:
                        mCurrentTabIndex=INDEX_ACCOUNT;
                        mNavController.switchTab(INDEX_ACCOUNT);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                mNavController.clearStack();
            }
        });
        //Set colors for different tabs
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, "#5D4037");
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
    }


    @Override
    public void onBackPressed() {
        if (mNavController.getCurrentStack().size()>1)  {
            mNavController.pop();
        }   else    {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mNavController.onSaveInstanceState(outState);
    }

    @Override
    public void pushFragment(Fragment fragment) {
        mNavController.push(fragment);
    }

}
