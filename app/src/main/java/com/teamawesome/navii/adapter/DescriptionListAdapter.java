package com.teamawesome.navii.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.HeartAndSoulDetailsActivity;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.util.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 03/02/16.
 */
public class DescriptionListAdapter extends RecyclerView.Adapter<DescriptionListAdapter
        .PackageViewHolder> {

    private List<Attraction> mAttractionList;
    private Context context;
    private GregorianCalendar gregorianCalendar;

    public DescriptionListAdapter(Context context, List<Attraction> mAttractionList) {
        this.mAttractionList = mAttractionList;
        this.context = context;
        this.gregorianCalendar = new GregorianCalendar(2016, 6, 26, 8, 0);
    }

    @Override
    public PackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.description_view, null);
        PackageViewHolder packageViewHolder = new PackageViewHolder(view);

        packageViewHolder.deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TAG","deleted");
                return false;
            }
        });
        return packageViewHolder;
    }

    @Override
    public void onBindViewHolder(PackageViewHolder holder, int position) {

        Picasso.with(context)
                .load(mAttractionList.get(position).getPhotoUri())
                .centerCrop()
                .fit()
                .into(holder.imageView);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        holder.attractionName.setText(mAttractionList.get(position).getName());
        holder.attractionPrice.setText("$" + String.valueOf(mAttractionList.get(position).getPrice()));
        holder.attractionStartTime.setText(dateFormat.format(gregorianCalendar.getTime()));

        gregorianCalendar.add(GregorianCalendar.HOUR, mAttractionList.get(position).getDuration());
    }

    public void refreshStartTimes() {


        for (int i = 0; i < getItemCount(); i++) {

        }
    }

    public void delete(int position) {
        Log.d("TAg","Delete:"+position);
        mAttractionList.remove(position);
        notifyItemRemoved(position);
    }

    public void move(int from, int to) {
        Attraction prev = mAttractionList.remove(from);
        mAttractionList.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);

    }

    @Override
    public int getItemCount() {
        return mAttractionList.size();
    }

    public class PackageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itinerary_relative_layout)
        public RelativeLayout relativeLayout;

        @BindView(R.id.attraction_name)
        TextView attractionName;

        @BindView(R.id.attraction_price)
        TextView attractionPrice;

        @BindView(R.id.attraction_start_time)
        TextView attractionStartTime;

        @BindView(R.id.attraction_photo)
        ImageView imageView;

        @BindView(R.id.overlay)
        public FrameLayout overlay;

        @BindView(R.id.delete_button_image)
        ImageButton deleteButton;

        public PackageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.itinerary_relative_layout)
        public void detailsView() {
            Intent heartAndSoulDetailsActivity = new Intent(context, HeartAndSoulDetailsActivity.class);
            Attraction attraction =  mAttractionList.get(getAdapterPosition());
            Bundle extras = new Bundle();
            extras.putString(Constants.ATTRACTION_PHOTO_URI, attraction.getPhotoUri());
            extras.putString(Constants.ATTRACTION_TITLE, attraction.getName());

            if (attraction.getLocation() != null) {
                extras.putString(Constants.ATTRACTION_LOCATION, attraction.getLocation().getAddress());
            }
            heartAndSoulDetailsActivity.putExtras(extras);
            context.startActivity(heartAndSoulDetailsActivity);
        }
    }

    @OnClick(R.id.overlay)
    public void deleteView(View view) {
        Log.d("TAG", "DELETE");
    }
}
