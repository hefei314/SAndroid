package com.hefei.payment.platform.wechatpay;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/7/26
 *     desc  :
 * </pre>
 */
public class WechatPayResultStatus {

    public static final int UN_SUPPORT = 1001;

    public static String getMessage(int code) {
        switch (code) {
            case UN_SUPPORT:
                return "未安装微信或微信版本过低";
        }
        return "其它支付错误。";
    }

}
