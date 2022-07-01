package com.hefei.pay.wx;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/03/16
 *     desc  : 微信支付
 *
 *     // 微信支付请求
 *     WXPayReq wxPayReq = new WXPayReq.Builder()
 *                 .with(MainActivity.this)
 *                 .setAppId(appId)
 *                 .setPartnerId(partnerId)
 *                 .setPrepayId(prepayId)
 *                 .setPackageValue(packageValue)
 *                 .setNonceStr(nonceStr)
 *                 .setTimeStamp(timeStamp)
 *                 .setSign(sign)
 *                 .create();
 *
 *     WXPayAPI.getInstance().sendPayRequest(wxPayReq);
 *
 *     // 添加AndroidManifest.xml
 *     <activity
 *             android:name=".wxapi.WXPayEntryActivity"
 *             android:exported="true"
 *             android:launchMode="singleTop"/>
 *
 *    // 微信支付结果回调
 *    public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
 *
 *        private IWXAPI mWXApi;
 *
 *        @Override
 *        protected void onCreate(Bundle savedInstanceState) {
 *            super.onCreate(savedInstanceState);
 *
 *            mWXApi = WXAPIFactory.createWXAPI(this, WXPayAPI.APP_ID);
 *            mWXApi.handleIntent(getIntent(), this);
 *        }
 *
 *        @Override
 *        protected void onNewIntent(Intent intent) {
 *            super.onNewIntent(intent);
 *            setIntent(intent);
 *            mWXApi.handleIntent(intent, this);
 *        }
 *
 *        @Override
 *        public void onReq(BaseReq baseReq) {
 *
 *        }
 *
 *        @Override
 *        public void onResp(BaseResp baseResp) {
 *            if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
 *                switch (baseResp.errCode) {
 *                    case BaseResp.ErrCode.ERR_OK:
 *                        // 支付成功
 *                        break;
 *                    case BaseResp.ErrCode.ERR_COMM:
 *                        // 支付失败
 *                        break;
 *                    case BaseResp.ErrCode.ERR_USER_CANCEL:
 *                        // 用户取消
 *                        break;
 *                    default:
 *                        // 支付失败
 *                        break;
 *                }
 *            }
 *        }
 *    }
 *
 * </pre>
 */
public class WXPayAPI {

    public static final String APP_ID = "wxd3cb10bf5e6995e2";

    /**
     * 获取微信支付API
     */
    private static final Object mLock = new Object();
    private static WXPayAPI mInstance;

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
