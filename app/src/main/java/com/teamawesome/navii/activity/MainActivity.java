package com.teamawesome.navii.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.planning.ChooseLocationFragment;
import com.teamawesome.navii.fragment.planning.NotificationsFragment;
import com.teamawesome.navii.fragment.planning.PlannedTripsFragment;
import com.teamawesome.navii.fragment.planning.PreferencesFragment;
import com.teamawesome.navii.fragment.planning.SavedTripsFragment;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiFragmentManager;

public class MainActivity extends AppCompatActivity {

    private NaviiFragmentManager fm;

    // Toolbar
    private Toolbar mToolbar;

    // Navigation drawer
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mNavDrawerTitles;

    // Fragment manager
    private String curFragmentTag = Constants.CHOOSE_LOCATION_FRAGMENT_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = new NaviiFragmentManager(getSupportFragmentManager(), R.id.main_activity_content_frame);

        mToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        mToolbar.setTitle("Main Activity");

        // TODO: add appropriate action items to menu
        mToolbar.inflateMenu(R.menu.toolbar_main_activity_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // TODO: add appropriate menu items
//                switch (menuItem.getItemId()) {
//                    case R.id.action_settings:
//                        // TODO: add custom animation
//                        Fragment fragment = new SettingsMainFragment();
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();
//                        return true;
//                }

                return false;
            }
        });

        mNavDrawerTitles = getResources().getStringArray(R.array.nav_drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.main_activity_left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<Object>(this, R.layout.drawer_main_list_item, mNavDrawerTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolbar,  /* nav drawer image to replace 'Up' caret */                  // THIS WAS MODIFIED
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectDrawerItem(0);
        }
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    // TODO: possibly move listener and selectItem to a separate class
    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDrawerItem(position);
        }
    }

    private void selectDrawerItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        String tag;

        switch (position) {
            case 0:         // Home
                fragment = new ChooseLocationFragment();
                tag = Constants.CHOOSE_LOCATION_FRAGMENT_TAG;
                break;
            case 1:         // Planned Trips
                fragment = new PlannedTripsFragment();
                tag = Constants.PLANNING_PLANNED_TRIPS_FRAGMENT_TAG;
                break;
            case 2:         // Saved Trips
                fragment = new SavedTripsFragment();
                tag = Constants.PLANNING_SAVED_TRIPS_FRAGMENT_TAG;
                break;
            case 3:         // Preferences
                fragment = new PreferencesFragment();
                tag = Constants.PREFERENCES_FRAGMENT_TAG;
                break;
            case 4:         // Notifications
                fragment = new NotificationsFragment();
                tag = Constants.NOTIFICATIONS_FRAGMENT_TAG;
                break;
//            case 5:         // Logout
//                fragment = new ();
//                tag = "";
//                break;
            default:        // this should never happen
                fragment = null;
                tag = "";
                break;
        }

        if (fragment != null) {
            fm.switchFragment(
                    fragment,
                    Constants.NO_ANIM,
                    Constants.NO_ANIM,
                    tag,
                    true,
                    true,
                    true
            );

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mToolbar.setTitle(mNavDrawerTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }

    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentByTag(curFragmentTag);
        if (f != null && !f.getTag().equals(Constants.CHOOSE_LOCATION_FRAGMENT_TAG)) {
            Log.v("test", f.getTag());
            super.onBackPressed();
        }
    }
}
