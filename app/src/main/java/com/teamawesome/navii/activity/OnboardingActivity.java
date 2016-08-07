package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

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

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //getWindow().setStatusBarColor(Color.TRANSPARENT);

        OnboardingPagerAdapter mPageAdapter = new OnboardingPagerAdapter(this.getSupportFragmentManager(), this);
        mViewPager.setAdapter(mPageAdapter);

        // Get the starting fragment, if not provided, choose zero
        int fragment = getIntent().getIntExtra("fragment", 0);

        // TODO: Bad practice to refer to the login page as always being the "last" page
        // Translate fragment to corresponding page of the view pager
        switch (fragment) {
            case R.layout.fragment_login:
                fragment = mPageAdapter.getCount();
                break;
        }

        mViewPager.setCurrentItem(fragment);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);
            }

            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mCircleIndicator.setViewPager(mViewPager);
        mPageAdapter.registerDataSetObserver(mCircleIndicator.getDataSetObserver());
    }
}
