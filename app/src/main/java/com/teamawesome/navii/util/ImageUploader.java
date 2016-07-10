package com.teamawesome.navii.util;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;

import java.io.File;
import java.util.Date;

// AMAZON COGNITO CREDENTIALS.  IMPORTANT.

// GET AWS CREDENTIALS
// Initialize the Amazon Cognito credentials provider
//CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
//        getApplicationContext(),
//        "us-east-1:31ad60dc-feaf-4683-804a-04b88f007d7a", // Identity Pool ID
//        Regions.US_EAST_1 // Region
//        );
// Initialize the Cognito Sync client

// STORE USER DATA
//CognitoSyncManager syncClient = new CognitoSyncManager(
//        getApplicationContext(),
//        Regions.US_EAST_1, // Region
//        credentialsProvider);
//
//// Create a record in a dataset and synchronize with the server
//        Dataset dataset = syncClient.openOrCreateDataset("myDataset");
//        dataset.put("myKey", "myValue");
//        dataset.synchronize(new DefaultSyncCallback() {
//@Override
//public void onSuccess(Dataset dataset, List newRecords) {
//        //Your handler code here
//        }
//        });


/**
 * Created by ecrothers on 16-03-03.
 */
public class ImageUploader {

    private File mImageFile = null;
    private String mRemoteName = null;
    private String mUploadedImageUrl = null;

    private Context mAppContext;
    private TransferListener mTransferListener;

    public ImageUploader(Context appContext, TransferListener transferListener) {
        mAppContext = appContext;
        mTransferListener = transferListener;
    }

    public void uploadPhotoSelection(Uri uri) {
        String imageFilePath = null;

        if (uri != null) {
            try {
                if( uri == null ) {
                    imageFilePath = uri.getPath();
                } else {
                    // get the id of the image selected by the user
                    imageFilePath = UriToPathConverter.getPath(mAppContext, uri);

                    mImageFile = new File(imageFilePath);
                    Toast.makeText(mAppContext, "Uploading...", Toast.LENGTH_SHORT).show();
                    new UploadToS3().execute(mImageFile);
                }
            } catch (Exception e) {
                Log.e("ImageUploader", "Failed to get image");
            }
        }
    }

    private class UploadToS3 extends AsyncTask<File, Integer, Long> {
        protected Long doInBackground(File... files) {
            // Create an S3 client
            // String accessKey, secretKey;

            // TODO: This is bad. Should only do this once, not every upload

            // Initialize the Amazon Cognito credentials provider
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    mAppContext,
                    "us-east-1:31ad60dc-feaf-4683-804a-04b88f007d7a", // Identity Pool ID
                    Regions.US_EAST_1 // Region
                    );
            // Initialize the Cognito Sync client
            AmazonS3 s3 = new AmazonS3Client(credentialsProvider);

            // Set the region of your S3 bucket
            s3.setRegion(Region.getRegion(Regions.US_EAST_1));

            // TODO: Use hashing to guarantee no collisions.
            String fname = "uploaded";
            fname += (int)(Math.random()*1000000000);
            mRemoteName = fname;

            TransferUtility transferUtility = new TransferUtility(s3, mAppContext);
            TransferObserver observer = transferUtility.upload(
                    "TODO: NAME",//Constants.PICTURE_BUCKET, /* The bucket to upload to */
                    fname,      /* The key for the uploaded object */
                    files[0]        /* The file where the data to upload exists */
            );

            ResponseHeaderOverrides override = new ResponseHeaderOverrides();
            override.setContentType("image/jpeg"); // TODO: Does this hold?

            // TODO: Do not need presigned URL request, use generic URL
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
                    Constants.PICTURE_BUCKET, mRemoteName);
            urlRequest.setExpiration(new Date(System.currentTimeMillis() + 3600000 * 24 * 365));  // Added an hour's worth of milliseconds to the current time.
            urlRequest.setResponseHeaders(override);
            mUploadedImageUrl = s3.generatePresignedUrl( urlRequest ).toString();
            observer.setTransferListener(mTransferListener);

            long result = 0;
            return result;
        }
    }

    public String getUploadedImageUrl() {
        return mUploadedImageUrl;
    }
}
