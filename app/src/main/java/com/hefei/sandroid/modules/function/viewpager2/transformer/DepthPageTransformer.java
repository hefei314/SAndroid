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
public class DepthPageTransformer implements ViewPager2.PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) {
            // 非临近左侧页面
            page.setAlpha(0);
        } else if (position <= 0) {
            // 从右向左滑: 当前页面[0->-1]
            // 从左向右滑: 临近左侧页面[-1->0]
            page.setAlpha(1 + position);
            page.setTranslationX(0);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 + Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position <= 1) {
            // 从右向左滑: 临近右侧页面[1->0]
            // 从左向右滑: 当前页面[0->1]
            page.setAlpha(1 - position);
            page.setTranslationX(-pageWidth * position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else {
            // 非临近右侧页面
            page.setAlpha(0);
        }
    }
}
