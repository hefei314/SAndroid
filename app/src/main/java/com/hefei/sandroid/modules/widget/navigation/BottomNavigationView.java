package com.hefei.sandroid.modules.widget.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/06/04
 *     desc  :
 * </pre>
 */
public class BottomNavigationView extends LinearLayout {

    private List<BaseNavigationItem> navigationItems = new ArrayList<>();

    private OnNavigationItemSelectedListener onNavigationItemSelectedListener;

    public BottomNavigationView(Context context) {
        super(context);
    }

    public BottomNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void addNavigationItem(BaseNavigationItem navigationItem) {
        this.navigationItems.add(navigationItem);

        navigationItem.setOnClickListener(v -> {
            if (!navigationItem.isChecked()) {
                if (onNavigationItemSelectedListener != null) {
                    onNavigationItemSelectedListener.onNavigationItemSelected(this.navigationItems.indexOf(navigationItem));
                }
            }
        });

        navigationItem.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        this.addView(navigationItem);
    }

    public void addNavigationItems(List<BaseNavigationItem> navigationItems) {
        this.navigationItems.addAll(navigationItems);

        for (BaseNavigationItem item : navigationItems) {
            addNavigationItem(item);
        }
    }

    public void setNavigationItems(List<BaseNavigationItem> navigationItems) {
        this.navigationItems.clear();
        this.navigationItems.addAll(navigationItems);

        for (BaseNavigationItem item : navigationItems) {
            addNavigationItem(item);
        }
    }

    public void setCurrentNavigationItem(int position) {
        if (position < navigationItems.size()) {
            for (BaseNavigationItem item : navigationItems) {
                item.setChecked(false);
            }
            navigationItems.get(position).setChecked(true);
        }
    }

    public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
        this.onNavigationItemSelectedListener = listener;
    }

    public interface OnNavigationItemSelectedListener {

        void onNavigationItemSelected(int position);

    }
}
