package com.lly_lab.snaptravel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lly_lab.snaptravel.trip.Trip;

public class MyTripDetailFragment extends BaseFragment {
    private Trip mTrip;

    private static String TRIP_SERIALIZE="com.lly_lab.snaptravel.MyTripDetailFragment.serializedTrip";
    private static String LOG_TAG="MY TRIP DETAIL FRAGMENT";

    public static MyTripDetailFragment newInstance(int instance, Trip trip) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        //serialize the object to put in bundle
        String jsonStr=Trip.toJSONUsingGSON(trip);
        args.putString(TRIP_SERIALIZE,jsonStr);
        Log.d(LOG_TAG,"Trip serialized in JSON");
        Log.d(LOG_TAG,jsonStr);

        MyTripDetailFragment fragment = new MyTripDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_trip_detail, container, false);
        //set action bar title
        getActionBar().setTitle(R.string.app_name);
        //to receive calls and handle option menu
        setHasOptionsMenu(true);

        deserializeTrip();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_my_trip_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_trip_add_item:
                // add trip item
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void deserializeTrip()   {
        if (mTrip==null)    {
            String jsonStr=getArguments().getString(TRIP_SERIALIZE);
            mTrip=Trip.JSONtoObjUsingGSON(jsonStr);
        }
    }
}