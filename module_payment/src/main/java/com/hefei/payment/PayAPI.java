package com.hefei.payment;

import android.app.Activity;

import com.hefei.payment.callback.IPayCallback;
import com.hefei.payment.internal.IPayInfo;
import com.hefei.payment.internal.IPayStrategy;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/7/26
 *     desc  :
 * </pre>
 */
public class PayAPI {

    private static final Object mLock = new Object();
    private static PayAPI mInstance;

    public static PayAPI getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new PayAPI();
                }
            }
        }
        return mInstance;
    }

    public void pay(Activity activity, IPayStrategy payStrategy, IPayInfo payInfo, IPayCallback payCallback) {
        payStrategy.pay(activity, payInfo, payCallback);
    }
}
