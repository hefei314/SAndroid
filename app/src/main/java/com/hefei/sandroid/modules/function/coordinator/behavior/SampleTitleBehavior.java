package com.hefei.sandroid.modules.function.coordinator.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/06/03
 *     desc  :
 * </pre>
 */
public class SampleTitleBehavior extends CoordinatorLayout.Behavior<View> {

    private float deltaY;

    public SampleTitleBehavior() {
    }

    public SampleTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (deltaY == 0) {
            deltaY = dependency.getY() - child.getHeight();
        }

        float dy = dependency.getY() - child.getHeight();
        Logger.e("dy: " + dy);
        dy = dy < 0 ? 0 : dy;
        float y = -(dy / deltaY) * child.getHeight();
        child.setTranslationY(y);

        float alpha = 1 - (dy / deltaY);
        child.setAlpha(alpha);

        return true;
    }
}
