package com.teamawesome.navii.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.teamawesome.navii.util.AsyncDrawable;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.server.model.PackageScheduleListItem;
import com.teamawesome.navii.util.HeartAndSoulHeaderConfiguration;
import com.teamawesome.navii.util.VectorDrawableWorkerTask;
import com.teamawesome.navii.views.MainLatoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 03/02/16.
 */
public class PackageScheduleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PackageScheduleListItem> mItemList;
    private Context mContext;

    public PackageScheduleViewAdapter(Context context, List<PackageScheduleListItem> mItemList) {
        this.mItemList = mItemList;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position).getItemType();
    }

    public List<PackageScheduleListItem> getItemsList() {
        return mItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PackageScheduleListItem.TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_package_schedule_item_view, null);
            return new PackageItemViewHolder(view);
        } else if (viewType == PackageScheduleListItem.TYPE_DAY_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_package_schedule_day_section_item_view, null);
            return new DaySectionViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_package_schedule_section_item_view, null);
            return new SectionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case PackageScheduleListItem.TYPE_ITEM:
                onBindPackageItemViewHolder(holder, position);
                break;
            case PackageScheduleListItem.TYPE_MORNING:
            case PackageScheduleListItem.TYPE_AFTERNOON:
            case PackageScheduleListItem.TYPE_EVENING:
                onBindSectionViewHolder(holder, position);
                break;
            case PackageScheduleListItem.TYPE_DAY_HEADER:
                onBindSectionDayViewHolder(holder, position);
                break;
        }
    }

    private void onBindPackageItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("PackageSchedule", "onBindPackageItemViewHolder:" + position);
        PackageItemViewHolder packageItemViewHolder = (PackageItemViewHolder) holder;
        PackageScheduleListItem attractionItem = ((PackageScheduleListItem) mItemList.get(position));
        Attraction current = attractionItem.getAttraction();

        Picasso.with(mContext)
                .load(current.getPhotoUri())
                .fit()
                .centerCrop()
                .into(packageItemViewHolder.imageView);
        packageItemViewHolder.itemView.setTranslationX(0.0f);
        packageItemViewHolder.relativeLayout.setTranslationX(0.0f);
        packageItemViewHolder.attractionName.setText(current.getName());
    }

    private void onBindSectionViewHolder(RecyclerView.ViewHolder holder, int position) {
        SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
        PackageScheduleListItem header = (PackageScheduleListItem) mItemList.get(position);
        int resId = HeartAndSoulHeaderConfiguration.getConfiguration(header.getItemType()).getResId();
        sectionViewHolder.sectionImageView.setImageResource(resId);
//        Picasso.with(mContext).load(header.getResId()).fit().into(sectionViewHolder.sectionImageView);
//        loadHeader(header.getResId(), sectionViewHolder.sectionImageView);
    }

    private void onBindSectionDayViewHolder(RecyclerView.ViewHolder holder, int position) {
        DaySectionViewHolder daySectionViewHolder = (DaySectionViewHolder) holder;
        PackageScheduleListItem header = (PackageScheduleListItem) mItemList.get(position);
        daySectionViewHolder.sectionDayTitle.setText(header.getName());

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)daySectionViewHolder.sectionDayTitle.getLayoutParams();
        if (position == 0) {
            params.setMargins(0, 0, 0, 0);
        } else {
            params.setMargins(0, 60, 0, 0);
        }
        daySectionViewHolder.sectionDayTitle.setLayoutParams(params);
    }

    private void loadHeader(int resId, ImageView imageView) {
        if (VectorDrawableWorkerTask.cancelPotentialWork(resId, imageView)) {
            final VectorDrawableWorkerTask task = new VectorDrawableWorkerTask(imageView, mContext, resId);

            final AsyncDrawable asyncDrawable = new AsyncDrawable(task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }

    public PackageScheduleListItem getItem(final int position) {
        return mItemList.get(position);
    }

    public PackageScheduleListItem delete(final int position) {
        final PackageScheduleListItem item = mItemList.remove(position);

        notifyItemRemoved(position);
        return item;
    }

    public void move(int from, int to) {
        PackageScheduleListItem previousAttraction = mItemList.remove(from);
        mItemList.add(to, previousAttraction);
        notifyItemMoved(from, to);
    }

    public void replace(List<PackageScheduleListItem> itemList) {
        mItemList.clear();
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void add(PackageScheduleListItem item) {
        mItemList.add(1, item);
        notifyItemInserted(1);
    }

    public void add(int position, PackageScheduleListItem item) {
        mItemList.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class DaySectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.section_day_title)
        MainLatoTextView sectionDayTitle;

        public DaySectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.section_background_image)
        ImageView sectionImageView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnLongClickListener(null);
        }
    }

    public class PackageItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.package_item_card_view)
        public CardView cardView;

        @BindView(R.id.overlay)
        public FrameLayout overlay;

        @BindView(R.id.delete_button_image)
        public ImageButton deleteButton;

        @BindView(R.id.attraction_name)
        TextView attractionName;

        @BindView(R.id.attraction_photo)
        ImageView imageView;

        @BindView(R.id.package_item_layout)
        public RelativeLayout relativeLayout;


        public PackageItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.package_item_layout)
        public void detailsView() {
            Intent heartAndSoulDetailsActivity = new Intent(mContext, HeartAndSoulDetailsActivity.class);
            Attraction attraction = ((PackageScheduleListItem) mItemList.get(getAdapterPosition())).getAttraction();
            Bundle extras = new Bundle();
            extras.putParcelable(Constants.INTENT_ATTRACTION, attraction);

            if (attraction.getLocation() != null) {
                extras.putString(Constants.INTENT_ATTRACTION_LOCATION, attraction.getLocation().getAddress());
            }
            heartAndSoulDetailsActivity.putExtras(extras);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, imageView, "heartAndSoulImage");
            mContext.startActivity(heartAndSoulDetailsActivity, options.toBundle());
        }
    }
}
