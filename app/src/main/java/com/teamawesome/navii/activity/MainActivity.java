package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.ParallaxPagerAdapter;
import com.teamawesome.navii.fragment.Fragment1;
import com.teamawesome.navii.fragment.Fragment2;
import com.teamawesome.navii.fragment.Fragment3;
import com.teamawesome.navii.fragment.intro.PreferencesFragment;
import com.teamawesome.navii.fragment.main.ChooseLocationFragment;
import com.teamawesome.navii.fragment.main.ChooseTagsFragment;
import com.teamawesome.navii.fragment.main.NotificationsFragment;
import com.teamawesome.navii.fragment.main.PlannedTripsFragment;
import com.teamawesome.navii.fragment.main.SavedTripsFragment;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.views.ParallaxHorizontalScrollView;
import com.teamawesome.navii.views.ParallaxViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends NaviiActivity implements NavigationView.OnNavigationItemSelectedListener {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        setTitle("");

        mNavigation.setNavigationItemSelectedListener(this);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, Fragment1.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragment2.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragment3.class.getName()));

        ParallaxPagerAdapter parallaxPagerAdapter = new ParallaxPagerAdapter(super.getSupportFragmentManager(), fragments);
        parallaxViewPager.setAdapter(parallaxPagerAdapter);
        parallaxViewPager.configure(parallaxHorizontalScrollView);
        parallaxViewPager.setCurrentItem(0);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            int index = parallaxViewPager.getCurrentItem();
            if (index != 0) {
                parallaxViewPager.setCurrentItem(--index, true);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        String tag = "";

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = new ChooseLocationFragment();
            tag = Constants.CHOOSE_LOCATION_FRAGMENT_TAG;

        } else if (id == R.id.nav_planned_trips) {
            fragment = new PlannedTripsFragment();
            tag = Constants.PLANNING_PLANNED_TRIPS_FRAGMENT_TAG;

        } else if (id == R.id.nav_saved_trips) {
            fragment = new SavedTripsFragment();
            tag = Constants.PLANNING_SAVED_TRIPS_FRAGMENT_TAG;

        } else if (id == R.id.nav_preferences) {
            fragment = PreferencesFragment.newInstance(Constants.PREFERENCE_TYPE_1);
            tag = Constants.PREFERENCES_FRAGMENT_TAG;

        } else if (id == R.id.nav_notifications) {
            fragment = new NotificationsFragment();
            tag = Constants.NOTIFICATIONS_FRAGMENT_TAG;

        } else if (id == R.id.nav_choose_tags) {
            fragment = new ChooseTagsFragment();
            tag = Constants.PLANNING_CHOOSE_TAGS_FRAGMENT_TAG;

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
        }

        fm.switchFragment(
            fragment,
            Constants.NO_ANIM,
            Constants.NO_ANIM,
            tag,
            true,
            true,
            true
        );

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
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
    }
}