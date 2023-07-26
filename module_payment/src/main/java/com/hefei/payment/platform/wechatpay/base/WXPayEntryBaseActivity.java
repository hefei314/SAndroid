package com.hefei.payment.platform.wechatpay.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hefei.payment.platform.wechatpay.WechatPayStrategy;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by Eknow on 2018/5/9.
 */

public class WXPayEntryBaseActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WechatPayStrategy.getInstance().getWXApi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WechatPayStrategy.getInstance().getWXApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            WechatPayStrategy.getInstance().onResp(resp.errCode, resp.errStr);
            finish();
        }
    }
}
