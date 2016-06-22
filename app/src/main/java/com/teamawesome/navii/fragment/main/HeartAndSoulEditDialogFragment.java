package com.teamawesome.navii.fragment.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by JMtorii on 16-06-22.
 */
public class HeartAndSoulEditDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO: Create a custom view using a layout xml
        // https://developer.android.com/guide/topics/ui/dialogs.html

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This is a message.")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }

                });
        return builder.create();
    }
}
