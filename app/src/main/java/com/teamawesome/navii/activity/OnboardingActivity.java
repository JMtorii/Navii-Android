package com.teamawesome.navii.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.OnboardingPagerAdapter;
import com.teamawesome.navii.views.CircleIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-07-22.
 */
public class OnboardingActivity extends AppCompatActivity {
    @BindView(R.id.onboarding_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.onboarding_circle_indicator)
    CircleIndicator mCircleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        OnboardingPagerAdapter mPageAdapter = new OnboardingPagerAdapter(this.getSupportFragmentManager(), this);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setCurrentItem(0);
        mCircleIndicator.setViewPager(mViewPager);
        mPageAdapter.registerDataSetObserver(mCircleIndicator.getDataSetObserver());
    }
}
