package com.hefei.sandroid.modules.function.viewpager2.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/7/1
 *     desc  :
 * </pre>
 */
public class ScaleInPageTransformer implements ViewPager2.PageTransformer {

    private static final float MIN_SCALE = 0.85f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 0) {
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position <= 1) {
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
