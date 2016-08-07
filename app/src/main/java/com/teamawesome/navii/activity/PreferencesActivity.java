package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.intro.PreferencesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 19/06/16.
 */
public class PreferencesActivity extends AppCompatActivity {
    private Adapter mAdapter;

    @BindView(R.id.itinerary_schedule_viewpager)
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        ButterKnife.bind(this);
        setupViewPager(mViewPager);
    }

    @OnClick(R.id.preferences_next_button)
    public void onClick() {
        int index = mViewPager.getCurrentItem();
        int maxIndex = mViewPager.getChildCount();
        //Currently the travel participants fragment precedes the budget fragment
        //Makes the next button invisible if the "next" fragment is budget
        if (index < maxIndex) {
            mViewPager.setCurrentItem(index + 1, true);
        } else {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                PreferencesFragment preferencesFragment = (PreferencesFragment) mAdapter.getItem(i);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new Adapter(getSupportFragmentManager());
        mAdapter.addFragment(PreferencesFragment.newInstance(1), "1");
        mAdapter.addFragment(PreferencesFragment.newInstance(2), "2");
        mAdapter.addFragment(PreferencesFragment.newInstance(3), "3");
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
}
