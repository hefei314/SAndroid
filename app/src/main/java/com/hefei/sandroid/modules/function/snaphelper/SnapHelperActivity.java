package com.hefei.sandroid.modules.function.snaphelper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;

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

        mViewBinding.rvImage.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.rvImage.setAdapter(imageListAdapter);

        /*
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(mViewBinding.rvImage);
        */

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mViewBinding.rvImage);

        imageListAdapter.setNewInstance(DataRepository.getInstance().getImageList());
    }

    @Override
    protected void updateViews() {

    }

}
