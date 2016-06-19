package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Itinerary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 10/12/15.
 */
public class ItineraryRecommendListAdapter extends
        RecyclerView.Adapter<ItineraryRecommendListAdapter.ItineraryRecommendViewHolder> {

    private List<Itinerary> itineraries;
    private Context context;

    public ItineraryRecommendListAdapter(Context context, List<Itinerary> itineraries) {
        this.context = context;
        this.itineraries = itineraries;
    }

    @Override
    public ItineraryRecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.itinerary_listitem_layout, null);

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

        @OnClick(R.id.package_image_view)
        void onClick(View v) {
            //TODO: Link with Jun's package descriptor
        }

        public ItineraryRecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
