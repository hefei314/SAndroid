# module_pay
```
支付模块，包含微信支付以及支付宝支付
```
更新内容

* 2021-08-11
```
1.微信支付
2.支付宝支付
```

说明

* 微信支付
```java
// 微信支付请求
WXPayReq wxPayReq = new WXPayReq.Builder()
			.with(MainActivity.this)
			.setAppId(appId)
			.setPartnerId(partnerId)
			.setPrepayId(prepayId)
			.setPackageValue(packageValue)
			.setNonceStr(nonceStr)
			.setTimeStamp(timeStamp)
			.setSign(sign)
			.create();

WXPayAPI.getInstance().sendPayRequest(wxPayReq);

// 添加AndroidManifest.xml
<activity
		android:name=".wxapi.WXPayEntryActivity"
		android:exported="true"
		android:launchMode="singleTop"/>

// 微信支付结果回调
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

   private IWXAPI mWXApi;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);

	   mWXApi = WXAPIFactory.createWXAPI(this, WXPayAPI.APP_ID);
	   mWXApi.handleIntent(getIntent(), this);
   }

   @Override
   protected void onNewIntent(Intent intent) {
	   super.onNewIntent(intent);
	   setIntent(intent);
	   mWXApi.handleIntent(intent, this);
   }

   @Override
   public void onReq(BaseReq baseReq) {

   }

   @Override
   public void onResp(BaseResp baseResp) {
	   if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
		   switch (baseResp.errCode) {
			   case BaseResp.ErrCode.ERR_OK:
				   // 支付成功
				   break;
			   case BaseResp.ErrCode.ERR_COMM:
				   // 支付失败
				   break;
			   case BaseResp.ErrCode.ERR_USER_CANCEL:
				   // 用户取消
				   break;
			   default:
				   // 支付失败
				   break;
		   }
	   }
   }
}
```

* 支付宝支付
```java
// 支付宝支付请求及支付结果回调
AliPayReq aliPayReq = new AliPayReq.Builder()
	 .with(MainActivity.this)
	 .setOrderInfo(orderInfo)
	 .create()
	 .setOnAliPayListener(new AliPayReq.OnAliPayListener() {
		 @Override
		 public void onPaySuccess(String resultInfo) {
			 // 支付成功
		 }

		 @Override
		 public void onPayFailure(String resultInfo) {
			 // 支付失败
		 }

		 @Override
		 public void onPayConfirming(String resultInfo) {
			 // 支付确认中
		 }

		 @Override
		 public void onPayCancel(String resultInfo) {
			 // 支付取消
		 }
	 });

AliPayAPI.getInstance().sendPayReq(aliPayReq);
```

