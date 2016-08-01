package com.teamawesome.navii.activity.debug;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.WifiCheck;
import com.teamawesome.navii.views.MainLatoButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ian on 2016-07-31.
 */
public class NaviiSuperActivity extends AppCompatActivity {

    @Override
    public void onResume(){
        super.onResume();
        //Check Connectivity to Wifi
        if (!WifiCheck.isConnected(this)){
            NaviiWifiDialogFragment mNoWifi = new NaviiWifiDialogFragment();
            mNoWifi.show(this.getSupportFragmentManager(), Constants.LOADING_WHEEL_TAG);
        }
    }


    public class NaviiWifiDialogFragment extends DialogFragment {
        @BindView(R.id.reconnect_button)
        MainLatoButton wifiButton;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.dialog_no_wifi, container, false);
            ButterKnife.bind(this, v);
            return v;
        }

        @Override
        public void show(android.support.v4.app.FragmentManager manager, String tag) {
            super.show(manager, tag);
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Dialog wifiDialog = new Dialog(NaviiSuperActivity.this);
            wifiDialog.requestWindowFeature(STYLE_NO_TITLE);
            wifiDialog.setTitle("Reconnect to Internet");
            this.setCancelable(false);
            wifiDialog.show();
            return wifiDialog;
        }

        @OnClick(R.id.reconnect_button)
        public void closeDialog(){
            this.dismiss();
        }
    }
}
