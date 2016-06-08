package com.lly_lab.snaptravel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lly_lab.snaptravel.trip.Trip;

import java.util.ArrayList;

public class TripFragment extends BaseFragment {
    private RecyclerView mMyTripRecyclerView;
    private MyTripRecyclerViewAdapter mRVAdapter;

    private static String LOG_TAG="TRIP FRAGMENT";

    public static TripFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        TripFragment fragment = new TripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        //set action bar title
        getActionBar().setTitle(R.string.trip_fragment_title);
        //to receive calls and handle option menu
        setHasOptionsMenu(true);

        mMyTripRecyclerView=(RecyclerView) view.findViewById(R.id.my_trip_recycler_view);
        mMyTripRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVAdapter=new MyTripRecyclerViewAdapter(getMyTrip(),new TripItemOnClickListener());
        mMyTripRecyclerView.setAdapter(mRVAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_trip, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_trip_create:
                Log.d(LOG_TAG,"Create trip button clicked");
                CreateTripFragment createTripFragment=CreateTripFragment.newInstance(0);
                ((MainActivity) getActivity()).pushFragment(createTripFragment);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public ArrayList<Trip> getMyTrip()  {
        ArrayList<Trip> myTrips=new ArrayList<>();

        //test purpose
        myTrips.add(0,new Trip("TRIP1","DATE1"));
        myTrips.add(1,new Trip("TRIP2","DATE2"));
        myTrips.add(2,new Trip("TRIP3","DATE3"));
        myTrips.add(3,new Trip("TRIP4","DATE4"));
        myTrips.add(4,new Trip("TRIP5","DATE5"));

        return myTrips;
    }

    /**
     * class TripFragment.TripItemOnClickListener
     */
    public class TripItemOnClickListener   {
        public void onItemClick(int position, View v)   {
            Log.d(LOG_TAG,"Item on position "+position+" clicked");
            MyTripDetailFragment detailFragment=MyTripDetailFragment.newInstance(0,mRVAdapter.getItem(position));
            ((MainActivity) getActivity()).pushFragment(detailFragment);
        }
    }
}