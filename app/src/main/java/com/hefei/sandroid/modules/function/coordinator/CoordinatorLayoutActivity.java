package com.hefei.sandroid.modules.function.coordinator;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hefei.sandroid.databinding.ActivityCoordinatorLayoutBinding;
import com.hefei.sandroid.support.adapter.SimpleTextAdapter;
import com.hefei.sandroid.support.base.BaseBindingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/25
 *     desc  :
 *
 *     app:layout_scrollFlags="scroll"
 *     => 向上滚动时：先滚动 AppBarLayout 至完全消失，再开始滚动 Scrolling View
 *        向下滚动时：先滚动 Scrolling View 至完全出现，再开始滚动 AppBarLayout 至完全出现
 *
 *     app:layout_scrollFlags="scroll|enterAlways"
 *     => 向上滚动时：先滚动 AppBarLayout 至完全消失，再开始滚动 Scrolling View
 *  *     向下滚动时：先滚动 AppBarLayout 至完全出现 ，再开始滚动 Scrolling View 至完全出现
 *
 *     android:minHeight="10dp"
 *     app:layout_scrollFlags="scroll|enterAlways|enterAlwaysCollapsed"
 *     => 向上滚动时：先滚动 AppBarLayout 至完全消失，再开始滚动 Scrolling View
 *  *     向下滚动时：先滚动 AppBarLayout 至最小高度 ，再开始滚动 Scrolling View 至完全出现 ，最后再滚动 AppBarLayout 至完全出现
 *
 *     app:layout_scrollFlags="scroll|exitUntilCollapsed"
 *     => 向上滚动时：先滚动 AppBarLayout 至最小高度，再开始滚动 Scrolling View 至完全出现
 *     => 向下滚动时：先滚动 Scrolling View 至完全出现 ，再开始滚动最小高度的 AppBarLayout 至完全出现
 *
 * </pre>
 */
public class CoordinatorLayoutActivity extends BaseBindingActivity<ActivityCoordinatorLayoutBinding> {

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, mTitle, true);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item-" + i);
        }

        SimpleTextAdapter simpleTextAdapter = new SimpleTextAdapter(list);
        mViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mViewBinding.recyclerView.setAdapter(simpleTextAdapter);
    }

    @Override
    protected void updateViews() {

    }
}
