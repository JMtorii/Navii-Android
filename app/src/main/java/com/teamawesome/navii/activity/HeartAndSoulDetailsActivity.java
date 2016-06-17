package com.teamawesome.navii.activity;

import android.app.Activity;
import android.os.Bundle;

import com.teamawesome.navii.R;

import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-06-11.
 */
public class HeartAndSoulDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_overview);
        ButterKnife.bind(this);
    }
}
