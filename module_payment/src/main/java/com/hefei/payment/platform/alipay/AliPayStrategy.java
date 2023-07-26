package com.hefei.payment.platform.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.hefei.payment.callback.IPayCallback;
import com.hefei.payment.internal.IPayInfo;
import com.hefei.payment.internal.IPayStrategy;

import java.util.Map;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/7/26
 *     desc  :
 * </pre>
 */
public class AliPayStrategy implements IPayStrategy {

    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler;
    private AliPayInfo mAliPayInfo;
    private IPayCallback mPayCallback;

    private static AliPayStrategy mInstance;

    public static AliPayStrategy getInstance() {
        if (mInstance == null) {
            synchronized (AliPayStrategy.class) {
                if (mInstance == null) {
                    mInstance = new AliPayStrategy();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void pay(Activity activity, IPayInfo payInfo, IPayCallback payCallback) {
        this.mAliPayInfo = (AliPayInfo) payInfo;
        this.mPayCallback = payCallback;

        initHandler();

        Runnable payRunnable = () -> {
            // 构造PayTask对象
            PayTask payTask = new PayTask(activity);
            // 调用支付接口，获取支付结果
            Map<String, String> result = payTask.payV2(mAliPayInfo.getOrderInfo(), true);

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void initHandler() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        AliPayResult payResult = new AliPayResult((Map<String, String>) msg.obj);
                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();
                        if (TextUtils.equals(resultStatus, AliPayResultStatus.SUCCESS)) {
                            if (mPayCallback != null) {
                                mPayCallback.success(resultInfo);
                            }
                        } else if (TextUtils.equals(resultStatus, AliPayResultStatus.CANCEL)) {
                            if (mPayCallback != null) {
                                mPayCallback.cancel();
                            }
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            if (mPayCallback != null) {
                                mPayCallback.failed(Integer.parseInt(resultStatus), AliPayResultStatus.getMessage(resultStatus));
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
}
