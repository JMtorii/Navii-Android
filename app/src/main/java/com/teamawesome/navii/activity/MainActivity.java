package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.ParallaxPagerAdapter;
import com.teamawesome.navii.fragment.TravelDestinationFragment;
import com.teamawesome.navii.fragment.TravelDurationFragment;
import com.teamawesome.navii.fragment.TravelParticipantsFragment;
import com.teamawesome.navii.fragment.main.BudgetFragment;
import com.teamawesome.navii.fragment.main.ChooseTagsFragment;
import com.teamawesome.navii.util.NavigationConfiguration;
import com.teamawesome.navii.views.ParallaxHorizontalScrollView;
import com.teamawesome.navii.views.ParallaxViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends NaviiActivity2 {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.nav_view)
    NavigationView mNavigation;

    @BindView(R.id.main_next_button)
    Button mNextButton;

    @BindView(R.id.main_horizontal_scrollview)
    ParallaxHorizontalScrollView parallaxHorizontalScrollView;

    @BindView(R.id.main_view_pager)
    ParallaxViewPager parallaxViewPager;


    @Override
    protected NavigationConfiguration getNavConfig() {
        return NavigationConfiguration.Home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        setupParallaxViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDrawer != null && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            int index = parallaxViewPager.getCurrentItem();
            if (mNextButton.getVisibility() == View.VISIBLE && index == 4)
                mNextButton.setVisibility(View.INVISIBLE);
            else if (index != 0) {
                mNextButton.setVisibility(View.VISIBLE);
            }
            parallaxViewPager.setCurrentItem(--index, true);
        }
    }

    @OnClick(R.id.main_next_button)
    public void nextPress(View view) {
        int index = parallaxViewPager.getCurrentItem();
        int maxIndex = parallaxViewPager.getChildCount();
        Log.i(this.getClass().getName(), Integer.toString(maxIndex));
        Log.i(this.getClass().getName(), Integer.toString(index));

        if (index < maxIndex) {
            parallaxViewPager.setCurrentItem(index + 1, true);
        }
        if (index + 1 == 3)
            mNextButton.setVisibility(View.INVISIBLE);
    }

    private void setupParallaxViews() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, TravelDestinationFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, TravelDurationFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, TravelParticipantsFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, BudgetFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ChooseTagsFragment.class.getName()));

        ParallaxPagerAdapter parallaxPagerAdapter = new ParallaxPagerAdapter(super.getSupportFragmentManager(), fragments);
        parallaxViewPager.setAdapter(parallaxPagerAdapter);
        parallaxViewPager.configure(parallaxHorizontalScrollView);
        parallaxViewPager.setCurrentItem(0);
    }
}