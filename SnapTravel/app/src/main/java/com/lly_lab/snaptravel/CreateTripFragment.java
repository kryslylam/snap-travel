package com.lly_lab.snaptravel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateTripFragment extends BaseFragment {

    public static CreateTripFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        CreateTripFragment fragment = new CreateTripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_setting, container, false);
        //set action bar title
        getActionBar().setTitle(R.string.page_create_trip);
        return view;
    }

}