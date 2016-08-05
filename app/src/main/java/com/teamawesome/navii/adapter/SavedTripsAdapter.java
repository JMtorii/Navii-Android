package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Itinerary;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Ian on 8/4/2016.
 */
public class SavedTripsAdapter extends RecyclerView.Adapter<SavedTripsAdapter.TripViewHolder> {

    public SavedTripsAdapter(List<Itinerary> savedItineraries){

    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tripsView = inflater.inflate(R.layout.activity_planned_trips, parent, false);
        return new TripViewHolder(tripsView);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder{

        public TripViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
