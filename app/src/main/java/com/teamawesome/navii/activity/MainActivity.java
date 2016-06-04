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
import com.teamawesome.navii.util.NaviiFragmentManager;
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
    ParallaxHorizontalScrollView realHorizontalScrollView;

    @BindView(R.id.main_view_pager)
    ParallaxViewPager realViewPager;

    private static NaviiFragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigation.setNavigationItemSelectedListener(this);

        // TODO: remove if we're sure that we're not using navigational fragments
//        fm = new NaviiFragmentManager(getSupportFragmentManager(), R.id.main_activity_content_frame);
//        fm.switchFragment(new HomeFragment(), -1, -1, "HomeFragment", true, true, true);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, Fragment1.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragment2.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragment3.class.getName()));

        ParallaxPagerAdapter realViewPagerAdapter = new ParallaxPagerAdapter(super.getSupportFragmentManager(), fragments);
        realViewPager.setAdapter(realViewPagerAdapter);
        realViewPager.configure(realHorizontalScrollView);
        realViewPager.setCurrentItem(0);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            int index = realViewPager.getCurrentItem();
            if (index != 0) {
                realViewPager.setCurrentItem(--index, true);
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
        int index = realViewPager.getCurrentItem();
        int maxIndex = realViewPager.getChildCount();
        Log.i(this.getClass().getName(), Integer.toString(maxIndex));
        Log.i(this.getClass().getName(), Integer.toString(index));

        if (index < maxIndex) {
            realViewPager.setCurrentItem(index + 1, true);
        }
    }

    public void setActionBarTitle(String text) {
        mToolbar.setTitle(text);
    }
}
//    // Toolbar
//    private Toolbar mToolbar;
//
//    // Navigation drawer
//    private DrawerLayout mDrawerLayout;
//    private LinearLayout mDrawerLinearLayout;
//    private ListView mDrawerList;
//    private ActionBarDrawerToggle mDrawerToggle;
//    private LinearLayout mProfileButton;
//    private String[] mNavDrawerTitles;
//
//    // Fragment manager
//    // TODO: do we need this?
//    private String curFragmentTag = Constants.CHOOSE_LOCATION_FRAGMENT_TAG;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//
//        fm = new NaviiFragmentManager(getSupportFragmentManager(), R.id.main_activity_content_frame);
//
//        mToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
//        mProfileButton = (LinearLayout) findViewById(R.id.main_activity_profile_button);
//        mNavDrawerTitles = getResources().getStringArray(R.array.nav_drawer_array);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
//        mDrawerLinearLayout = (LinearLayout) findViewById(R.id.main_activity_drawer_linear_layout);
//        mDrawerList = (ListView) findViewById(R.id.main_activity_left_drawer);
//
//        setupToolbar();
//        setupDrawer();
//        setupUserInformation();
//
//        if (savedInstanceState == null) {
//            selectDrawerItem(0);
//        }
//    }
//
//    /**
//     * When using the ActionBarDrawerToggle, you must call it during
//     * onPostCreate() and onConfigurationChanged()...
//     */
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggle
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
////    @Override
////    public void onBackPressed() {
////        Fragment f = getSupportFragmentManager().findFragmentByTag(curFragmentTag);
////        if (f != null && !f.getTag().equals(Constants.CHOOSE_LOCATION_FRAGMENT_TAG)) {
////            Log.v("test", f.getTag());
////            super.onBackPressed();
////        }
////    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.PROFILE_FRAGMENT_TAG);
//        if (fragment != null && fragment.isVisible() && fragment instanceof OnFocusListenable) {
//            ((OnFocusListenable) fragment).onWindowFocusChanged(hasFocus);
//        }
//    }
//
//    private void setupToolbar() {
//        mToolbar.setTitle("Main Activity");
//
//        // TODO: add appropriate action items to menu
//        mToolbar.inflateMenu(R.menu.toolbar_main_activity_menu);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                // TODO: add appropriate menu items
////                switch (menuItem.getItemId()) {
////                    case R.id.action_settings:
////                        // TODO: add custom animation
////                        Fragment fragment = new SettingsMainFragment();
////                        FragmentManager fragmentManager = getSupportFragmentManager();
////                        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();
////                        return true;
////                }
//
//                return false;
//            }
//        });
//    }
//
//    private void setupDrawer() {
//        // set a custom shadow that overlays the main content when the drawer opens
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        // set up the drawer's list view with items and click listener
//        mDrawerList.setAdapter(new ArrayAdapter<Object>(this, R.layout.drawer_main_list_item, mNavDrawerTitles));
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//
//        // ActionBarDrawerToggle ties together the the proper interactions
//        // between the sliding drawer and the action bar app icon
//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                mToolbar,  /* nav drawer image to replace 'Up' caret */                  // THIS WAS MODIFIED
//                R.string.drawer_open,  /* "open drawer" description for accessibility */
//                R.string.drawer_close  /* "close drawer" description for accessibility */
//        ) {
//            public void onDrawerClosed(View view) {
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//
//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//
//        mProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new ProfileFragment();
//                String tag = Constants.PROFILE_FRAGMENT_TAG;
//
//                fm.switchFragment(
//                        fragment,
//                        Constants.NO_ANIM,
//                        Constants.NO_ANIM,
//                        tag,
//                        true,
//                        true,
//                        true
//                );
//                mDrawerLayout.closeDrawers();
//            }
//        });
//    }
//
//    // TODO: possibly move listener and selectItem to a separate class
//    /* The click listener for ListView in the navigation drawer */
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectDrawerItem(position);
//        }
//    }
//
//    private void selectDrawerItem(int position) {
//        // update the main content by replacing fragments
//        Fragment fragment = null;
//        String tag;
//
//        switch (position) {
//            case 0:         // Home
//                fragment = new ChooseLocationFragment();
//                tag = Constants.CHOOSE_LOCATION_FRAGMENT_TAG;
//                break;
//            case 1:         // Planned Trips
//                fragment = new PlannedTripsFragment();
//                tag = Constants.PLANNING_PLANNED_TRIPS_FRAGMENT_TAG;
//                break;
//            case 2:         // Saved Trips
//                fragment = new SavedTripsFragment();
//                tag = Constants.PLANNING_SAVED_TRIPS_FRAGMENT_TAG;
//                break;
//            case 3:         // Preferences
//                fragment = PreferencesFragment.newInstance(Constants.PREFERENCE_TYPE_1);
//                tag = Constants.PREFERENCES_FRAGMENT_TAG;
//                break;
//            case 4:         // Notifications
//                fragment = new NotificationsFragment();
//                tag = Constants.NOTIFICATIONS_FRAGMENT_TAG;
//                break;
//            case 5:          // Tags
//                fragment = new ChooseTagsFragment();
//                tag = Constants.PLANNING_CHOOSE_TAGS_FRAGMENT_TAG;
//                break;
//            case 6:         // Logout
//                Intent intent = new Intent(this, IntroActivity.class);
//                startActivity(intent);
//                tag = "";
//                NaviiPreferenceData.clearAllUserData();
//                break;
//            default:        // this should never happen
//                tag = "";
//                break;
//        }
//
//        if (fragment != null) {
//            fm.switchFragment(
//                    fragment,
//                    Constants.NO_ANIM,
//                    Constants.NO_ANIM,
//                    tag,
//                    true,
//                    true,
//                    true
//            );
//
//            // update selected item and title, then close the drawer
//            mDrawerList.setItemChecked(position, false);
//            mToolbar.setTitle(mNavDrawerTitles[position]);
//            mDrawerLayout.closeDrawer(mDrawerLinearLayout);
//        }
//    }
//
//    private void setupUserInformation() {
//        Call<User> call = userAPI.getUser(NaviiPreferenceData.getLoggedInUserEmail());
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Response<User> response, Retrofit retrofit) {
//                if (response.code() == 400) {
//                    Log.w("MainActivity", "Something messed up.");
//                    // TODO: possibly log the user out
//
//                } else if (response.code() == 200) {
//                    Log.w("MainActivity", "Success.");
//                    User user = response.body();
//                    NaviiPreferenceData.setLoggedInUserEmail(user.getUsername());
//                    NaviiPreferenceData.setIsFacebook(user.isFacebook());
//                } else {
//                    Log.w("MainActivity", "What the fuck happened. Response code: " + String.valueOf(response.code()));
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.w("Sign Up", "Failed: " + t.getMessage());
//            }
//        });
//    }
//
//    public void setActionBarTitle(String text) {
//        mToolbar.setTitle(text);
//    }
//}
