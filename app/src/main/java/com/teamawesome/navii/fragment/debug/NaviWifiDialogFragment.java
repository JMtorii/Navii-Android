package com.teamawesome.navii.fragment.debug;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.views.MainLatoButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ian on 8/4/2016.
 */
public class NaviWifiDialogFragment extends DialogFragment {

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
        final Dialog wifiDialog = new Dialog(this.getActivity());
        this.setCancelable(false);
        wifiDialog.requestWindowFeature(STYLE_NO_TITLE);
        wifiDialog.setTitle("Reconnect to Internet");
        wifiDialog.show();
        return wifiDialog;
    }

    @Override
    @OnClick (R.id.reconnect_button)
    public void dismiss(){
        startActivity(new Intent(Settings.ACTION_SETTINGS));
        super.dismiss();
    }
}
