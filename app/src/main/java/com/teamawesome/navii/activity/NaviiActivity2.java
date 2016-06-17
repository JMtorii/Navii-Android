package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.teamawesome.navii.NaviiApplication;
import com.teamawesome.navii.R;
import com.teamawesome.navii.util.NavigationConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-06-16.
 */
public abstract class NaviiActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.nav_view)
    NavigationView mNavigation;

    private static final String NAV_ITEM_ID = "navItemId";
    protected NaviiApplication application;
    private int mNavItemId;


    protected abstract NavigationConfiguration getNavConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getNavConfig().getLayoutResId());
        ButterKnife.bind(this);

        application = NaviiApplication.getInstance();
        mNavItemId = (savedInstanceState == null) ? getNavConfig().getDrawerItemId() : savedInstanceState.getInt(NAV_ITEM_ID);

        setupActionBar();
        setupNavigationView();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Class launchClass;

        switch (id) {
            case R.id.nav_home:
                launchClass = MainActivity.class;
                break;
            case R.id.nav_planned_trips:
                launchClass = PlannedTripsActivity.class;
                break;
            default:
                launchClass = null;
        }

        if (launchClass != null) {
            if (!launchClass.equals(getClass())) {
                Intent i = new Intent(this, launchClass);
                startActivity(i);
                finish();
            } else {
                onActivityRelaunchedFromMenu();
            }
        }

        return true;
    }

    protected void onActivityRelaunchedFromMenu() {
        // nothing to do here
    }

    protected void setupActionBar() {
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        setTitle("");
    }

    protected void setupNavigationView() {
        mNavigation.getMenu().findItem(mNavItemId).setChecked(true);
        mNavigation.setNavigationItemSelectedListener(this);
    }
}
