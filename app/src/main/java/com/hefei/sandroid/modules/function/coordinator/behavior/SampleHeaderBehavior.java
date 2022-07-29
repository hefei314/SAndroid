package com.hefei.sandroid.modules.function.coordinator.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/06/03
 *     desc  :
 * </pre>
 */
public class SampleHeaderBehavior extends CoordinatorLayout.Behavior<TextView> {

    public SampleHeaderBehavior() {
    }

    public SampleHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if (target instanceof RecyclerView) {
            if (isCoordinatorLayoutConsumed(child, target, dy)) {
                float finalY = child.getTranslationY() - dy;
                if (finalY < -child.getHeight()) {
                    finalY = -child.getHeight();
                } else if (finalY > 0) {
                    finalY = 0;
                }
                child.setTranslationY(finalY);

                consumed[1] = dy;
            }
        }
    }

    private boolean isCoordinatorLayoutConsumed(@NonNull TextView child, @NonNull View target, int dy) {
        // 上滑且到临界点，RecyclerView 开始消费
        if (dy > 0 && child.getTranslationY() == -child.getHeight()) {
            return false;
        }

        // 下滑且 RecyclerView 未滑动到第一个位置，RecyclerView 继续消费
        if (dy < 0 && !isScrollToRecycleViewFirstPosition(target)) {
            return false;
        }

        return true;
    }

    private boolean isScrollToRecycleViewFirstPosition(@NonNull View target) {
        return ((LinearLayoutManager) ((RecyclerView) target).getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0;
    }
}
