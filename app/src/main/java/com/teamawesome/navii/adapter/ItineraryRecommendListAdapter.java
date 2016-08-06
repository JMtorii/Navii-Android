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
import com.teamawesome.navii.activity.PackageOverviewActivity;
import com.teamawesome.navii.server.model.HeartAndSoulPackage;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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

    public ItineraryRecommendListAdapter(Context context, HeartAndSoulPackage heartAndSoulPackage) {
        this.context = context;
        this.heartAndSoulPackage = heartAndSoulPackage;
    }

    @Override
    public ItineraryRecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_itinerary_list_item, null);

        return new ItineraryRecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItineraryRecommendViewHolder holder, int position) {
        Itinerary itinerary = heartAndSoulPackage.getItineraries()[position][0];
        holder.mTextView.setText(itinerary.getDescription());
        if (itinerary!= null && itinerary.getAttractions() != null) {
            int index = new Random().nextInt(itinerary.getAttractions().size());
            String pictureURI = itinerary.getAttractions().get(index).getPhotoUri();

            while (uniquePictureMap.contains(pictureURI)) {
                index = new Random().nextInt(itinerary.getAttractions().size());
                pictureURI = itinerary.getAttractions().get(index).getPhotoUri();
            }

            Picasso.with(context)
                    .load(pictureURI)
                    .centerCrop()
                    .fit()
                    .into(holder.mImageView);
            uniquePictureMap.add(pictureURI);

            holder.itineraries = heartAndSoulPackage.getItineraries()[position];
        }
    }

    @Override
    public int getItemCount() {
        return heartAndSoulPackage.getItineraries()[0].length;
    }

    public class ItineraryRecommendViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.package_title)
        TextView mTextView;

        @BindView(R.id.package_image_view)
        ImageView mImageView;

        private Itinerary[] itineraries;
        private List<String> photoUriList;

        public ItineraryRecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            photoUriList = new ArrayList<>();
        }

        @OnClick(R.id.package_image_view)
        public void onClick() {
            if (itineraries != null) {
                Intent packageOverviewActivity = new Intent(context, PackageOverviewActivity.class);
                packageOverviewActivity.putParcelableArrayListExtra(Constants.INTENT_ITINERARIES, new ArrayList<>(Arrays.asList(itineraries)));
                packageOverviewActivity.putParcelableArrayListExtra(Constants.INTENT_EXTRA_ATTRACTION_LIST, new ArrayList<>(heartAndSoulPackage.getExtraAttractions()));
                packageOverviewActivity.putParcelableArrayListExtra(Constants.INTENT_EXTRA_RESTAURANT_LIST, new ArrayList<>(heartAndSoulPackage.getExtraRestaurants()));
                packageOverviewActivity.putExtra(Constants.INTENT_ITINERARY_TITLE, mTextView.getText().toString());
                packageOverviewActivity.putExtra(Constants.INTENT_DAYS, itineraries.length);
                Activity activity = (Activity) context;
                activity.startActivity(packageOverviewActivity);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                Toast.makeText(context, R.string.error_steve_fucked_up, Toast.LENGTH_LONG).show();
            }

        }
    }
}
