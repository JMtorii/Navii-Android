package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.views.MainLatoButton;
import com.teamawesome.navii.views.MainLatoTextView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ian on 8/4/2016.
 */
public class SavedTripsAdapter extends RecyclerView.Adapter<SavedTripsAdapter.TripViewHolder> {

    List<Itinerary> itineraries;
    public SavedTripsAdapter(List<Itinerary> savedItineraries){
        this.itineraries = savedItineraries;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tripsView = inflater.inflate(R.layout.adapter_savedtrip_item, parent, false);
        return new TripViewHolder(tripsView);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        System.out.println(position);
        holder.itinerary = this.itineraries.get(position);
        System.out.println(holder.itinerary);
        holder.tripName.setText(holder.itinerary.getNickname());
    }


    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.trips_tripname)
        MainLatoTextView tripName;

        private Itinerary itinerary;

        public TripViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
