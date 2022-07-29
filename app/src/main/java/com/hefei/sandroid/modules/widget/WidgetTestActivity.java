package com.hefei.sandroid.modules.widget;

import com.hefei.sandroid.databinding.ActivityWidgetTestBinding;
import com.hefei.sandroid.support.base.BaseBindingActivity;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/7/6
 *     desc  :
 * </pre>
 */
public class WidgetTestActivity extends BaseBindingActivity<ActivityWidgetTestBinding> {

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, mTitle, true);
    }

    @Override
    protected void updateViews() {

    }
}
