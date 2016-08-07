package com.teamawesome.navii.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.NaviiPreferenceData;

/**
 * Created by JMtorii on 16-08-04.
 */
public class LaunchActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        if (NaviiPreferenceData.isLoggedIn()) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, OnboardingActivity.class);
        }
        startActivity(intent);
        finish();

        AnalyticsManager.getMixpanel().track("LaunchActivity - onCreate");
    }
}
