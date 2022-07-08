package com.hefei.sandroid.modules.function.touch.child;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.orhanobut.logger.Logger;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/03/15
 *     desc  :
 * </pre>
 */
public class TouchDispatchView extends AppCompatTextView {

    public TouchDispatchView(@NonNull Context context) {
        super(context);
    }

    public TouchDispatchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchDispatchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * onTouchEvent
     * default => 继续消费
     * true    => 消费拦截，不继续向上传递
     *            View#onTouchEvent(MotionEvent) 中 MotionEvent.ACTION_DOWN 返回 true：
     *              MotionEvent.ACTION_UP事件：
     *                  dispatchTouchEvent也截断到该层级
     *              MotionEvent.ACTION_UP事件：
     *                  Activity#dispatchTouchEvent(MotionEvent)
     *                  ViewGroup#dispatchTouchEvent(MotionEvent)
     *                  View#dispatchTouchEvent(MotionEvent)
     *                  View#onTouchEvent(MotionEvent)
     *                  Activity#onTouchEvent(MotionEvent)
     *            View#onTouchEvent(MotionEvent) 中 MotionEvent.ACTION_DOWN 返回 false：
     *              MotionEvent.ACTION_UP事件：
     *                  Activity#dispatchTouchEvent(MotionEvent)
     *                  Activity#onTouchEvent(MotionEvent)
     * false   => 继续消费
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e("TouchDispatchView: onTouchEvent(): ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Logger.e("TouchDispatchView: onTouchEvent(): ACTION_UP");
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
                Logger.e("TouchDispatchView: dispatchTouchEvent(): ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Logger.e("TouchDispatchView: dispatchTouchEvent(): ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
