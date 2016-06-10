package com.teamawesome.navii.views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.teamawesome.navii.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JMtorii on 16-06-05.
 */
public class PackageOverviewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.package_overview_item_text)
    TextView name;

    public PackageOverviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    @OnClick(R.id.card_view)
    public void clicked() {
        Log.i("Hey", name.getText().toString());
    }
}
