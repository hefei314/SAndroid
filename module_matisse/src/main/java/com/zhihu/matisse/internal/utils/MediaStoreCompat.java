/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhihu.matisse.internal.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MediaStoreCompat {

    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;
    private CaptureStrategy mCaptureStrategy;
    private Uri mCurrentPhotoUri;
    private String mCurrentPhotoPath;

    public MediaStoreCompat(Activity activity) {
        mContext = new WeakReference<>(activity);
        mFragment = null;
    }

    public MediaStoreCompat(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    /**
     * Checks whether the device has a camera feature or not.
     *
     * @param context a context to check for camera feature.
     * @return true if the device has a camera feature. false otherwise.
     */
    public static boolean hasCameraFeature(Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void setCaptureStrategy(CaptureStrategy strategy) {
        mCaptureStrategy = strategy;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void dispatchCaptureIntent(Context context, String action, int requestCode) {
        Intent captureIntent = new Intent(action);
        if (captureIntent.resolveActivity(context.getPackageManager()) != null) {

            String displayName = getDisplayName(action);

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, isCaptureVideo(action) ? "video/mp4" : "image/jpeg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + mCaptureStrategy.directory);
            } else {
                String path = getFilePath();
                File fileDir = new File(path);
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }
                contentValues.put(MediaStore.MediaColumns.DATA, path + "/" + displayName);
            }

            mCurrentPhotoUri = mContext.get().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            mCurrentPhotoPath = getRealPathFromUri(mCurrentPhotoUri);

            if (mCurrentPhotoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                if (mFragment != null) {
                    mFragment.get().startActivityForResult(captureIntent, requestCode);
                } else {
                    mContext.get().startActivityForResult(captureIntent, requestCode);
                }
            }
        }
    }

    private void createImageContentValues() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        ContentValues contentValues = new ContentValues();

        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, String.format("IMG_%s.jpg", timeStamp));
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/*");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + mCaptureStrategy.directory);

        mCurrentPhotoUri = mContext.get().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        mCurrentPhotoPath = getRealPathFromUri(mCurrentPhotoUri);
    }

    private void createVideoContentValues() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        ContentValues contentValues = new ContentValues();

        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, String.format("VID_%s.mp4", timeStamp));
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/*");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, String.format("%s/%s", Environment.DIRECTORY_MOVIES, mCaptureStrategy.directory));

        mCurrentPhotoUri = mContext.get().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        mCurrentPhotoPath = getRealPathFromUri(mCurrentPhotoUri);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("IMG_%s.jpg", timeStamp);
        File storageDir;
        if (mCaptureStrategy.isPublic) {
            storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!storageDir.exists())
                storageDir.mkdirs();
        } else {
            storageDir = mContext.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        if (mCaptureStrategy.directory != null) {
            storageDir = new File(storageDir, mCaptureStrategy.directory);
            if (!storageDir.exists())
                storageDir.mkdirs();
        }

        // Avoid joining path components manually
        File tempFile = new File(storageDir, imageFileName);

        // Handle the situation that user's external storage is not ready
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            mCurrentPhotoPath = "";
            mCurrentPhotoUri = null;
        } else {
            mCurrentPhotoPath = tempFile.getAbsolutePath();
            mCurrentPhotoUri = FileProvider.getUriForFile(mContext.get(), mCaptureStrategy.authority, tempFile);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createVideoFile() {
        // Create an video file name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String videoFileName = String.format("VID_%s.mp4", timeStamp);
        File storageDir;
        if (mCaptureStrategy.isPublic) {
            storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!storageDir.exists())
                storageDir.mkdirs();
        } else {
            storageDir = mContext.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        if (mCaptureStrategy.directory != null) {
            storageDir = new File(storageDir, mCaptureStrategy.directory);
            if (!storageDir.exists())
                storageDir.mkdirs();
        }

        // Avoid joining path components manually
        File tempFile = new File(storageDir, videoFileName);

        // Handle the situation that user's external storage is not ready
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            mCurrentPhotoPath = "";
            mCurrentPhotoUri = null;
        } else {
            mCurrentPhotoPath = tempFile.getAbsolutePath();
            mCurrentPhotoUri = FileProvider.getUriForFile(mContext.get(), mCaptureStrategy.authority, tempFile);
        }
    }

    private boolean isCaptureVideo(String action) {
        return MediaStore.ACTION_VIDEO_CAPTURE.equalsIgnoreCase(action);
    }

    private String getDisplayName(String action) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return isCaptureVideo(action) ? String.format("VID_%s.mp4", timeStamp) : String.format("IMG_%s.jpg", timeStamp);
    }

    private String getFilePath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(Environment.getExternalStorageDirectory().getAbsolutePath())
                .append("/")
                .append(Environment.DIRECTORY_PICTURES);
        if (mCaptureStrategy.directory != null && !"".equalsIgnoreCase(mCaptureStrategy.directory)) {
            stringBuilder
                    .append("/")
                    .append(mCaptureStrategy.directory);
        }

        return stringBuilder.toString();
    }

    private String getRealPathFromUri(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(mContext.get(), contentUri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return "";
    }

    public Uri getCurrentPhotoUri() {
        return mCurrentPhotoUri;
    }

    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }
}
