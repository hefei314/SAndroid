package com.hefei.payment.callback;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/7/26
 *     desc  :
 * </pre>
 */
public interface IPayCallback {

    void success(String resultInfo);

    void failed(int code, String message);

    void cancel();

}
