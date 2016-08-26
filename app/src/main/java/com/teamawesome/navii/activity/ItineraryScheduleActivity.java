package com.teamawesome.navii.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.debug.NaviCustomAttractionDialogFragment;
import com.teamawesome.navii.fragment.main.ItineraryScheduleMapFragment;
import com.teamawesome.navii.fragment.main.ItineraryScheduleViewFragment;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.server.model.Location;
import com.teamawesome.navii.server.model.PackageScheduleListItem;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.ToolbarConfiguration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sjung on 19/06/16.
 */
public class ItineraryScheduleActivity extends NaviiToolbarActivity
        implements NaviCustomAttractionDialogFragment.NoticeDialogListener {

    @BindView(R.id.itinerary_schedule_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.floating_action_menu)
    FloatingActionMenu floatingActionMenu;

    private Adapter mAdapter;
    public int days;
    private boolean mEditable;
    private Itinerary itinerary;
    private List<Attraction> attractions;
    private List<Attraction> restaurants;
    private ProgressDialog progressDialog;

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
        ItineraryScheduleViewFragment mapFragment = (ItineraryScheduleViewFragment) mAdapter.getItem(0);
        Itinerary newItinerary = new Itinerary.Builder()
                .description(itinerary.getDescription())
                .packageScheduleListItems(mapFragment.getItems())
                .build();
        nextActivity.putExtra(Constants.INTENT_ITINERARIES, newItinerary);
        nextActivity.putParcelableArrayListExtra(Constants.INTENT_EXTRA_ATTRACTION_LIST, (ArrayList<Attraction>) attractions);
        nextActivity.putParcelableArrayListExtra(Constants.INTENT_EXTRA_RESTAURANT_LIST, (ArrayList<Attraction>) restaurants);
        startActivity(nextActivity);
        overridePendingTransition(R.anim.slide_in_down, R.anim.hold);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getIntent().getStringExtra(Constants.INTENT_ITINERARY_TITLE);
        days = getIntent().getIntExtra(Constants.INTENT_DAYS, 2);
        titleText.setText(title);

        setupViewPager(mViewPager);

        itinerary = getIntent().getParcelableExtra(Constants.INTENT_ITINERARIES);

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

    @OnClick(R.id.custom_menu_item)
    public void onCustomItemClick() {
        //Custom fab button is bound to Itinerary Activity layout so had to call it in fragment to
        //access the adapter
        NaviCustomAttractionDialogFragment.days = this.days;
        new NaviCustomAttractionDialogFragment().show(getSupportFragmentManager(), Constants.CUSTOM_ATTRACTION_TAG);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        if (requestCode == Constants.GET_ATTRACTION_EXTRA_REQUEST_CODE) {
            if (resultCode == Constants.RESPONSE_GOOGLE_SEARCH) {
                final Place place = PlaceAutocomplete.getPlace(this, data);
                final Location location = new Location.Builder()
                        .address(place.getAddress().toString())
                        .latitude(place.getLatLng().latitude)
                        .longitude(place.getLatLng().longitude)
                        .build();

                String cll = place.getLatLng().latitude + "," + place.getLatLng().longitude;
                Call<Attraction> imageUrlCall = RestClient.attractionAPI.getYelpImageUrl(place.getName().toString(), cll);
                imageUrlCall.enqueue(new Callback<Attraction>() {
                    @Override
                    public void onResponse(Response<Attraction> response, Retrofit retrofit) {
                        Attraction s = response.body();
                        Log.d("ItineraryScheduleView", s.getPhotoUri());
                        final Attraction attraction = new Attraction.Builder()
                                .location(location)
                                .name(place.getName().toString())
                                .rating((double) Math.round(place.getRating() * 100) / 100.0)
                                .phoneNumber(place.getPhoneNumber().toString())
                                .photoUri(s.getPhotoUri())
                                .description("Google Places Search")
                                .price(place.getPriceLevel())
                                .build();
                        Intent intent = new Intent();
                        intent.putExtra(Constants.INTENT_ATTRACTION, attraction);
                        for (int i = 0; i < mAdapter.getCount(); i++) {
                            Fragment fragment = mAdapter.getItem(i);
                            fragment.onActivityResult(requestCode, Constants.RESPONSE_ATTRACTION_SELECTED, intent);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("onFailure()", t.getMessage(), t);
                        progressDialog.dismiss();
                    }
                });
                progressDialog = ProgressDialog.show(this, "Retrieving Attraction", "Please wait...");
            } else {
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    Fragment fragment = mAdapter.getItem(i);
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }

        floatingActionMenu.close(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new Adapter(getSupportFragmentManager());
        mAdapter.addFragment(new ItineraryScheduleViewFragment(), "Schedule");
        mAdapter.addFragment(new ItineraryScheduleMapFragment(), "Map");
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public void onDialogPositiveClick(int day, Attraction attraction) {
        ItineraryScheduleViewFragment scheduleViewFragment = (ItineraryScheduleViewFragment) mAdapter.getItem(0);

        List<PackageScheduleListItem> items = scheduleViewFragment.getItems();
        int counter = 0;
        int index = 1;
        for (PackageScheduleListItem item : items) {
            index++;
            if (item.getItemType() == PackageScheduleListItem.TYPE_DAY_HEADER) {
                counter++;
                if (counter == day) {
                    break;
                }
            }
        }

        scheduleViewFragment.add(index, attraction);
        floatingActionMenu.close(true);
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

    public Itinerary getItinerary() {
        return itinerary;
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
