package com.teamawesome.navii.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.ItineraryScheduleActivity;
import com.teamawesome.navii.activity.PackageOverviewActivity;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 10/12/15.
 */
public class ItineraryRecommendListAdapter extends RecyclerView.Adapter<ItineraryRecommendListAdapter.ItineraryRecommendViewHolder> {

    private List<Itinerary> itineraries;
    private Context context;

    public ItineraryRecommendListAdapter(Context context, List<Itinerary> itineraries) {
        this.context = context;
        this.itineraries = itineraries;
    }

    @Override
    public ItineraryRecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_itinerary_list_item, null);

        return new ItineraryRecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItineraryRecommendViewHolder holder, int position) {
        holder.mTextView.setText(itineraries.get(position).getDescription());

        Picasso.with(context)
                .load(itineraries.get(position).getAttractions().get(0).getPhotoUri())
                .centerCrop()
                .fit()
                .into(holder.mImageView);

        holder.attractions = itineraries.get(position).getAttractions();

        final List<Attraction> attractionList = holder.attractions;
        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("TAG", "onLongClick");
                Intent itineraryScheduleActivity = new Intent(context, ItineraryScheduleActivity.class);
                itineraryScheduleActivity.putParcelableArrayListExtra(Constants.INTENT_ATTRACTION_LIST, new ArrayList<>(attractionList));
                context.startActivity(itineraryScheduleActivity);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    class ItineraryRecommendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.package_title)
        TextView mTextView;

        @BindView(R.id.package_image_view)
        ImageView mImageView;

        private List<Attraction> attractions;
        private List<String> photoUriList;

        @OnClick(R.id.package_image_view)
        void onClick() {
            //TODO: Link with Jun's package descriptor
            Log.d("TAG", "onClick");
            Intent packageOverviewActivity = new Intent(context, PackageOverviewActivity.class);
            packageOverviewActivity.putParcelableArrayListExtra(Constants.INTENT_ATTRACTION_LIST, new ArrayList<>(attractions));
            context.startActivity(packageOverviewActivity);
        }

        public ItineraryRecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            photoUriList = new ArrayList<>();
        }
    }
}
