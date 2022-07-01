package com.hefei.sandroid.modules.main.widget;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.DebouncingUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.hefei.sandroid.databinding.FragmentWidgetBinding;
import com.hefei.sandroid.support.adapter.ViewRouteAdapter;
import com.hefei.sandroid.support.base.BaseBindingFragment;
import com.hefei.sandroid.support.entity.ViewRoute;
import com.hefei.sandroid.support.utils.JumpUtils;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/01/09
 *     desc  :
 * </pre>
 */
public class WidgetFragment extends BaseBindingFragment<FragmentWidgetBinding> {

    private ViewRouteAdapter viewRouteAdapter;

    @Override
    protected void initViews() {
        viewRouteAdapter = new ViewRouteAdapter(GsonUtils.fromJson(
                ResourceUtils.readAssets2String("router_widget.json"),
                GsonUtils.getListType(ViewRoute.class)));
        viewRouteAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (DebouncingUtils.isValid(view)) {
                JumpUtils.startActivity(mContext, viewRouteAdapter.getItem(position));
            }
        });
        mViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mViewBinding.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mViewBinding.recyclerView.setAdapter(viewRouteAdapter);
    }

    @Override
    protected void updateViews() {

    }
}
