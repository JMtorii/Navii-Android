package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.main.UploadImageFragment;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiFragmentManager;

/**
 * Created by ecrothers on 16-06-19.
 */
public class UploadImageTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UploadImageFragment fragment = new UploadImageFragment();
        NaviiFragmentManager fm = new NaviiFragmentManager(getSupportFragmentManager(), R.id.upload_image_test_frame_layout);

        setContentView(R.layout.activity_upload_image_test);

        fm.switchFragment(
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
