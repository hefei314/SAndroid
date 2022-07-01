package com.hefei.sandroid.modules.function.coordinator;

import com.hefei.sandroid.databinding.ActivityCoordinatorLayoutBinding;
import com.hefei.sandroid.support.base.BaseBindingActivity;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/25
 *     desc  :
 * </pre>
 */
public class CoordinatorLayoutActivity extends BaseBindingActivity<ActivityCoordinatorLayoutBinding> {

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, mTitle, true);
    }

    @Override
    protected void updateViews() {

    }
}
