package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

    @BindView(R.id.hns_categories)
    TextView descriptionTextView;

    @BindView(R.id.hns_rating)
    TextView ratingTextView;

    @BindView(R.id.hns_phone_number)
    TextView phoneNumberTextView;

    @BindView(R.id.heart_and_soul_detail_fab)
    FloatingActionButton floatingActionButton;

    Attraction attraction;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        String title = "title";
        String imageUri = "http://cpl.jumpfactor.netdna-cdn.com/wp-content/uploads/2015/04/plumber-Toronto-Toronto-plumbers.jpg";
        String address = "";
        String description = "description";
        String phoneNumber = "";
        double rating = 0;

        attraction = getIntent().getParcelableExtra(Constants.INTENT_ATTRACTION);

        boolean prefetchView = getIntent().getBooleanExtra(Constants.INTENT_VIEW_PREFETCH, false);

        if (prefetchView) {
            floatingActionButton.setVisibility(View.VISIBLE);
        }

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
        AnalyticsManager.getMixpanel().track("HeartAndSoulDetailsActivity - onCreate");
    }

    @OnClick(R.id.heart_and_soul_detail_fab)
    public void onClicked() {
        Intent data = new Intent();
        data.putExtra(Constants.INTENT_ATTRACTION, attraction);
        data.putExtra(Constants.INTENT_VIEW_PREFETCH, true);
        setResult(Constants.RESPONSE_ATTRACTION_SELECTED, data);
        finish();
    }
}
