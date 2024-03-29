package com.teamawesome.navii.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.ItineraryScheduleActivity;
import com.teamawesome.navii.server.model.HeartAndSoulPackage;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.server.model.PackageScheduleListItem;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 10/12/15.
 */
public class ItineraryRecommendListAdapter extends RecyclerView.Adapter<ItineraryRecommendListAdapter.ItineraryRecommendViewHolder> {
    private HeartAndSoulPackage heartAndSoulPackage;
    private Context context;
    private Set<String> uniquePictureMap = new HashSet<>();
    private int duration;

    public ItineraryRecommendListAdapter(Context context, HeartAndSoulPackage heartAndSoulPackage, int days) {
        this.context = context;
        this.heartAndSoulPackage = heartAndSoulPackage;
        this.duration = days;
    }

    @Override
    public ItineraryRecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_itinerary_list_item, null);

        return new ItineraryRecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItineraryRecommendViewHolder holder, int position) {
        List<Itinerary> itinerary = heartAndSoulPackage.getItineraries();
        List<PackageScheduleListItem> packageScheduleListItems = itinerary.get(position).getPackageScheduleListItems();
        for (PackageScheduleListItem packageScheduleListItem : packageScheduleListItems) {
            if (packageScheduleListItem.getItemType() == 4) {
                String pictureURI = packageScheduleListItem.getAttraction().getPhotoUri();
                if (!uniquePictureMap.contains(pictureURI) && !pictureURI.equals("N/A")) {
                    Picasso.with(context)
                            .load(pictureURI)
                            .fit()
                            .centerCrop()
                            .into(holder.mImageView);
                    uniquePictureMap.add(pictureURI);
                }
            }
        }
        holder.mTextView.setText(itinerary.get(position).getDescription());
        holder.itinerary = heartAndSoulPackage.getItineraries().get(position);
    }

    @Override
    public int getItemCount() {
        return heartAndSoulPackage.getItineraries().size();
    }

    public class ItineraryRecommendViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.package_title)
        TextView mTextView;

        @BindView(R.id.package_image_view)
        ImageView mImageView;

        private Itinerary itinerary;
        private List<String> photoUriList;

        public ItineraryRecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            photoUriList = new ArrayList<>();
        }

        @OnClick(R.id.package_image_view)
        public void onClick() {
            if (itinerary != null) {
                Intent itineraryScheduleActivity = new Intent(context, ItineraryScheduleActivity.class);
                itineraryScheduleActivity.putExtra(Constants.INTENT_ITINERARIES, itinerary);
                itineraryScheduleActivity.putParcelableArrayListExtra(Constants.INTENT_EXTRA_ATTRACTION_LIST, new ArrayList<>(heartAndSoulPackage.getExtraAttractions()));
                itineraryScheduleActivity.putParcelableArrayListExtra(Constants.INTENT_EXTRA_RESTAURANT_LIST, new ArrayList<>(heartAndSoulPackage.getExtraRestaurants()));
                itineraryScheduleActivity.putExtra(Constants.INTENT_ITINERARY_TITLE, mTextView.getText().toString());
                itineraryScheduleActivity.putExtra(Constants.INTENT_ITINERARY_EDITABLE, true);
                itineraryScheduleActivity.putExtra(Constants.INTENT_DAYS, duration);

                Activity activity = (Activity) context;
                activity.startActivity(itineraryScheduleActivity);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                Toast.makeText(context, R.string.error_steve_fucked_up, Toast.LENGTH_LONG).show();
            }

        }
    }
}
