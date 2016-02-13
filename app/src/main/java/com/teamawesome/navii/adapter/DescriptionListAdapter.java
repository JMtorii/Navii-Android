package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;

import java.util.List;

/**
 * Created by sjung on 03/02/16.
 */
public class DescriptionListAdapter extends RecyclerView.Adapter<PackageViewHolder> {

    private List<String> itemList;
    private Context context;

    public DescriptionListAdapter(Context context, List<String> itemList) {
        this.itemList = itemList;
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
//        holder.imageView.setImageResource(R.drawable.toronto1);
        holder.textView.setText(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
