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

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.ParallaxPagerAdapter;
import com.teamawesome.navii.fragment.main.BudgetFragment;
import com.teamawesome.navii.fragment.main.ChooseTagsFragment;
import com.teamawesome.navii.fragment.main.NaviiParallaxFragment;
import com.teamawesome.navii.fragment.main.TravelDestinationFragment;
import com.teamawesome.navii.fragment.main.TravelDurationFragment;
import com.teamawesome.navii.fragment.main.TravelParticipantsFragment;
import com.teamawesome.navii.util.NavigationConfiguration;
import com.teamawesome.navii.views.ParallaxHorizontalScrollView;
import com.teamawesome.navii.views.ParallaxViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends NaviiNavigationalActivity {
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
        ButterKnife.bind(this);

        setupParallaxViews();

        String projectToken = "3ff82d13d92fab6d2a258f9ea8a62747";
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);

        try {
            JSONObject props = new JSONObject();
            props.put("Gender", "Female");
            props.put("Logged in", false);
            mixpanel.track("MainActivity - onCreate called", props);
        } catch (JSONException e) {
            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (mDrawer != null && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            int index = parallaxViewPager.getCurrentItem();
            int maxIndex = parallaxViewPager.getChildCount();
            if (index != 0) {
                int lastFragmentId = parallaxViewPager.getChildAt(maxIndex - 1).getId();
                if (parallaxViewPager.getChildAt(index).getId() != lastFragmentId)
                    mNextButton.setVisibility(View.VISIBLE);
                else
                    mNextButton.setVisibility(View.INVISIBLE);
                parallaxViewPager.setCurrentItem(--index, true);
            }
        }
    }

    @OnClick(R.id.main_next_button)
    public void nextPress(View view) {
        int index = parallaxViewPager.getCurrentItem();
        int maxIndex = parallaxViewPager.getChildCount();
        //Currently the travel participants fragment precedes the budget fragment
        //Makes the next button invisible if the "next" fragment is budget
        if (index < maxIndex) {
            if (parallaxViewPager.getChildAt(index).getId() == R.id.travel_participants)
                mNextButton.setVisibility(View.INVISIBLE);
            else
                mNextButton.setVisibility(View.VISIBLE);
            parallaxViewPager.setCurrentItem(index + 1, true);
        }

        ParallaxPagerAdapter adapter = (ParallaxPagerAdapter) parallaxViewPager.getAdapter();
        NaviiParallaxFragment f = (NaviiParallaxFragment) adapter.getItem(index);

        f.nextFunction();
    }

    private void setupParallaxViews() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, TravelDestinationFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, TravelDurationFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, TravelParticipantsFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, BudgetFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ChooseTagsFragment.class.getName()));

        ParallaxPagerAdapter parallaxPagerAdapter = new ParallaxPagerAdapter(super.getSupportFragmentManager(), fragments);
        parallaxViewPager.setOffscreenPageLimit(fragments.size());
        parallaxViewPager.setAdapter(parallaxPagerAdapter);
        parallaxViewPager.configure(parallaxHorizontalScrollView, this);
        parallaxViewPager.setCurrentItem(0);
    }

    public Button getmNextButton() {
        return this.mNextButton;
    }
}