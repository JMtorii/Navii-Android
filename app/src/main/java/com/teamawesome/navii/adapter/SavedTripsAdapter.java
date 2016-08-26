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
import com.teamawesome.navii.activity.SavedItineraryScheduleActivity;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.server.model.PackageScheduleListItem;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.views.MainLatoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ian on 8/4/2016.
 */
public class SavedTripsAdapter extends RecyclerView.Adapter<SavedTripsAdapter.TripViewHolder> {

    private List<Itinerary> saved_trips;
    public SavedTripsAdapter(List<Itinerary> itineraries, Context context){
        this.saved_trips = itineraries;
        TripViewHolder.context = context;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tripsView = inflater.inflate(R.layout.adapter_itinerary_list_item, parent, false);
        return new TripViewHolder(tripsView);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        holder.mTripName.setText(saved_trips.get(position).getDescription());
        holder.trip = saved_trips.get(position);
        for (PackageScheduleListItem packageScheduleListItem : holder.trip.getPackageScheduleListItems()) {
            if (packageScheduleListItem.getItemType() == PackageScheduleListItem.TYPE_ITEM) {
                String savedTripURI = packageScheduleListItem.getAttraction().getPhotoUri();
                Picasso.with(TripViewHolder.context)
                        .load(savedTripURI)
                        .fit()
                        .centerCrop()
                        .into(holder.mSavedTripsImage);
            }
        }
    }


    @Override
    public int getItemCount() {
        return saved_trips.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.package_title)
        MainLatoTextView mTripName;

        @BindView(R.id.package_image_view)
        ImageView mSavedTripsImage;

        private Itinerary trip;

        private static Context context;

        public TripViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick (R.id.itinerary_item)
        public void gotoSchedule(){
            Intent scheduleActivity = new Intent(context, SavedItineraryScheduleActivity.class);
            scheduleActivity.putExtra(Constants.INTENT_ITINERARIES, this.trip);
            scheduleActivity.putExtra(Constants.INTENT_ITINERARY_TITLE, mTripName.getText().toString());
            Activity activity = (Activity) context;
            activity.startActivity(scheduleActivity);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
