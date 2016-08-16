package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;
import com.teamawesome.navii.R;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.ToolbarConfiguration;

import butterknife.BindView;

/**
 * Created by sjung on 19/06/16.
 */
public class SavedItineraryScheduleActivity extends ItineraryScheduleActivity {

    @BindView(R.id.itinerary_schedule_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.floating_action_menu)
    FloatingActionMenu floatingActionMenu;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.HeartAndSoul;
    }

    @Override
    public void onLeftButtonClick() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**

            Can change this to a save button (change text and redirect to another screen)
            or something when name editing gets hooked up

         **/
        if (super.rightTextButton != null) {
            super.rightTextButton.setVisibility(View.GONE);
        }


        AnalyticsManager.getMixpanel().track("SavedItineraryScheduleActivity - onCreate");
    }



}
