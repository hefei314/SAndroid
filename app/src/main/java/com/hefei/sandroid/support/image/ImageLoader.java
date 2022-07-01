package com.hefei.sandroid.support.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.blankj.utilcode.util.NetworkUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hefei.sandroid.R;

/**
 * <pre>
 *     author: hefei
 *     time  : 2018/11/30
 *     desc  :
 * </pre>
 */
public final class ImageLoader {

    private ImageLoader() {
        throw new RuntimeException("ImageLoader cannot be initialized!");
    }

    public static void loadCenterCrop(Context context, String url, ImageView view) {
        loadCenterCrop(context, url, view, R.mipmap.default_image);
    }

    /**
     * 按比例放大，不缩小，截图
     */
    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId) {
        if (NetworkUtils.isConnected()) {
            Glide.with(context).load(url)
                    .centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(defaultResId)
                    .error(defaultResId)
                    .into(view);
        } else {
            view.setImageResource(defaultResId);
        }
    }

    /**
     * 按比例缩放，不缩小，截图，设置图片大小
     */
    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId,
                                      int width, int height) {
        if (NetworkUtils.isConnected()) {
            Glide.with(context).load(url)
                    .centerCrop()
                    .dontAnimate()
                    .override(width, height)
                    .placeholder(defaultResId)
                    .error(defaultResId)
                    .into(view);
        } else {
            view.setImageResource(defaultResId);
        }
    }

    /**
     * 按比例缩放，居中显示全图
     */
    public static void loadFitCenter(Context context, String url, ImageView view) {
        loadFitCenter(context, url, view, R.mipmap.default_image);
    }

    /**
     * 按比例缩放，居中显示全图
     */
    public static void loadFitCenter(Context context, String url, ImageView view, int defaultResId) {
        if (NetworkUtils.isConnected()) {
            Glide.with(context).load(url)
                    .fitCenter()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(defaultResId)
                    .error(defaultResId)
                    .into(view);
        } else {
            view.setImageResource(defaultResId);
        }
    }

    /**
     * 按比例缩放，居中显示全图，设置图片大小
     */
    public static void loadFitCenter(Context context, String url, ImageView view, int defaultResId,
                                     int width, int height) {
        if (NetworkUtils.isConnected()) {
            Glide.with(context).load(url)
                    .fitCenter()
                    .dontAnimate()
                    .override(width, height)
                    .placeholder(defaultResId)
                    .error(defaultResId)
                    .into(view);
        } else {
            view.setImageResource(defaultResId);
        }
    }

    /**
     * 加载本地图片,按比例放大，不缩小，截图
     */
    public static void loadLocalCenterCrop(Context context, Uri uri, ImageView view, int defaultResId) {
        Glide.with(context)
                .load(uri)
                .centerCrop()
                .dontAnimate()
                .placeholder(defaultResId)
                .error(defaultResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }
}
