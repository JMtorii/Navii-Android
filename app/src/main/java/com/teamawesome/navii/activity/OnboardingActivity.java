package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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

        OnboardingPagerAdapter mPageAdapter = new OnboardingPagerAdapter(this.getSupportFragmentManager(), this);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setCurrentItem(0);
        mCircleIndicator.setViewPager(mViewPager);
        mPageAdapter.registerDataSetObserver(mCircleIndicator.getDataSetObserver());
    }
}
