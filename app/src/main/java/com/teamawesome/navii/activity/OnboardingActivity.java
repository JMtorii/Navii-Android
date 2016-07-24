package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.intro.OnboardingViewPagerFragment;
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

    private OnboardingPagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        mPageAdapter = new OnboardingPagerAdapter(this.getSupportFragmentManager());
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setCurrentItem(0);
        mCircleIndicator.setViewPager(mViewPager);
        mPageAdapter.registerDataSetObserver(mCircleIndicator.getDataSetObserver());
    }

    public class OnboardingPagerAdapter extends FragmentPagerAdapter {
        public OnboardingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public int getCount() {
            return getResources().getStringArray(R.array.onboarding_titles).length;
        }

        @Override
        public Fragment getItem(int position) {
            return OnboardingViewPagerFragment.newInstance(position);
        }
    }
}
