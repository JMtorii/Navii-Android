package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Attraction;

import java.util.List;

/**
 * Created by sjung on 03/02/16.
 */
public class DescriptionListAdapter extends RecyclerView.Adapter<PackageViewHolder> {

    private List<Attraction> attractions;
    private Context context;

    public DescriptionListAdapter(Context context, List<Attraction> attractions) {
        this.attractions = attractions;
        this.context = context;
    }

    @Override
    public PackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.description_view, null);
        PackageViewHolder packageViewHolder = new PackageViewHolder(view);
        return packageViewHolder;
    }

    @Override
    public void onBindViewHolder(PackageViewHolder holder, int position) {

        Picasso.with(context)
                .load(attractions.get(position).getPhotoUri())
                .centerCrop()
                .fit()
                .into(holder.imageView);

        holder.attractionName.setText(attractions.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }
}
