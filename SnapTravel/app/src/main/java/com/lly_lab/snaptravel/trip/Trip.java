package com.lly_lab.snaptravel.trip;

import android.hardware.camera2.params.Face;

import com.google.gson.Gson;
import com.lly_lab.snaptravel.account.FacebookUser;
import com.lly_lab.snaptravel.crosscountryadaption.City;

import java.util.ArrayList;

public class Trip {
    private int mTripID;
    private String mStartDate;
    private String mEndDate;
    private City mDestination;
    private ArrayList<FacebookUser> mTripMembers;
    private ArrayList<Tag> mTags;
    private TripSetting mSetting;

    public String text1;
    public String text2;

    public Trip(String text1,String text2)  {
        this.text1=text1;
        this.text2=text2;
    }

    //JSON serialization and deserialization
    public static String toJSONUsingGSON(Trip trip) {
        String jsonStr=new Gson().toJson(trip);
        return jsonStr;
    }

    public static Trip JSONtoObjUsingGSON(String jsonStr)   {
        Trip trip=new Gson().fromJson(jsonStr,Trip.class);
        return trip;
    }
}
