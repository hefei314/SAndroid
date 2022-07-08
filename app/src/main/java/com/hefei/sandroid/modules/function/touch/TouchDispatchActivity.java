package com.hefei.sandroid.modules.function.touch;

import android.view.MotionEvent;

import com.hefei.sandroid.databinding.ActivityTouchDispatchBinding;
import com.hefei.sandroid.support.base.BaseBindingActivity;
import com.orhanobut.logger.Logger;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/03/15
 *     desc  : touch dispatch
 *
 *     Activity
 *
 *     ViewGroup
 *
 *     View
 *
 * </pre>
 */
public class TouchDispatchActivity extends BaseBindingActivity<ActivityTouchDispatchBinding> {

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, true);

        mViewBinding.touchEventView.setText("测试");
    }

    @Override
    protected void updateViews() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e("TouchDispatchActivity: onTouchEvent(): ACTION_DOWN");
                //return true;
                break;
            case MotionEvent.ACTION_UP:
                Logger.e("TouchDispatchActivity: onTouchEvent(): ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e("TouchDispatchActivity: dispatchTouchEvent(): ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Logger.e("TouchDispatchActivity: dispatchTouchEvent(): ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
