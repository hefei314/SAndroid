package com.hefei.payment.platform.wechatpay;

import com.hefei.payment.internal.IPayInfo;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/7/26
 *     desc  :
 * </pre>
 */
public class WechatPayInfo implements IPayInfo {

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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
