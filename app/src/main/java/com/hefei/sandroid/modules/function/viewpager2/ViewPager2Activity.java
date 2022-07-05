package com.hefei.sandroid.modules.function.viewpager2;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.ConvertUtils;
import com.hefei.sandroid.R;
import com.hefei.sandroid.databinding.ActivityViewPager2Binding;
import com.hefei.sandroid.modules.common.NormalFragment;
import com.hefei.sandroid.support.adapter.ViewPagerAdapter;
import com.hefei.sandroid.support.base.BaseBindingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/25
 *     desc  : viewPager2
 * </pre>
 */
public class ViewPager2Activity extends BaseBindingActivity<ActivityViewPager2Binding> {

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, mTitle, true);

        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(NormalFragment.newInstance(0));
        mFragments.add(NormalFragment.newInstance(1));
        mFragments.add(NormalFragment.newInstance(2));
        mFragments.add(NormalFragment.newInstance(3));
        mFragments.add(NormalFragment.newInstance(4));

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, mFragments);
        mViewBinding.viewPager.setAdapter(pagerAdapter);
        // 1.设置预加载数量，预先创建出所有的fragment，防止切换造成的频繁销毁和创建
        // 2.在fragment#OnResume()中控制是否需要调用updateViews()
        mViewBinding.viewPager.setOffscreenPageLimit(mFragments.size());

        mViewBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        CompositePageTransformer transformer = new CompositePageTransformer();
        //transformer.addTransformer(new ZoomOutPageTransformer());
        transformer.addTransformer(new MarginPageTransformer(ConvertUtils.dp2px(10)));

        RecyclerView recyclerView = (RecyclerView) mViewBinding.viewPager.getChildAt(0);
        recyclerView.setPadding(ConvertUtils.dp2px(20), 0, ConvertUtils.dp2px(20), 0);
        recyclerView.setClipToPadding(false);

        mViewBinding.viewPager.setPageTransformer(transformer);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_pager2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_vertical) {
            mViewBinding.viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            return true;
        } else if (item.getItemId() == R.id.menu_horizontal) {
            mViewBinding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            return true;
        } else if (item.getItemId() == R.id.menu_fake_drag) {
            mViewBinding.viewPager.beginFakeDrag();
            if (mViewBinding.viewPager.fakeDragBy(-200)) {
                mViewBinding.viewPager.endFakeDrag();
            }
            return true;
        } else if (item.getItemId() == R.id.menu_user_input_enable) {
            mViewBinding.viewPager.setUserInputEnabled(true);
            return true;
        } else if (item.getItemId() == R.id.menu_user_input_disable) {
            mViewBinding.viewPager.setUserInputEnabled(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
