package com.hefei.sandroid.modules.widget.countdown;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hefei.sandroid.R;
import com.hefei.sandroid.support.entity.CountdownItem;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/07/21
 *     desc  :
 * </pre>
 */
public class CountdownAdapter extends BaseQuickAdapter<CountdownItem, BaseViewHolder> {

    private SparseArray<Long> startTimeMap = new SparseArray<>();

    private OnCountdownViewEndListener listener;

    public interface OnCountdownViewEndListener {
        void onTimeEnd(int position);
    }

    public void setOnCountdownViewEndListener(OnCountdownViewEndListener listener) {
        this.listener = listener;
    }

    public CountdownAdapter(@Nullable List<CountdownItem> data) {
        super(R.layout.item_countdown2, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, CountdownItem item) {
        helper.setText(R.id.textTitle, item.getTitle());

        CountdownView countdownView = helper.getView(R.id.countdownView);
        countdownView.setOnCountdownEndListener(cv -> {
            // 倒计时结束，设为不可见
            // cv.setVisibility(View.GONE);

            if (listener != null)
                listener.onTimeEnd(helper.getBindingAdapterPosition());
        });

        long currLeftTime = item.getLeftTime() - (System.currentTimeMillis() - startTimeMap.get(item.getId()));
        if (currLeftTime > 0) {
            refreshTime(countdownView, currLeftTime);
        } else {
            countdownView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        CountdownView countdownView = holder.getView(R.id.countdownView);
        int currPosition = holder.getBindingAdapterPosition();
        if (currPosition < getData().size() && countdownView.getVisibility() == View.VISIBLE) {
            CountdownItem item = getData().get(currPosition);
            long currLeftTime = item.getLeftTime() - (System.currentTimeMillis() - startTimeMap.get(item.getId()));

            if (currLeftTime > 0) {
                refreshTime(countdownView, currLeftTime);
            } else {
                countdownView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        CountdownView countdownView = holder.getView(R.id.countdownView);
        if (countdownView.getVisibility() == View.VISIBLE) {
            countdownView.stop();
        }
    }

    /**
     * 设置数据
     *
     * @param data 数据
     */
    public void setCountdownData(List<CountdownItem> data) {
        // set start time
        startTimeMap.clear();
        long currentTimeMillis = System.currentTimeMillis();
        for (CountdownItem bean : data) {
            startTimeMap.put(bean.getId(), currentTimeMillis);
        }

        setNewInstance(data);
    }

    /**
     * 更多数据
     *
     * @param data 数据
     */
    public void addCountdownData(List<CountdownItem> data) {
        // add new start time
        long currentTimeMillis = System.currentTimeMillis();
        for (CountdownItem bean : data) {
            startTimeMap.put(bean.getId(), currentTimeMillis);
        }

        addData(data);
    }

    /**
     * 刷新倒计时
     *
     * @param countdownView 控价
     * @param leftTime      剩余时间
     */
    public void refreshTime(CountdownView countdownView, long leftTime) {
        if (leftTime > 0) {
            countdownView.start(leftTime);
            // 启动倒计时，设为可见
            countdownView.setVisibility(View.VISIBLE);
        } else {
            countdownView.stop();
            countdownView.allShowZero();
            // 未启动倒计时，设为不可见
            countdownView.setVisibility(View.GONE);
        }
    }
}

