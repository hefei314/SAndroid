package com.hefei.sandroid.modules.function.skeleton;

import com.hefei.sandroid.databinding.ActivitySkeletonBinding;
import com.hefei.sandroid.support.base.BaseBindingActivity;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/25
 *     desc  :
 * </pre>
 */
public class SkeletonActivity extends BaseBindingActivity<ActivitySkeletonBinding> {

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, mTitle, true);
    }

    @Override
    protected void updateViews() {

    }
}
