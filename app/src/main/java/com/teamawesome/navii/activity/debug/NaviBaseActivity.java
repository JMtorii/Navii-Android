package com.teamawesome.navii.activity.debug;

import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.fragment.debug.NaviWifiDialogFragment;
import com.teamawesome.navii.util.WifiCheck;


/**
 * Created by Ian on 2016-07-31.
 */
public class NaviBaseActivity extends AppCompatActivity {

    @Override
    public void onResume() {
        super.onResume();
        //Check Connectivity to Wifi
        if (!WifiCheck.isConnected(this)) {
            new NaviWifiDialogFragment().show(this.getSupportFragmentManager(), Constants.LOADING_WHEEL_TAG);
        }
    }


}
