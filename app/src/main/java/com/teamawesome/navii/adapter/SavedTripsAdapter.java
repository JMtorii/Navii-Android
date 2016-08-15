package com.teamawesome.navii.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.ItineraryScheduleActivity;
import com.teamawesome.navii.activity.SavedItineraryScheduleActivity;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.views.MainLatoTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ian on 8/4/2016.
 */
public class SavedTripsAdapter extends RecyclerView.Adapter<SavedTripsAdapter.TripViewHolder> {

    private List<List<Itinerary>> saved_trips;
    public SavedTripsAdapter(List<List<Itinerary>> itineraries, Context context){
        this.saved_trips = itineraries;
        TripViewHolder.context = context;
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
        holder.mTripName.setText(saved_trips.get(position).get(0).getDescription());
        holder.trip = saved_trips.get(position);

        String savedTripURI = holder.trip.get(0).getAttractions().get(0).getPhotoUri();
        Picasso.with(TripViewHolder.context)
                .load(savedTripURI)
                .fit()
                .centerCrop()
                .into(holder.mSavedTripsImage);
    }


    @Override
    public int getItemCount() {
        return saved_trips.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.trips_tripname)
        MainLatoTextView mTripName;

        @BindView(R.id.saved_trip_imageview)
        ImageView mSavedTripsImage;

        private List<Itinerary> trip;

        private static Context context;

        public TripViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick (R.id.trip_item)
        public void gotoSchedule(){
            Intent scheduleActivity = new Intent(context, SavedItineraryScheduleActivity.class);
            scheduleActivity.putParcelableArrayListExtra(Constants.INTENT_ITINERARIES, (ArrayList<Itinerary>) this.trip);
            scheduleActivity.putExtra(Constants.INTENT_ITINERARY_TITLE, mTripName.getText().toString());
            scheduleActivity.putExtra(Constants.INTENT_DAYS, trip.size());
            Activity activity = (Activity) context;
            activity.startActivity(scheduleActivity);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
