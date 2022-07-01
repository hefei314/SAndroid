package com.hefei.pay.ali;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/03/16
 *     desc  : 支付宝支付
 *
 *     // 支付宝支付请求及支付结果回调
 *     AliPayReq aliPayReq = new AliPayReq.Builder()
 *             .with(MainActivity.this)
 *             .setOrderInfo(orderInfo)
 *             .create()
 *             .setOnAliPayListener(new AliPayReq.OnAliPayListener() {
 *                 @Override
 *                 public void onPaySuccess(String resultInfo) {
 *                     // 支付成功
 *                 }
 *
 *                 @Override
 *                 public void onPayFailure(String resultInfo) {
 *                     // 支付失败
 *                 }
 *
 *                 @Override
 *                 public void onPayConfirming(String resultInfo) {
 *                     // 支付确认中
 *                 }
 *
 *                 @Override
 *                 public void onPayCancel(String resultInfo) {
 *                     // 支付取消
 *                 }
 *             });
 *
 *     AliPayAPI.getInstance().sendPayReq(aliPayReq);
 * </pre>
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