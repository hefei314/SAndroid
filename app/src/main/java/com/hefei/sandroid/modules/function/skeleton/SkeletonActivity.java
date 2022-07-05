package com.hefei.sandroid.modules.function.skeleton;

import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.hefei.sandroid.R;
import com.hefei.sandroid.databinding.ActivitySkeletonBinding;
import com.hefei.sandroid.support.adapter.SimpleTextAdapter;
import com.hefei.sandroid.support.base.BaseBindingActivity;
import com.hefei.sandroid.support.global.DataRepository;

import java.util.Objects;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/25
 *     desc  : Skeleton
 * </pre>
 */
public class SkeletonActivity extends BaseBindingActivity<ActivitySkeletonBinding> {

    private SkeletonScreen skeletonScreen;

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, mTitle, true);

        SimpleTextAdapter simpleTextAdapter = new SimpleTextAdapter(null);

        mViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.recyclerView.setAdapter(simpleTextAdapter);

        skeletonScreen = Skeleton.bind(mViewBinding.recyclerView)
                .adapter(simpleTextAdapter)
                .load(R.layout.item_skeleton_simple_text)
                .count(20)
                .show();

        new Handler(Objects.requireNonNull(Looper.myLooper()))
                .postDelayed(() -> {
                    skeletonScreen.hide();
                    simpleTextAdapter.setNewInstance(DataRepository.getInstance().getSimpleTextList());
                }, 3000);
    }

    @Override
    protected void updateViews() {

    }
}
