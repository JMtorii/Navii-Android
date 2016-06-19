package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.teamawesome.navii.R;


public class UploadImageFragment extends NaviiFragment {

    private BootstrapButton mUploadImageButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upload_image, container, false);

        mUploadImageButton = (BootstrapButton) v.findViewById(R.id.upload_image_button);
        mUploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Boop.", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}