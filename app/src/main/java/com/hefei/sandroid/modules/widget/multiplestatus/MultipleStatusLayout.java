package com.hefei.sandroid.modules.widget.multiplestatus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * <pre>
 *     author: hefei
 *     time  : 2018/10/09
 *     desc  : 加载中、无数据、无网络、出错四种状态布局
 *
 *     全局配置:
 *
 *     默认空视图ID
 *     {@link Config#setEmptyView(int)}
 *     默认错误视图ID
 *     {@link Config#setErrorView(int)}
 *     默认加载视图ID
 *     {@link Config#setLoadingView(int)}
 *     默认无网络视图ID
 *     {@link Config#setNoNetworkView(int)}
 *
 *     默认空视图图片ID
 *     {@link Config#setEmptyImageViewId(int)}
 *     默认错误视图图片ID
 *     {@link Config#setErrorImageViewId(int)}
 *     默认无网络视图图片ID
 *     {@link Config#setNoNetworkImageViewId(int)}
 *
 *     默认空视图文本ID
 *     {@link Config#setEmptyTextViewId(int)}
 *     默认错误视图文本ID
 *     {@link Config#setErrorTextViewId(int)}
 *     默认无网络视图文本ID
 *     {@link Config#setNoNetworkTextViewId(int)}
 *
 *     默认空视图重试ID
 *     {@link Config#setEmptyRetryViewId(int)}
 *     默认错误视图重试ID
 *     {@link Config#setErrorRetryViewId(int)}
 *     默认无网络视图重试ID
 *     {@link Config#setNoNetworkRetryViewId(int)}
 *
 *     页面配置项:
 *
 *     设置自定义页面
 *         若是沿用全局配置的图片ID、文本ID、重试ID，
 *         可通过设置默认页面的图片、文本、重试监听的方法设置页面，否则需要自己实现
 *
 *     设置默认页面的图片、文本、重试监听
 *
 * </pre>
 */
@SuppressWarnings("unused")
public class MultipleStatusLayout extends FrameLayout {

    private static final String TAG = "MultipleStatusLayout";

    public static final LayoutParams DEFAULT_LAYOUT_PARAMS =
            new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);

    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;

    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private View mNoNetworkView;

    private int mViewStatus;
    private LayoutInflater mInflater;
    private OnClickListener mOnRetryClickListener;
    private OnClickListener mOnEmptyRetryClickListener;
    private OnClickListener mOnErrorRetryClickListener;
    private OnClickListener mOnNoNetworkRetryClickListener;

    private final ArrayList<Integer> mOtherIds = new ArrayList<>();

    public MultipleStatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public MultipleStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("MultipleStatusLayout can host only one direct child");
        }
        showContent();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clear(mEmptyView, mLoadingView, mErrorView, mNoNetworkView);
        if (mOtherIds.size() > 0) {
            mOtherIds.clear();
        }
        if (mOnRetryClickListener != null) {
            mOnRetryClickListener = null;
        }
        if (mOnEmptyRetryClickListener != null) {
            mOnEmptyRetryClickListener = null;
        }
        if (mOnErrorRetryClickListener != null) {
            mOnErrorRetryClickListener = null;
        }
        if (mOnNoNetworkRetryClickListener != null) {
            mOnNoNetworkRetryClickListener = null;
        }
    }

    /**
     * 获取当前状态
     */
    public int getViewStatus() {
        return mViewStatus;
    }

    /**
     * 设置空视图图像
     */
    public void setEmptyImage(int resId) {
        if (mEmptyView != null) {
            ImageView emptyImageView = mEmptyView.findViewById(mConfig.mEmptyImageViewId);
            if (emptyImageView != null) {
                emptyImageView.setImageResource(resId);
            }
        }
    }

    /**
     * 设置错误视图图像
     */
    public void setErrorImage(int resId) {
        if (mErrorView != null) {
            ImageView errorImageView = mErrorView.findViewById(mConfig.mErrorImageViewId);
            if (errorImageView != null) {
                errorImageView.setImageResource(resId);
            }
        }
    }

    /**
     * 设置无网络视图图像
     */
    public void setNoNetworkImage(int resId) {
        if (mNoNetworkView != null) {
            ImageView noNetworkImageView = mNoNetworkView.findViewById(mConfig.mNoNetworkImageViewId);
            if (noNetworkImageView != null) {
                noNetworkImageView.setImageResource(resId);
            }
        }
    }

    /**
     * 设置空视图文本
     */
    public void setEmptyText(CharSequence text) {
        if (mEmptyView != null) {
            TextView emptyTextView = mEmptyView.findViewById(mConfig.mEmptyTextViewId);
            if (emptyTextView != null) {
                emptyTextView.setText(text);
            }
        }
    }

    /**
     * 设置错误视图文本
     */
    public void setErrorText(CharSequence text) {
        if (mErrorView != null) {
            TextView errorTextView = mErrorView.findViewById(mConfig.mErrorTextViewId);
            if (errorTextView != null) {
                errorTextView.setText(text);
            }
        }
    }

    /**
     * 设置无网络视图文本
     */
    public void setNoNetworkText(CharSequence text) {
        if (mNoNetworkView != null) {
            TextView noNetworkTextView = mNoNetworkView.findViewById(mConfig.mNoNetworkTextViewId);
            if (noNetworkTextView != null) {
                noNetworkTextView.setText(text);
            }
        }
    }

    //------------------------------------- 重试监听 -------------------------------------//

    /**
     * 设置重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    /**
     * 设置空布局重试点击事件
     */
    public void setOnEmptyRetryClickListener(OnClickListener onEmptyRetryClickListener) {
        this.mOnEmptyRetryClickListener = onEmptyRetryClickListener;
    }

    /**
     * 设置错误布局重试点击事件
     */
    public void setOnErrorRetryClickListener(OnClickListener onErrorRetryClickListener) {
        this.mOnErrorRetryClickListener = onErrorRetryClickListener;
    }

    /**
     * 设置无网络布局重试点击事件
     */
    public void setOnNoNetworkRetryClickListener(OnClickListener onNoNetworkRetryClickListener) {
        this.mOnNoNetworkRetryClickListener = onNoNetworkRetryClickListener;
    }

    //------------------------------------- 状态变化 -------------------------------------//

    /**
     * 显示内容视图
     */
    public void showContent() {
        mViewStatus = STATUS_CONTENT;
        showContentView();
    }

    /**
     * 显示加载中视图
     */
    public void showLoading() {
        showLoading(mConfig.mLoadingViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示加载中视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public void showLoading(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showLoading(mLoadingView == null ? inflateView(layoutId) : mLoadingView, layoutParams);
    }

    /**
     * 显示加载中视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public void showLoading(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Loading view is null.");
        checkNull(layoutParams, "Layout params is null.");
        mViewStatus = STATUS_LOADING;
        if (mLoadingView == null) {
            mLoadingView = view;
            mLoadingView.setId(View.generateViewId());
            mOtherIds.add(mLoadingView.getId());
            addView(mLoadingView, layoutParams);
        }
        showViewById(mLoadingView.getId());
    }

    /**
     * 显示空视图
     */
    public void showEmpty() {
        showEmpty(mConfig.mEmptyViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示空视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public void showEmpty(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showEmpty(mEmptyView == null ? inflateView(layoutId) : mEmptyView, layoutParams);
    }

    /**
     * 显示空视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public void showEmpty(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Empty view is null.");
        checkNull(layoutParams, "Layout params is null.");
        mViewStatus = STATUS_EMPTY;
        if (mEmptyView == null) {
            mEmptyView = view;
            mEmptyView.setId(generateViewId());
            View emptyRetryView = mEmptyView.findViewById(mConfig.mEmptyRetryViewId);
            if (emptyRetryView != null) {
                if (mOnEmptyRetryClickListener != null) {
                    emptyRetryView.setOnClickListener(mOnEmptyRetryClickListener);
                } else if (mOnRetryClickListener != null) {
                    emptyRetryView.setOnClickListener(mOnRetryClickListener);
                }
            }
            mOtherIds.add(mEmptyView.getId());
            addView(mEmptyView, layoutParams);
        }
        showViewById(mEmptyView.getId());
    }

    /**
     * 显示错误视图
     */
    public void showError() {
        showError(mConfig.mErrorViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示错误视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public void showError(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showError(mErrorView == null ? inflateView(layoutId) : mErrorView, layoutParams);
    }

    /**
     * 显示错误视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public void showError(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Error view is null.");
        checkNull(layoutParams, "Layout params is null.");
        mViewStatus = STATUS_ERROR;
        if (mErrorView == null) {
            mErrorView = view;
            mErrorView.setId(generateViewId());
            View errorRetryView = mErrorView.findViewById(mConfig.mErrorRetryViewId);
            if (errorRetryView != null) {
                if (mOnErrorRetryClickListener != null) {
                    errorRetryView.setOnClickListener(mOnErrorRetryClickListener);
                } else if (mOnRetryClickListener != null) {
                    errorRetryView.setOnClickListener(mOnRetryClickListener);
                }
            }
            mOtherIds.add(mErrorView.getId());
            addView(mErrorView, layoutParams);
        }
        showViewById(mErrorView.getId());
    }

    /**
     * 显示无网络视图
     */
    public void showNoNetwork() {
        showNoNetwork(mConfig.mNoNetworkViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示无网络视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public void showNoNetwork(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showNoNetwork(mNoNetworkView == null ? inflateView(layoutId) : mNoNetworkView, layoutParams);
    }

    /**
     * 显示无网络视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public void showNoNetwork(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "No network view is null.");
        checkNull(layoutParams, "Layout params is null.");
        mViewStatus = STATUS_NO_NETWORK;
        if (mNoNetworkView == null) {
            mNoNetworkView = view;
            mNoNetworkView.setId(generateViewId());
            View noNetworkRetryView = mNoNetworkView.findViewById(mConfig.mNoNetworkRetryViewId);
            if (noNetworkRetryView != null) {
                if (mOnNoNetworkRetryClickListener != null) {
                    noNetworkRetryView.setOnClickListener(mOnNoNetworkRetryClickListener);
                } else if (mOnRetryClickListener != null) {
                    noNetworkRetryView.setOnClickListener(mOnRetryClickListener);
                }
            }
            mOtherIds.add(mNoNetworkView.getId());
            addView(mNoNetworkView, layoutParams);
        }
        showViewById(mNoNetworkView.getId());
    }

    //------------------------------------- 布局配置 -------------------------------------//

    private static Config mConfig = new Config();

    /**
     * 获取全局配置
     */
    public static Config getConfig() {
        return mConfig;
    }

    /**
     * 全局配置
     */
    public static class Config {
        @LayoutRes
        int mEmptyViewResId;
        @LayoutRes
        int mErrorViewResId;
        @LayoutRes
        int mLoadingViewResId;
        @LayoutRes
        int mNoNetworkViewResId;

        @IdRes
        int mEmptyImageViewId;
        @IdRes
        int mErrorImageViewId;
        @IdRes
        int mNoNetworkImageViewId;

        @IdRes
        int mEmptyTextViewId;
        @IdRes
        int mErrorTextViewId;
        @IdRes
        int mNoNetworkTextViewId;

        @IdRes
        int mEmptyRetryViewId;
        @IdRes
        int mErrorRetryViewId;
        @IdRes
        int mNoNetworkRetryViewId;

        public Config setEmptyView(@LayoutRes int emptyViewResId) {
            this.mEmptyViewResId = emptyViewResId;
            return this;
        }

        public Config setErrorView(@LayoutRes int errorViewResId) {
            this.mErrorViewResId = errorViewResId;
            return this;
        }

        public Config setLoadingView(@LayoutRes int loadingViewResId) {
            this.mLoadingViewResId = loadingViewResId;
            return this;
        }

        public Config setNoNetworkView(@LayoutRes int noNetworkViewResId) {
            this.mNoNetworkViewResId = noNetworkViewResId;
            return this;
        }

        public Config setEmptyImageViewId(@IdRes int emptyImageViewId) {
            this.mEmptyImageViewId = emptyImageViewId;
            return this;
        }

        public Config setErrorImageViewId(@IdRes int errorImageViewId) {
            this.mErrorImageViewId = errorImageViewId;
            return this;
        }

        public Config setNoNetworkImageViewId(@IdRes int noNetworkImageViewId) {
            this.mNoNetworkImageViewId = noNetworkImageViewId;
            return this;
        }

        public Config setEmptyTextViewId(@IdRes int emptyTextViewId) {
            this.mEmptyTextViewId = emptyTextViewId;
            return this;
        }

        public Config setErrorTextViewId(@IdRes int errorTextViewId) {
            this.mErrorTextViewId = errorTextViewId;
            return this;
        }

        public Config setNoNetworkTextViewId(@IdRes int noNetworkTextViewId) {
            this.mNoNetworkTextViewId = noNetworkTextViewId;
            return this;
        }

        public Config setEmptyRetryViewId(@IdRes int emptyRetryViewId) {
            this.mEmptyRetryViewId = emptyRetryViewId;
            return this;
        }

        public Config setErrorRetryViewId(@IdRes int errorRetryViewId) {
            this.mErrorRetryViewId = errorRetryViewId;
            return this;
        }

        public Config setNoNetworkRetryViewId(@IdRes int noNetworkRetryViewId) {
            this.mNoNetworkRetryViewId = noNetworkRetryViewId;
            return this;
        }
    }

    //------------------------------------- 辅助方法 -------------------------------------//

    private void showViewById(int viewId) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
        }
    }

    private void showContentView() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(mOtherIds.contains(view.getId()) ? View.GONE : View.VISIBLE);
        }
    }

    private View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }

    private void checkNull(Object object, String hint) {
        if (object == null) {
            throw new NullPointerException(hint);
        }
    }

    private void clear(View... views) {
        if (views == null) {
            return;
        }
        try {
            for (View view : views) {
                if (view != null) {
                    removeView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}