package com.hefei.sandroid.modules.function.snaphelper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hefei.sandroid.R;
import com.hefei.sandroid.databinding.ActivitySnapHelperBinding;
import com.hefei.sandroid.support.base.BaseBindingActivity;
import com.hefei.sandroid.support.global.DataRepository;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/25
 *     desc  : RecyclerView's SnapHelper
 * </pre>
 */
public class SnapHelperActivity extends BaseBindingActivity<ActivitySnapHelperBinding> {

    private BaseQuickAdapter<Integer, BaseViewHolder> imageListAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_image_list2) {
        @Override
        protected void convert(BaseViewHolder helper, Integer item) {
            helper.setImageResource(R.id.iv_image, item);
        }
    };

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, mTitle, true);

        mViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mViewBinding.recyclerView.setAdapter(imageListAdapter);

//        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(mViewBinding.recyclerView);

//        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
//        pagerSnapHelper.attachToRecyclerView(mViewBinding.recyclerView);

        GallerySnapHelper gallerySnapHelper = new GallerySnapHelper();
        gallerySnapHelper.attachToRecyclerView(mViewBinding.recyclerView);

        imageListAdapter.setNewInstance(DataRepository.getInstance().getImageList());
    }

    @Override
    protected void updateViews() {

    }

}
