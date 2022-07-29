package com.hefei.pay.wx;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/03/16
 *     desc  : 微信支付
 */
public class WXPayAPI {

    private static final Object mLock = new Object();
    private static WXPayAPI mInstance;

    /**
     * 获取微信支付API
     */
    public static WXPayAPI getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new WXPayAPI();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送微信支付请求
     *
     * @param wxPayReq 请求参数
     */
    public void sendPayReq(WXPayReq wxPayReq) {
        wxPayReq.pay();
    }
}
