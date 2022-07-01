package com.hefei.sandroid.support.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hefei.sandroid.R;

import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/08/31
 *     desc  :
 * </pre>
 */
public class SimpleTextAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SimpleTextAdapter(List<String> data) {
        super(R.layout.item_simple_text, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String item) {
        baseViewHolder.setText(R.id.text, item);
    }
}
