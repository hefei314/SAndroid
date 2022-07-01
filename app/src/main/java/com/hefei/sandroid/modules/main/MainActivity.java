package com.hefei.sandroid.modules.main;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.navigation.NavigationBarView;
import com.hefei.sandroid.R;
import com.hefei.sandroid.databinding.ActivityMainBinding;
import com.hefei.sandroid.modules.main.animate.AnimateFragment;
import com.hefei.sandroid.modules.main.function.FunctionFragment;
import com.hefei.sandroid.modules.main.widget.WidgetFragment;
import com.hefei.sandroid.support.adapter.ViewPagerAdapter;
import com.hefei.sandroid.support.base.BaseBindingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/01/09
 *     desc  :
 * </pre>
 */
public class MainActivity extends BaseBindingActivity<ActivityMainBinding>
        implements NavigationBarView.OnItemSelectedListener {

    private long mExitTime = 0;

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, false);

        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(new FunctionFragment());
        mFragments.add(new WidgetFragment());
        mFragments.add(new AnimateFragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, mFragments);
        mViewBinding.viewPager.setAdapter(adapter);
        // 1.设置预加载数量，预先创建出所有的fragment，防止切换造成的频繁销毁和创建
        // 2.在fragment#OnResume()中控制是否需要调用updateViews()
        mViewBinding.viewPager.setOffscreenPageLimit(mFragments.size());

        mViewBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                initToolbarTitle(position);
                mViewBinding.bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        mViewBinding.bottomNavigationView.setOnItemSelectedListener(this);

        initToolbarTitle(0);
    }

    @Override
    protected void updateViews() {

    }

    private void initToolbarTitle(int position) {
        switch (position) {
            case 0:
                setToolbarTitle("Function");
                break;
            case 1:
                setToolbarTitle("Widget");
                break;
            case 2:
                setToolbarTitle("Animate");
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.function) {
            mViewBinding.viewPager.setCurrentItem(0);
        } else if (menuItem.getItemId() == R.id.widget) {
            mViewBinding.viewPager.setCurrentItem(1);
        } else if (menuItem.getItemId() == R.id.animate) {
            mViewBinding.viewPager.setCurrentItem(2);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ToastUtils.showLong("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            AppUtils.exitApp();
        }
    }
}