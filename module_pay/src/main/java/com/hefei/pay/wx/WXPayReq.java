package com.hefei.pay.wx;

import android.app.Activity;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/03/16
 *     desc  : 微信支付请求参数
 * </pre>
 */
public class WXPayReq {

    // 上下文
    private Activity activity;
    // 应用ID
    private String appId;
    // 商户号
    private String partnerId;
    // 预支付交易会话ID
    private String prepayId;
    // 扩展字段 暂填写固定值Sign=WXPay
    private String packageValue;
    // 随机字符串，不长于32位
    private String nonceStr;
    // 时间戳
    private String timeStamp;
    // 签名
    private String sign;

    private IWXAPI mWXApi;

    public WXPayReq(Activity activity, String appId, String partnerId, String prepayId,
                    String packageValue, String nonceStr, String timeStamp, String sign) {
        this.activity = activity;
        this.appId = appId;
        this.partnerId = partnerId;
        this.prepayId = prepayId;
        this.packageValue = packageValue;
        this.nonceStr = nonceStr;
        this.timeStamp = timeStamp;
        this.sign = sign;
    }

    /**
     * 微信支付
     */
    public void pay() {
        mWXApi = WXAPIFactory.createWXAPI(activity, appId);

        if (!check()) {
            Toast.makeText(this.activity, "未安装微信或微信版本过低", Toast.LENGTH_LONG).show();
            return;
        }

        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.packageValue = packageValue != null ? packageValue : "Sign=WXPay";
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.sign = sign;
        mWXApi.sendReq(req);
    }

    /**
     * 检测是否支持微信支付
     */
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    public static class Builder {
        // 上下文
        private Activity activity;
        // 应用ID
        private String appId;
        // 商户号
        private String partnerId;
        // 预支付交易会话ID
        private String prepayId;
        // 扩展字段 暂填写固定值Sign=WXPay
        private String packageValue;
        // 随机字符串，不长于32位
        private String nonceStr;
        // 时间戳
        private String timeStamp;
        // 签名
        private String sign;

        public Builder with(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setPartnerId(String partnerId) {
            this.partnerId = partnerId;
            return this;
        }

        public Builder setPrepayId(String prepayId) {
            this.prepayId = prepayId;
            return this;
        }

        public Builder setPackageValue(String packageValue) {
            this.packageValue = packageValue;
            return this;
        }

        public Builder setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public Builder setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder setSign(String sign) {
            this.sign = sign;
            return this;
        }

        public WXPayReq create() {
            return new WXPayReq(
                    this.activity,
                    this.appId,
                    this.partnerId,
                    this.prepayId,
                    this.packageValue,
                    this.nonceStr,
                    this.timeStamp,
                    this.sign);
        }
    }
}
