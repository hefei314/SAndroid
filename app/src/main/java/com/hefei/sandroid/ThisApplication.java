package com.hefei.sandroid;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.hefei.sandroid.modules.widget.multiplestatus.MultipleStatusLayout;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhihu.matisse.ui.MatisseActivity;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/21
 *     desc  :
 * </pre>
 */
public class ThisApplication extends MultiDexApplication {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initConfig();
        initAutoSize();
    }

    public static Context getContext() {
        return sContext;
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        Utils.init(this);

        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter());
        }

        initMultipleStatusLayout();

        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(context));
    }

    /**
     * 初始化全局多状态布局
     */
    private void initMultipleStatusLayout() {
        MultipleStatusLayout.getConfig()
                .setEmptyView(R.layout.layout_status_empty)
                .setErrorView(R.layout.layout_status_error)
                .setLoadingView(R.layout.layout_status_loading)
                .setNoNetworkView(R.layout.layout_status_no_network)
                .setEmptyImageViewId(R.id.iv_empty)
                .setErrorImageViewId(R.id.iv_error)
                .setNoNetworkImageViewId(R.id.iv_no_network)
                .setEmptyTextViewId(R.id.tv_empty)
                .setErrorTextViewId(R.id.tv_error)
                .setNoNetworkTextViewId(R.id.tv_no_network)
                .setEmptyRetryViewId(R.id.btn_retry)
                .setErrorRetryViewId(R.id.btn_retry)
                .setNoNetworkRetryViewId(R.id.btn_retry);
    }

    /**
     * 初始化屏幕适配
     */
    private void initAutoSize() {
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(this);
        //让框架支持自定义 Fragment 的适配参数
        AutoSizeConfig.getInstance().setCustomFragment(true)
                .setExcludeFontScale(true);

        AutoSizeConfig.getInstance().getExternalAdaptManager()
                //给外部的三方库 Activity 自定义适配参数
                .addCancelAdaptOfActivity(MatisseActivity.class);
    }
}
