package com.hefei.payment.platform.alipay;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/7/26
 *     desc  :
 * </pre>
 */
public class AliPayResultStatus {

    public static final String SUCCESS = "9000";
    public static final String HANDLING = "8000";
    public static final String FAIL = "4000";
    public static final String REPEAT = "5000";
    public static final String CANCEL = "6001";
    public static final String NETWORK = "6002";
    public static final String UNKNOWN = "6004";

    public static String getMessage(String code) {
        switch (code) {
            case SUCCESS:
                return "订单支付成功";
            case HANDLING:
                return "正在处理中，支付结果未知（有可能已经支付成功），请查询商家订单列表中订单的支付状态";
            case FAIL:
                return "订单支付失败";
            case REPEAT:
                return "重复请求";
            case CANCEL:
                return "用户中途取消";
            case NETWORK:
                return "网络连接出错";
            case UNKNOWN:
                return "支付结果未知（有可能已经支付成功），请查询商家订单列表中订单的支付状态";
        }
        return "其它支付错误";
    }

}
