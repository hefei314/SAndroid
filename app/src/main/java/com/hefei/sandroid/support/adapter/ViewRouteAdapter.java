package com.hefei.sandroid.support.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hefei.sandroid.R;
import com.hefei.sandroid.support.entity.ViewRoute;

import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/04/15
 *     desc  :
 * </pre>
 */
public class ViewRouteAdapter extends BaseQuickAdapter<ViewRoute, BaseViewHolder> {

    public ViewRouteAdapter(@Nullable List<ViewRoute> data) {
        super(R.layout.item_view_route, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ViewRoute item) {
        helper.setText(R.id.text, item.getTitle());
    }
}
