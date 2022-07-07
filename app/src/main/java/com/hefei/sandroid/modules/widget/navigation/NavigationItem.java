package com.hefei.sandroid.modules.widget.navigation;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.core.view.ViewCompat;

import com.blankj.utilcode.util.ConvertUtils;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/06/04
 *     desc  :
 * </pre>
 */
public class NavigationItem extends BaseNavigationItem {

    private TextView text;
    private ImageView image;

    private String title;
    private int icon, checkedIcon;
    private int titleColor, checkedTitleColor;

    public NavigationItem(Context context, String title, @DrawableRes int icon, @DrawableRes int checkedIcon, int titleColor, int checkedTitleColor) {
        super(context);

        this.title = title;
        this.icon = icon;
        this.checkedIcon = checkedIcon;
        this.titleColor = titleColor;
        this.checkedTitleColor = checkedTitleColor;

        init();
    }

    private void init() {
        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.CENTER);

        image = new ImageView(mContext);
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(24), ConvertUtils.dp2px(24));
        imageLayoutParams.topMargin = ConvertUtils.dp2px(4);
        image.setLayoutParams(imageLayoutParams);

        this.addView(image);

        text = new TextView(mContext);
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayoutParams.topMargin = ConvertUtils.dp2px(2);
        textLayoutParams.bottomMargin = ConvertUtils.dp2px(4);
        text.setLayoutParams(textLayoutParams);
        text.setTextSize(10);

        this.addView(text);

        text.setText(title);
        text.setTextColor(titleColor);
        image.setImageResource(icon);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        text.setTextColor(checked ? checkedTitleColor : titleColor);
        image.setImageResource(checked ? checkedIcon : icon);

        ViewCompat.animate(this)
                .scaleX(checked ? 1.0f : 0.9f)
                .scaleY(checked ? 1.0f : 0.9f)
                .start();
    }
}
