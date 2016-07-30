package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.intro.LoginFragment;
import com.teamawesome.navii.fragment.intro.OnboardingViewPagerFragment;

/**
 * Created by JMtorii on 16-07-25.
 */
public class OnboardingPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public OnboardingPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public int getCount() {
        return context.getResources().getStringArray(R.array.onboarding_titles).length + 1;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == context.getResources().getStringArray(R.array.onboarding_titles).length) {
            return new LoginFragment();
        }

        return OnboardingViewPagerFragment.newInstance(position);
    }
}
