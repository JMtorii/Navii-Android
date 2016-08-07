package com.teamawesome.navii.fragment.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.UploadImageTestActivity;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.ImageUploader;


public class UploadImageFragment extends Fragment {
    private static final int PHOTO_SELECTED = 1;

    private String mUploadedImageUrlStr = null;

    private Button mUploadImageButton;
    private ImageUploader mUploader;
    private TransferListener mTransferListener;
    private Activity parentActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (UploadImageTestActivity) getActivity();

        /**
         * Handle updates from uploading the image file
         */
        mTransferListener = new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    Toast.makeText(parentActivity.getApplicationContext(),
                            "Upload successful", Toast.LENGTH_SHORT).show();
                    mUploadedImageUrlStr = mUploader.getUploadedImageUrl();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//                    int percentage = (int) (bytesCurrent / bytesTotal * 100);
//                    progress.setProgress(percentage);
                //Display percentage transfered to user
            }

            @Override
            public void onError(int id, Exception ex) {
                Toast.makeText(parentActivity.getApplicationContext(),
                        "Upload failed", Toast.LENGTH_SHORT).show();
            }
        };

        mUploader = new ImageUploader(parentActivity.getApplicationContext(), mTransferListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upload_image, container, false);

        mUploadImageButton = (Button) v.findViewById(R.id.upload_image_button);
        mUploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(parentActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                if (ContextCompat.checkSelfPermission(parentActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(parentActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        // if necessary, show rationale for permission here

                    } else {
                        ActivityCompat.requestPermissions(parentActivity,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                }

                // If we got the permission, open the photo chooser dialog
                if (ContextCompat.checkSelfPermission(parentActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    openPhotoChooserDialog();
                }

            }
        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    openPhotoChooserDialog();
                } else {
                    // We didn't get the permission.
                    // TODO: Not sure what to do here.
                }
                return;
            }
        }
    }

    public void openPhotoChooserDialog() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_SELECTED);
    }
}