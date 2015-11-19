package com.teamawesome.navii.fragment.main;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.teamawesome.navii.R;
import com.teamawesome.navii.util.BitmapResizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JMtorii on 2015-11-01.
 */
public class ProfileFragment extends MainFragment implements OnFocusListenable {

    private static final String APP_PICTURE_DIRECTORY = "/Navi";
    private static final String MIME_TYPE_IMAGE = "image/";
    private static final String FILE_SUFFIX_JPG = ".jpg";
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final String IMAGE_URI_KEY = "IMAGE_URI";
    private static final String BITMAP_WIDTH = "BITMAP_WIDTH";
    private static final String BITMAP_HEIGHT = "BITMAP_HEIGHT";

    private BootstrapCircleThumbnail mPictureThumbnail;
    private Uri selectedPhotoPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity.setTitle("Spongebob Squarepants");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        mPictureThumbnail = (BootstrapCircleThumbnail) v.findViewById(R.id.profile_thumbnail);
        mPictureThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureWithCamera();
            }
        });

        return v;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        checkReceivedIntent();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == parentActivity.RESULT_OK) {
            setImageViewWithImage();
        }
    }

    private void takePictureWithCamera() {
        // create intent to capture image from camera
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = createImageFile();
        selectedPhotoPath = Uri.parse(photoFile.getAbsolutePath());

        captureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(captureIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    private void setImageViewWithImage() {
        Bitmap pictureBitmap = BitmapResizer.shrinkBitmap(selectedPhotoPath.toString(), mPictureThumbnail.getWidth(), mPictureThumbnail.getHeight());
        mPictureThumbnail.setImageBitmap(pictureBitmap);
//        pictureTaken = true;
    }

    private File createImageFile() {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + APP_PICTURE_DIRECTORY);
        storageDir.mkdirs();

        File imageFile = null;

        try {
            imageFile = File.createTempFile(
                    imageFileName,  /* prefix */
                    FILE_SUFFIX_JPG,         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    private Uri getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = parentActivity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return Uri.parse(result);
    }

    private void checkReceivedIntent() {
        Intent imageRecievedIntent = parentActivity.getIntent();
        String intentAction = imageRecievedIntent.getAction();
        String intentType = imageRecievedIntent.getType();

        if (Intent.ACTION_SEND.equals(intentAction) && intentType != null) {
            if (intentType.startsWith(MIME_TYPE_IMAGE)) {
                Uri contentUri = imageRecievedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                selectedPhotoPath = getRealPathFromURI(contentUri);
                setImageViewWithImage();
            }
        }
    }


}
