package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.R;

import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-07-30.
 */
public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }
}
