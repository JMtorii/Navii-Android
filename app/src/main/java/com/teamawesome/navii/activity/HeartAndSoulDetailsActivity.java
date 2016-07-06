package com.teamawesome.navii.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.main.HeartAndSoulEditDialogFragment;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.ToolbarConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JMtorii on 16-06-11.
 */
public class HeartAndSoulDetailsActivity extends NaviiToolbarActivity {
    @BindView(R.id.collapsing_toolbar_imageview)
    ImageView toolbarImageView;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.hns_location)
    TextView locationTextView;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.HeartAndSoulDetails;
    }

    @Override
    public void onLeftButtonClick() {
        // nothing to do here
        onBackPressed();
    }

    @Override
    public void onRightButtonClick() {
        // nothing to do here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        String title = "title";
        String imageUri = "http://cpl.jumpfactor.netdna-cdn.com/wp-content/uploads/2015/04/plumber-Toronto-Toronto-plumbers.jpg";
        String address = "";

        if (!getIntent().getExtras().isEmpty()) {
            title = getIntent().getStringExtra(Constants.INTENT_ATTRACTION_TITLE);
            imageUri = getIntent().getStringExtra(Constants.INTENT_ATTRACTION_PHOTO_URI);
            address = getIntent().getStringExtra(Constants.INTENT_ATTRACTION_LOCATION);
        }

        if (address == null) {
            address = "Address";
        }
        Picasso.with(this)
                .load(imageUri)
                .centerCrop()
                .fit()
                .into(toolbarImageView);

//        toolbarImageView.setImageResource(R.drawable.toronto);
        collapsingToolbarLayout.setTitle(title);
        locationTextView.setText(address);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @OnClick(R.id.heart_and_soul_detail_fab)
    public void fabClicked() {
        HeartAndSoulEditDialogFragment dialog = new HeartAndSoulEditDialogFragment();
        dialog.show(getSupportFragmentManager(), "HeartAndSoulEditDialogFragment");
    }
}
