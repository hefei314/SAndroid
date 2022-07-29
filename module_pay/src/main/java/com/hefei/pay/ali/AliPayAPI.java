package com.hefei.pay.ali;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/03/16
 *     desc  : 支付宝支付
 */
public class AliPayAPI {

    private static final Object mLock = new Object();
    private static AliPayAPI mInstance;

    /**
     * 获取支付宝支付API
     */
    public static AliPayAPI getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new AliPayAPI();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送支付宝支付请求
     *
     * @param aliPayReq 请求参数
     */
    public void sendPayReq(AliPayReq aliPayReq) {
        aliPayReq.pay();
    }
}