package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.intro.PreferencesFragment;
import com.teamawesome.navii.util.Constants;

/**
 * Created by sjung on 19/06/16.
 */
public class PreferencesActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        PreferencesFragment preferencesFragment = PreferencesFragment.newInstance(Constants.PREFERENCE_TYPE_1);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.preference_fragment_frame, preferencesFragment, Constants.PREFERENCES_FRAGMENT_TAG)
                .commit();
    }
}
