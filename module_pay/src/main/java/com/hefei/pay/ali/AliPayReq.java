package com.hefei.pay.ali;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/03/16
 *     desc  : 支付宝支付请求参数
 * </pre>
 */
public class AliPayReq {

    private static final int SDK_PAY_FLAG = 1;

    // 上下文
    private Activity activity;
    // app支付请求参数字符串
    private String orderInfo;

    private Handler mHandler;
    private OnAliPayListener mOnAliPayListener;

    public AliPayReq setOnAliPayListener(OnAliPayListener listener) {
        this.mOnAliPayListener = listener;
        return this;
    }

    /**
     * 支付宝支付监听
     */
    public interface OnAliPayListener {
        void onPaySuccess(String result);

        void onPayFailure(String result);

        void onPayConfirming(String result);

        void onPayCancel(String result);
    }

    @SuppressLint("HandlerLeak")
    public AliPayReq(Activity activity, String orderInfo) {
        this.activity = activity;
        this.orderInfo = orderInfo;

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 支付成功
                            if (mOnAliPayListener != null)
                                mOnAliPayListener.onPaySuccess(resultInfo);
                        } else {
                            if (TextUtils.equals(resultStatus, "8000")) {
                                // 正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                                if (mOnAliPayListener != null)
                                    mOnAliPayListener.onPayConfirming(resultInfo);
                            } else if (TextUtils.equals(resultStatus, "6001")) {
                                // 用户中途取消
                                if (mOnAliPayListener != null)
                                    mOnAliPayListener.onPayCancel(resultInfo);
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                if (mOnAliPayListener != null)
                                    mOnAliPayListener.onPayFailure(resultInfo);
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 支付宝支付
     */
    public void pay() {
        Runnable payRunnable = () -> {
            // 构造PayTask对象
            PayTask payTask = new PayTask(activity);
            // 调用支付接口，获取支付结果
            Map<String, String> result = payTask.payV2(orderInfo, true);

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static class Builder {
        // 上下文
        private Activity activity;
        // app支付请求参数字符串
        private String orderInfo;

        public Builder with(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setOrderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
            return this;
        }

        public AliPayReq create() {
            return new AliPayReq(this.activity, this.orderInfo);
        }
    }
}
