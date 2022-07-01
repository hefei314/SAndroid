package com.hefei.sandroid.modules.function.pictureselector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hefei.sandroid.R;
import com.hefei.sandroid.support.entity.MultimediaList;
import com.hefei.sandroid.support.image.ImageLoader;

import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/25
 *     desc  :
 * </pre>
 */
public class MultimediaListAdapter extends BaseQuickAdapter<MultimediaList, BaseViewHolder> {

    public MultimediaListAdapter(@Nullable List<MultimediaList> data) {
        super(R.layout.item_multimedia_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, MultimediaList item) {
        ImageLoader.loadLocalCenterCrop(getContext(), item.getUri(), baseViewHolder.getView(R.id.iv_multimedia), R.mipmap.default_image);
        baseViewHolder.setGone(R.id.iv_play, item.getType() == 1);
    }
}
