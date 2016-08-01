package com.teamawesome.navii.fragment.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;

import com.teamawesome.navii.R;

/**
 * Created by JMtorii on 16-06-22.
 */
public class HeartAndSoulEditDialogFragment extends DialogFragment {
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogSlideAnimation);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("This is a dialog title")
                .setView(inflater.inflate(R.layout.fragment_heart_and_soul_edit_dialog, null))
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Nothing to do here
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Nothing to do here
                    }

                });
        return builder.create();
    }

    @Override
    public void onStart() {
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onStart();
    }
}
