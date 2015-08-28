package com.teamawesome.navii.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.teamawesome.navii.R;

/**
 * Created by JMtorii on 15-08-25.
 */
public class IntroViewPagerFragment extends Fragment {
    private PagerSlidingTabStrip mTabs;
    private ViewPager mPager;
    private PagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro_view_pager, container, false);
        mTabs = (PagerSlidingTabStrip) v.findViewById(R.id.intro_tabs);
        mPager = (ViewPager) v.findViewById(R.id.intro_pager);
        mAdapter = new PagerAdapter(getFragmentManager());
        mPager.setAdapter(mAdapter);
        mTabs.setViewPager(mPager);
        mPager.setCurrentItem(0);
        return v;
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = getActivity()
                .getApplicationContext()
                .getResources()
                .getStringArray(R.array.intro_pager_titles);

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;

            // TODO: If all fragments are relatively similar, we can optimize and use only one fragment
            switch(position) {
                case 0:     // One
                    fragment = IntroPageOneFragment.newInstance(position);
                    break;
                case 1:     // Two
                    fragment = IntroPageTwoFragment.newInstance(position);
                    break;
                case 2:     // Three
                    fragment = IntroPageThreeFragment.newInstance(position);
                    break;
                case 3:     // Four
                    fragment = IntroPageFourFragment.newInstance(position);
                    break;
                default:
                    return null;    // TODO: horrible. should do error checking instead
            }

            return fragment;
        }
    }

}
