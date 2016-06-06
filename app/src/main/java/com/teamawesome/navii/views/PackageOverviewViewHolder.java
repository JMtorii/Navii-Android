package com.teamawesome.navii.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.teamawesome.navii.R;

/**
 * Created by JMtorii on 16-06-05.
 */

// TODO: implement Butterknife
public class PackageOverviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name;

    public PackageOverviewViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        name = (TextView) itemView.findViewById(R.id.package_overview_item_text);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
