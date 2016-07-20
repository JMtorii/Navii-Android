package com.teamawesome.navii.activity;

import android.os.Bundle;

import com.teamawesome.navii.util.ToolbarConfiguration;

import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-07-17.
 */
public class HeartAndSoulSaveActivity extends NaviiToolbarActivity {
    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.HeartAndSoulSave;
    }

    @Override
    public void onLeftButtonClick() {
        // Nothing to do here
    }

    @Override
    public void onRightButtonClick() {
        onModalBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
