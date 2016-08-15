package com.teamawesome.navii.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.ToolbarConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.hns_categories)
    TextView descriptionTextView;

    @BindView(R.id.hns_rating)
    TextView ratingTextView;

    @BindView(R.id.hns_phone_number)
    TextView phoneNumberTextView;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.HeartAndSoulDetails;
    }

    @Override
    public void onLeftButtonClick() {
        supportFinishAfterTransition();
        super.onBackPressed();
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
        String description = "description";
        String phoneNumber = "";
        double rating = 0;

        Attraction attraction = getIntent().getParcelableExtra(Constants.INTENT_ATTRACTION);
        if (attraction != null) {
            title = attraction.getName();
            imageUri = attraction.getPhotoUri();
            address = attraction.getLocation().getAddress();
            description = attraction.getDescription();
            phoneNumber = attraction.getPhoneNumber();
            rating = attraction.getRating();
        }

        if (address == null) {
            address = "Address";
        }
        Picasso.with(this)
                .load(imageUri)
                .centerCrop()
                .fit()
                .into(toolbarImageView);

        collapsingToolbarLayout.setTitle(title);
        locationTextView.setText(address);
        descriptionTextView.setText(description);
        ratingTextView.setText(Double.toString(rating)+"/"+"5");
        phoneNumberTextView.setText(phoneNumber);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        AnalyticsManager.getMixpanel().track("HeartAndSoulDetailsActivity - onCreate");
    }
}
