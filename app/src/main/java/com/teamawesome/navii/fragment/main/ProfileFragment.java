package com.teamawesome.navii.fragment.main;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.teamawesome.navii.R;
import com.teamawesome.navii.util.BitmapResizer;
import com.teamawesome.navii.util.NaviiPreferenceData;

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

    private BootstrapCircleThumbnail mPictureThumbnail;
    private TextView mUsernameTextView;
    private TextView mFacebookTextView;
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
        mUsernameTextView = (TextView) v.findViewById(R.id.profile_username_textview);
        mFacebookTextView = (TextView) v.findViewById(R.id.profile_facebook_textview);

        mUsernameTextView.setText(NaviiPreferenceData.getLoggedInUserEmail());

        String facebookText = NaviiPreferenceData.isFacebook() ? "Hell yes" : "Naw";
        mFacebookTextView.setText(facebookText);

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
        Uri imageUri = selectedPhotoPath;
        String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
        Cursor cur = parentActivity.getContentResolver().query(imageUri, orientationColumn, null, null, null);
        int orientation = -1;

        if (cur != null && cur.moveToFirst()) {
            orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(orientation);

        Bitmap pictureBitmap = BitmapResizer.shrinkBitmap(selectedPhotoPath.toString(), mPictureThumbnail.getWidth(), mPictureThumbnail.getHeight());
        Bitmap rotatedBitmap = Bitmap.createBitmap(pictureBitmap, 0, 0, pictureBitmap.getWidth(), pictureBitmap.getHeight(), matrix, true);
        mPictureThumbnail.setImageBitmap(rotatedBitmap);
    }

    private int getImageOrientation(String imagePath) {
        int rotate = 0;

        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rotate;
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
        Intent imageReceivedIntent = parentActivity.getIntent();
        String intentAction = imageReceivedIntent.getAction();
        String intentType = imageReceivedIntent.getType();

        if (Intent.ACTION_SEND.equals(intentAction) && intentType != null) {
            if (intentType.startsWith(MIME_TYPE_IMAGE)) {
                Uri contentUri = imageReceivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                selectedPhotoPath = getRealPathFromURI(contentUri);
                setImageViewWithImage();
            }
        }
    }
}
