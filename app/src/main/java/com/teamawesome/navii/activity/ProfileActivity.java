package com.teamawesome.navii.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.BitmapResizer;
import com.teamawesome.navii.util.NavigationConfiguration;
import com.teamawesome.navii.util.NaviiPreferenceData;
import com.teamawesome.navii.util.OnFocusListenable;
import com.teamawesome.navii.views.MainLatoTextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JMtorii on 2015-11-01.
 */
public class ProfileActivity extends NaviiNavigationalActivity implements OnFocusListenable {
    @BindView(R.id.profile_thumbnail)
    CircleImageView mPictureThumbnail;

    @BindView(R.id.profile_name_textview)
    MainLatoTextView mNameTextView;

    @BindView(R.id.profile_username_textview)
    MainLatoTextView mUsernameTextView;

    private static final String APP_PICTURE_DIRECTORY = "/Navi";
    private static final String MIME_TYPE_IMAGE = "image/";
    private static final String FILE_SUFFIX_JPG = ".jpg";
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private Uri selectedPhotoPath;

    @Override
    protected NavigationConfiguration getNavConfig() {
        return NavigationConfiguration.Profile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mNameTextView.setText(NaviiPreferenceData.getFullName());
        mUsernameTextView.setText(NaviiPreferenceData.getLoggedInUserEmail());
        mPictureThumbnail.setImageResource(R.drawable.ic_account_circle);
    }

    @Override
    public void onResume(){
        super.onResume();
        mNameTextView.setText(NaviiPreferenceData.getFullName());
        mUsernameTextView.setText(NaviiPreferenceData.getLoggedInUserEmail());
        mUsernameTextView.refreshDrawableState();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        checkReceivedIntent();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            setImageViewWithImage();
        }
    }

    @OnClick(R.id.profile_thumbnail)
    public void profilePressed(View view) {
        Log.i(getClass().getName(), "Profile button pressed");
        takePictureWithCamera();
    }

    @OnClick(R.id.profile_preferences_button)
    public void preferencesPressed(View view) {
        Log.i(getClass().getName(), "Preferences button pressed");
        Intent intent = new Intent(ProfileActivity.this, PreferencesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void takePictureWithCamera() {
        // create intent to capture image from camera
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = createImageFile();
        if (photoFile == null) {
            return;
        }
        selectedPhotoPath = Uri.parse(photoFile.getAbsolutePath());

        captureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(captureIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    private void setImageViewWithImage() {
        Matrix matrix = new Matrix();
        matrix.postRotate(getImageOrientation(selectedPhotoPath.getPath()));

        Bitmap pictureBitmap = BitmapResizer.shrinkBitmap(selectedPhotoPath.toString(), mPictureThumbnail.getWidth(), mPictureThumbnail.getHeight());
        Bitmap rotatedBitmap = Bitmap.createBitmap(pictureBitmap, 0, 0, pictureBitmap.getWidth(), pictureBitmap.getHeight(), matrix, true);
        mPictureThumbnail.setImageBitmap(rotatedBitmap);
    }

    private int getImageOrientation(String imagePath) {
        int rotate = 0;

        try {
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

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + APP_PICTURE_DIRECTORY);
        storageDir.getParentFile().mkdirs();
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
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
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
        Intent imageReceivedIntent = getIntent();
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