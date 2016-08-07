package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.main.ItineraryScheduleMapFragment;
import com.teamawesome.navii.fragment.main.ItineraryScheduleViewFragment;
import com.teamawesome.navii.server.model.Attraction;

import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.ToolbarConfiguration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by sjung on 19/06/16.
 */
public class ItineraryScheduleActivity extends NaviiToolbarActivity {

//    @BindView(R.id.itinerary_schedule_fab)
//    FloatingActionButton mAddScheduleFloatingActionButton;

    @BindView(R.id.itinerary_schedule_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.itinerary_day_spinner)
    Spinner spinner;

    @BindView(R.id.floating_action_menu)
    FloatingActionMenu floatingActionMenu;

    private Adapter mAdapter;
    private int days;
    private boolean mEditable;
    private List<Itinerary> itineraries;
    private List<Attraction> attractions;
    private List<Attraction> restaurants;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.HeartAndSoul;
    }

    @Override
    public void onLeftButtonClick() {
        super.onBackPressed();
    }

    @Override
    public void onRightButtonClick() {
        Intent nextActivity = new Intent(this, HeartAndSoulSaveActivity.class);

        nextActivity.putParcelableArrayListExtra(Constants.INTENT_ITINERARIES,(ArrayList<Itinerary>) itineraries);
        nextActivity.putParcelableArrayListExtra(Constants.INTENT_EXTRA_ATTRACTION_LIST,(ArrayList<Attraction>) attractions);
        nextActivity.putParcelableArrayListExtra(Constants.INTENT_EXTRA_RESTAURANT_LIST, (ArrayList<Attraction>) restaurants);
        startActivity(nextActivity);
        overridePendingTransition(R.anim.slide_in_down, R.anim.hold);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getIntent().getStringExtra(Constants.INTENT_ITINERARY_TITLE);
        days = getIntent().getIntExtra(Constants.INTENT_DAYS, 1);
        titleText.setText(title);

        setSupportActionBar(mToolbar);
        setupViewPager(mViewPager);
        setupSpinner();

        itineraries = getIntent().getParcelableArrayListExtra(Constants.INTENT_ITINERARIES);
        attractions = getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_ATTRACTION_LIST);
        restaurants = getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_RESTAURANT_LIST);
        mEditable = getIntent().getBooleanExtra(Constants.INTENT_ITINERARY_EDITABLE, false);
        if (!mEditable) {
            floatingActionMenu.setVisibility(View.GONE);
        }
        mTabLayout.setVisibility(View.VISIBLE);
        mTabLayout.setupWithViewPager(mViewPager);
        setupWindowAnimations();

        AnalyticsManager.getMixpanel().track("ItineraryScheduleActivity - onCreate");
    }

    @OnClick(R.id.eat_menu_item)
    public void onEatMenuClick() {
        List<Attraction> extraRestaurants = getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_RESTAURANT_LIST);

        Intent intent = new Intent(this, PrefetchAttractionsActivity.class);
        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_RESTAURANT_LIST, new ArrayList<>(extraRestaurants));
        startActivityForResult(intent, Constants.GET_ATTRACTION_EXTRA_REQUEST_CODE);
    }

    @OnClick(R.id.see_menu_item)
    public void onSeeMenuClick() {
        List<Attraction> extraAttractions = getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_ATTRACTION_LIST);
        Intent intent = new Intent(this, PrefetchAttractionsActivity.class);
        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_RESTAURANT_LIST, new ArrayList<>(extraAttractions));
        startActivityForResult(intent, Constants.GET_ATTRACTION_EXTRA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            Fragment fragment = mAdapter.getItem(i);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new Adapter(getSupportFragmentManager());
        mAdapter.addFragment(new ItineraryScheduleViewFragment(), "Schedule");
        mAdapter.addFragment(new ItineraryScheduleMapFragment(), "Map");
        viewPager.setAdapter(mAdapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        String[] dayString = new String[days];
        for (int i = 0; i < dayString.length; i++) {
            dayString[i] = Integer.toString(i+1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dayString);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @OnItemSelected(R.id.itinerary_day_spinner)
    public void onSpinnerItemSelected(View view, int position) {
        Toast.makeText(this, spinner.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        sendDayChangeMessage(position);
        for (int i = 0; i < mAdapter.getCount(); i++) {
            Fragment fragment = mAdapter.getItem(i);
            if (fragment.getClass().equals(ItineraryScheduleMapFragment.class)) {
                ((ItineraryScheduleMapFragment) fragment).updateDay(position);
            } else {
                ((ItineraryScheduleViewFragment) fragment).updateDay(position);
            }
        }
    }

    private void sendDayChangeMessage(int position) {
        Intent intent = new Intent("intent_day_change");
        intent.putExtra("day", position);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }
}
