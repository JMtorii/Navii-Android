package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.main.UploadImageFragment;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiFragmentManager;

/**
 * Created by ecrothers on 16-06-19.
 */
public class UploadImageTestActivity extends NaviiActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UploadImageFragment fragment = new UploadImageFragment();
        fm = new NaviiFragmentManager(getSupportFragmentManager(), R.id.intro_activity_content_frame);

        setContentView(R.layout.activity_intro);

        switchFragment(
                fragment,
                Constants.NO_ANIM,
                Constants.NO_ANIM,
                "IMAGE_TEST",
                true,
                false,
                false
        );
    }
}
