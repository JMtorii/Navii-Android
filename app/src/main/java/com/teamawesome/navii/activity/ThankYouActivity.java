package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.AnalyticsManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JMtorii on 16-08-05.
 */
public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        ButterKnife.bind(this);
        AnalyticsManager.getMixpanel().track("ThankYouActivity - onCreate");
    }

    @OnClick(R.id.thank_you_button)
    public void startButtonPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
