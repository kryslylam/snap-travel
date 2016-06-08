package com.lly_lab.snaptravel;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lly_lab.snaptravel.trip.Trip;

import java.util.ArrayList;

public class MyTripRecyclerViewAdapter
        extends RecyclerView.Adapter<MyTripRecyclerViewAdapter.TripViewHolder>{
    private static String LOG_TAG="MY TRIP RV ADAPTER";

    private ArrayList<Trip> mMyTrips;
    private static TripFragment.TripItemOnClickListener mItemOnClickListener;

    public MyTripRecyclerViewAdapter(ArrayList<Trip> myTrips, TripFragment.TripItemOnClickListener itemOnClickListener)   {
        mMyTrips=myTrips;
        mItemOnClickListener=itemOnClickListener;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_trip_card_item,parent,false);
        return new TripViewHolder(view);
    }

    //bind data / value into the view of card item
    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        Trip trip=mMyTrips.get(position);

        //test purpose
        holder.view1.setText(trip.text1);
        holder.view2.setText(trip.text2);
    }

    @Override
    public int getItemCount() {
        return mMyTrips.size();
    }

    public void addItem(int index,Trip trip)    {
        mMyTrips.add(index,trip);
        notifyItemInserted(index);
    }

    public void deleteItem(int index)   {
        mMyTrips.remove(index);
        notifyItemRemoved(index);
    }

    public Trip getItem(int index)  {
        return mMyTrips.get(index);
    }

    /**
     * Static class MyTripRecyclerViewAdapter.TripViewHolder
     */
    public static class TripViewHolder
            extends RecyclerView.ViewHolder {
        TextView view1,view2;

        public TripViewHolder(View itemView)    {
            super(itemView);
            //test purpose
            view1=(TextView) itemView.findViewById(R.id.destination);
            view2=(TextView) itemView.findViewById(R.id.start_date);

            //set item on click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //call TripItemOnClickListener, send along the item index and view
                    mItemOnClickListener.onItemClick(getAdapterPosition(),v);
                }
            });
        }
    }

}
