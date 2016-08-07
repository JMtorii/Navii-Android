package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.teamawesome.navii.NaviiApplication;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.debug.NaviBaseActivity;
import com.teamawesome.navii.util.NavigationConfiguration;
import com.teamawesome.navii.util.NaviiPreferenceData;
import com.teamawesome.navii.views.MainLatoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JMtorii on 16-06-16.
 */
public abstract class NaviiNavigationalActivity extends NaviBaseActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        int fragment = -1;

        switch (id) {
            case R.id.nav_home:
                launchClass = MainActivity.class;
                break;
            case R.id.nav_planned_trips:
                launchClass = SavedTripsActivity.class;
                break;
            case R.id.nav_logout:
                if (AccessToken.getCurrentAccessToken() != null) {
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                            .Callback() {

                        @Override
                        public void onCompleted(GraphResponse graphResponse) {
                            LoginManager.getInstance().logOut();
                        }
                    }).executeAsync();
                }
                NaviiPreferenceData.deleteLoginSession();
                launchClass = OnboardingActivity.class;
                fragment = R.layout.fragment_login;
                break;
            default:
                launchClass = null;
        }

        if (launchClass != null) {
            if (!launchClass.equals(getClass())) {
                Intent i = new Intent(this, launchClass);

                if (fragment != -1) {
                    i.putExtra("fragment", fragment);
                }

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

        setTitle(getResources().getString(getNavConfig().getToolbarTitleResId()));

        //TODO: Find the appropriate place to set side drawer email (and eventually photo)
        //TextView email = (TextView)findViewById(R.id.emailTextView);
        //email.setText(NaviiPreferenceData.getLoggedInUserEmail());

        // ButterKnife nor Google can bind the header layout when we add the header through the XML
        // As a result, we add this the old-fashioned way
        View headerView = getLayoutInflater().inflate(R.layout.nav_header_main, mNavigation, false);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(getClass().getName(), "Header has been pressed");
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        mNavigation.addHeaderView(headerView);
        MainLatoTextView nameTextView = (MainLatoTextView) headerView.findViewById(R.id.nav_header_name_text_view);
        MainLatoTextView emailTextView = (MainLatoTextView) headerView.findViewById(R.id.nav_header_email_text_view);
        CircleImageView circleImageView = (CircleImageView) headerView.findViewById(R.id.nav_header_image_view);

        nameTextView.setText(NaviiPreferenceData.getFullName());
        emailTextView.setText(NaviiPreferenceData.getLoggedInUserEmail());
    }

    protected void setupNavigationView() {
        // TODO: Implement checked drawer item logic
//        if (mNavItemId != NavigationConfiguration.PROFILE_DRAWER_ID) {
//            mNavigation.getMenu().findItem(mNavItemId).setChecked(true);
//        }
        mNavigation.setNavigationItemSelectedListener(this);
    }
}