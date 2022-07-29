package com.hefei.sandroid.modules.function.coordinator.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/06/04
 *     desc  :
 * </pre>
 */
public class SampleContentBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    public SampleContentBehavior() {
    }

    public SampleContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        float y = dependency.getHeight() + dependency.getTranslationY();
        if (y < 0) {
            y = 0;
        }
        child.setY(y);
        return true;
    }
}
