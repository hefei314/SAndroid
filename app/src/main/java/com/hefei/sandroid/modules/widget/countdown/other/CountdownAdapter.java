package com.hefei.sandroid.modules.widget.countdown.other;

import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hefei.sandroid.R;
import com.hefei.sandroid.support.entity.CountdownItem;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2018/12/24
 *     desc  : 计时器==>CountDownTimer实现
 *             缺陷：调整手机时间，倒计时也会被改变，原因是计算当前剩余时间，需要去掉（当前时间戳与进入页面时间戳的时间差）
 *             可新增：两种类型，1 剩余时间秒数（毫秒数） 2 截止时间（YY-MM-DD HH:MM:SS）
 * </pre>
 */
public class CountdownAdapter extends BaseQuickAdapter<CountdownItem, CountdownAdapter.ViewHolder> {

    private SparseArray<Long> startTimeMap = new SparseArray<>();
    private SparseArray<CountDownTimer> countDownMap = new SparseArray<>();

    private OnCountdownViewEndListener listener;

    public interface OnCountdownViewEndListener {
        void onTimeEnd(int position);
    }

    public void setOnCountdownViewEndListener(OnCountdownViewEndListener listener) {
        this.listener = listener;
    }

    public CountdownAdapter(@Nullable List<CountdownItem> data) {
        super(R.layout.item_countdown, data);
    }

    @Override
    protected void convert(final ViewHolder helper, CountdownItem item) {
        helper.setText(R.id.textTitle, item.getTitle());

        final TextView textLeftTime = helper.getView(R.id.textLeftTime);

        if (helper.countDownTimer != null) {
            helper.countDownTimer.cancel();
        }

        long currLeftTime = item.getLeftTime() - (System.currentTimeMillis() - startTimeMap.get(item.getId()));

        Logger.e("======title: " + item.getTitle() + "======leftTime: " + item.getLeftTime() + "======currLeftTime: " + currLeftTime);

        if (currLeftTime > 0) {
            helper.countDownTimer = new CountDownTimer(currLeftTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    textLeftTime.setText(millis2Time(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    textLeftTime.setText("倒计时已结束");

                    if (listener != null)
                        listener.onTimeEnd(helper.getAdapterPosition());
                }
            }.start();

            countDownMap.put(textLeftTime.hashCode(), helper.countDownTimer);
        } else {
            textLeftTime.setText("倒计时已结束");
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

        setNewData(data);
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
     * 计时器销毁
     */
    public void cancelAllTimers() {
        if (countDownMap != null) {
            for (int i = 0; i < countDownMap.size(); i++) {
                CountDownTimer countDownTimer = countDownMap.get(countDownMap.keyAt(i));
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
            }
        }
    }

    /**
     * 毫秒转换
     *
     * @param millis 毫秒数
     * @return
     */
    public static String millis2Time(long millis) {
        long days, hours, minutes, seconds;

        days = millis / 86400000;
        hours = (millis % 86400000) / 3600000;
        minutes = ((millis % 86400000) % 3600000) / 60000;
        seconds = (((millis % 86400000) % 3600000) % 60000) / 1000;

        if (days > 0) {
            return days + "天" + hours + "时" + minutes + "分" + seconds + "秒";
        } else if (hours > 0) {
            return hours + "时" + minutes + "分" + seconds + "秒";
        } else {
            return minutes + "分" + seconds + "秒";
        }
    }

    class ViewHolder extends BaseViewHolder {
        CountDownTimer countDownTimer;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
