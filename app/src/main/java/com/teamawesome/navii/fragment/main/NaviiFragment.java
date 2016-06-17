package com.teamawesome.navii.fragment.main;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.activity.NaviiActivity;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.WifiCheck;

/**
 * Created by JMtorii on 15-12-25.
 */
public class NaviiFragment extends Fragment {
    protected NaviiActivity parentActivity;
    private NaviiSpinner NaviiLoaderWheel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        parentActivity = getActivity().getClass().equals(MainActivity.class) ?
//                (MainActivity) getActivity() :
//                (IntroActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!WifiCheck.isConnected(this)) {
//            DialogFragment test = new NaviiWifiDialogFragment();
//            test.setCancelable(false);
//            test.show(this.getFragmentManager(), Constants.NO_WIFI_DIALOG);
//        }
    }

    protected void startLoader(){
        NaviiLoaderWheel = new NaviiSpinner();
        NaviiLoaderWheel.setCancelable(false);
        NaviiLoaderWheel.show(this.getFragmentManager(), Constants.LOADING_WHEEL_TAG);
    }

    protected void endLoader (){
        NaviiLoaderWheel.dismiss();
        NaviiLoaderWheel = null;
    }

    public class NaviiWifiDialogFragment extends DialogFragment {

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Dialog wifiDialog = new Dialog(NaviiFragment.this.getContext());
            wifiDialog.requestWindowFeature(STYLE_NO_TITLE);
            wifiDialog.setContentView(R.layout.dialog_no_wifi);
            BootstrapButton wifiButton =
                    (BootstrapButton) wifiDialog.findViewById(R.id.reconnect_button);
            wifiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast t = Toast.makeText(NaviiFragment.this.getContext(),
                            "Reconnecting",
                            Toast.LENGTH_SHORT);
                    t.show();
                    wifiDialog.dismiss();
                }
            });
            wifiDialog.setTitle("Reconnect to Internet");
            return wifiDialog;
        }
    }

    public class NaviiSpinner extends DialogFragment {

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState){
            Dialog naviiLoaderWheel = new Dialog (this.getContext());
            naviiLoaderWheel.requestWindowFeature(STYLE_NO_TITLE);
            naviiLoaderWheel.setContentView(R.layout.naviloader);
            return naviiLoaderWheel;
        }
    }
}
