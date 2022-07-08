package com.hefei.sandroid.modules.function.touch.child;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/03/15
 *     desc  :
 * </pre>
 */
public class TouchDispatchLayout extends LinearLayout {

    public TouchDispatchLayout(Context context) {
        super(context);
    }

    public TouchDispatchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchDispatchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchDispatchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * onTouchEvent
     * default => 继续消费
     * true    => 消费拦截，不继续向上传递
     * false   => 继续消费
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e("TouchDispatchLayout: onTouchEvent(): ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Logger.e("TouchDispatchLayout: onTouchEvent(): ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * dispatchTouchEvent
     * default => 继续分发
     * true    => 事件结束，不消费事件
     * false   => 事件拦截，上一层级onTouchEvent()开始消费
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e("TouchDispatchLayout: dispatchTouchEvent(): ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.e("TouchDispatchLayout: dispatchTouchEvent(): ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Logger.e("TouchDispatchLayout: dispatchTouchEvent(): ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * onInterceptTouchEvent
     * true    => 事件拦截，本层级onTouchEvent()开始消费
     * false   => 继续分发
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
