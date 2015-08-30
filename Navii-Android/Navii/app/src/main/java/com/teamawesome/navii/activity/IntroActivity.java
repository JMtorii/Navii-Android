package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.IntroViewPagerFragment;

/**
 * Created by JMtorii on 15-08-24.
 */
public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Fragment fragment = new IntroViewPagerFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.intro_content_frame, fragment);
        trans.commit();
    }

    public void loadFragment(Fragment newFragment, boolean isReplace) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        // TODO: consider adding tags to fragments
        if (isReplace) {
            trans.replace(R.id.intro_content_frame, newFragment);
        } else {
            trans.add(R.id.intro_content_frame, newFragment);
        }

        trans.commit();
    }
}