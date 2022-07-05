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
public class FlipPageTransformer implements ViewPager2.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) {
            page.setAlpha(0);
            page.setRotationY(0);
        } else if (position <= 0) {
            page.setAlpha(1 + position);
            page.setRotationY(180 * position);
            page.setTranslationX(-pageWidth * position);
        } else if (position <= 1) {
            page.setAlpha(1 - position);
            page.setRotationY(180 * position);
            page.setTranslationX(-pageWidth * position);
        } else {
            page.setAlpha(0);
            page.setRotationY(0);
        }
    }
}
