package com.hefei.payment.platform.wechatpay;

import android.app.Activity;

import com.hefei.payment.callback.IPayCallback;
import com.hefei.payment.internal.IPayInfo;
import com.hefei.payment.internal.IPayStrategy;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/7/26
 *     desc  :
 * </pre>
 */
public class WechatPayStrategy implements IPayStrategy {

    private IWXAPI mWXApi;
    private WechatPayInfo mWechatPayInfo;
    private IPayCallback mPayCallback;

    private static WechatPayStrategy mInstance;

    public static WechatPayStrategy getInstance() {
        if (mInstance == null) {
            synchronized (WechatPayStrategy.class) {
                if (mInstance == null) {
                    mInstance = new WechatPayStrategy();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void pay(Activity activity, IPayInfo payInfo, IPayCallback payCallback) {
        this.mWechatPayInfo = (WechatPayInfo) payInfo;
        this.mPayCallback = payCallback;

        mWXApi = WXAPIFactory.createWXAPI(activity, mWechatPayInfo.getAppId());

        if (check()) {
            PayReq request = new PayReq();
            request.appId = mWechatPayInfo.getAppId();
            request.partnerId = mWechatPayInfo.getPartnerId();
            request.prepayId = mWechatPayInfo.getPrepayId();
            request.packageValue = mWechatPayInfo.getPackageValue() != null ? mWechatPayInfo.getPackageValue() : "Sign=WXPay";
            request.nonceStr = mWechatPayInfo.getNonceStr();
            request.timeStamp = mWechatPayInfo.getTimeStamp();
            request.sign = mWechatPayInfo.getSign();
            mWXApi.sendReq(request);
        } else {
            if (mPayCallback != null) {
                mPayCallback.failed(WechatPayResultStatus.UN_SUPPORT, WechatPayResultStatus.getMessage(WechatPayResultStatus.UN_SUPPORT));
            }
        }
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    public void onResp(int errorCode, String errorMsg) {
        if (mPayCallback == null) {
            return;
        }

        if (errorCode == BaseResp.ErrCode.ERR_OK) {
            mPayCallback.success("");
        } else if (errorCode == BaseResp.ErrCode.ERR_COMM) {
            mPayCallback.failed(errorCode, errorMsg);
        } else if (errorCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            mPayCallback.cancel();
        } else {
            mPayCallback.failed(errorCode, errorMsg);
        }

        mPayCallback = null;
    }

    /**
     * 检测是否支持微信支付
     */
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
}
