package com.teamawesome.navii.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.ToolbarConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-06-11.
 */
public class HeartAndSoulDetailsActivity extends NaviiToolbarActivity {
    @BindView(R.id.collapsing_toolbar_imageview)
    ImageView toolbarImageView;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.HeartAndSoulDetails;
    }

    @Override
    public void onLeftButtonClick() {
        // nothing to do here
    }

    @Override
    public void onRightButtonClick() {
        // nothing to do here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        toolbarImageView.setImageResource(R.drawable.toronto);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}
