package com.hefei.sandroid.support.image;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;

import com.hefei.sandroid.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/08/04
 *     desc  :
 * </pre>
 */
public class PictureSelector {

    public static final int REQUEST_CODE_CHOOSE_All = 101;
    public static final int REQUEST_CODE_CHOOSE_IMAGE = 102;
    public static final int REQUEST_CODE_CHOOSE_VIDEO = 103;

    public static void chooseSingleAll(Activity activity) {
        chooseSingleAll(activity, REQUEST_CODE_CHOOSE_All);
    }

    public static void chooseSingleAll(Activity activity, int requestCode) {
        chooseAll(activity, true, false, 1, requestCode);
    }

    public static void chooseMultiAll(Activity activity, int maxSelectable) {
        chooseMultiAll(activity, maxSelectable, REQUEST_CODE_CHOOSE_All);
    }

    public static void chooseMultiAll(Activity activity, int maxSelectable, int requestCode) {
        chooseAll(activity, true, true, maxSelectable, requestCode);
    }

    public static void chooseAll(Activity activity, boolean captureEnable, boolean countable, int maxSelectable, int requestCode) {
        Matisse.from(activity)
                .choose(MimeType.ofAll())
                .countable(countable)
                .maxSelectable(maxSelectable)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .capture(captureEnable)
                .captureStrategy(new CaptureStrategy(true, "com.hefei.sandroid.fileprovider", "SAndroid"))
                .imageEngine(new GlideEngine())
                .theme(R.style.Matisse_Dracula)
                .forResult(requestCode);
    }

    public static void chooseSingleImage(Activity activity) {
        chooseSingleImage(activity, REQUEST_CODE_CHOOSE_IMAGE);
    }

    public static void chooseSingleImage(Activity activity, int requestCode) {
        chooseImage(activity, true, false, 1, requestCode);
    }

    public static void chooseMultiImage(Activity activity, int maxSelectable) {
        chooseMultiImage(activity, maxSelectable, REQUEST_CODE_CHOOSE_IMAGE);
    }

    public static void chooseMultiImage(Activity activity, int maxSelectable, int requestCode) {
        chooseImage(activity, true, true, maxSelectable, requestCode);
    }

    public static void chooseImage(Activity activity, boolean captureEnable, boolean countable, int maxSelectable, int requestCode) {
        Matisse.from(activity)
                .choose(MimeType.ofImage())
                .showSingleMediaType(true)
                .countable(countable)
                .maxSelectable(maxSelectable)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .capture(captureEnable)
                .captureStrategy(new CaptureStrategy(true, "com.hefei.sandroid.fileprovider", "SAndroid"))
                .imageEngine(new GlideEngine())
                .theme(R.style.Matisse_Dracula)
                .forResult(requestCode);
    }

    public static void chooseSingleVideo(Activity activity) {
        chooseSingleVideo(activity, REQUEST_CODE_CHOOSE_VIDEO);
    }

    public static void chooseSingleVideo(Activity activity, int requestCode) {
        chooseVideo(activity, true, false, 1, requestCode);
    }

    public static void chooseMultiVideo(Activity activity, int maxSelectable) {
        chooseMultiVideo(activity, maxSelectable, REQUEST_CODE_CHOOSE_VIDEO);
    }

    public static void chooseMultiVideo(Activity activity, int maxSelectable, int requestCode) {
        chooseVideo(activity, true, true, maxSelectable, requestCode);
    }

    public static void chooseVideo(Activity activity, boolean captureEnable, boolean countable, int maxSelectable, int requestCode) {
        Matisse.from(activity)
                .choose(MimeType.ofVideo())
                .showSingleMediaType(true)
                .countable(countable)
                .maxSelectable(maxSelectable)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .capture(captureEnable)
                .captureStrategy(new CaptureStrategy(true, "com.hefei.sandroid.fileprovider", "SAndroid"))
                .imageEngine(new GlideEngine())
                .theme(R.style.Matisse_Dracula)
                .forResult(requestCode);
    }

    public static List<Uri> obtainResult(Intent data) {
        return Matisse.obtainResult(data);
    }

    public static List<String> obtainPathResult(Intent data) {
        return Matisse.obtainPathResult(data);
    }
}
