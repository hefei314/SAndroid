package com.hefei.payment.internal;

import android.app.Activity;

import com.hefei.payment.callback.IPayCallback;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/7/26
 *     desc  :
 * </pre>
 */
public interface IPayStrategy {

    void pay(Activity activity, IPayInfo payInfo, IPayCallback payCallback);

}
