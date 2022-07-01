package com.hefei.sandroid.support.utils;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.DebouncingUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hefei.sandroid.support.entity.ViewRoute;
import com.hefei.sandroid.support.global.DataConstants;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/04/24
 *     desc  :
 * </pre>
 */
public class JumpUtils {

    public static void startActivity(Context context, ViewRoute router) {
        startActivity(context, router, null);
    }

    public static void startActivity(Context context, ViewRoute viewRoute, String data) {
        if (viewRoute == null)
            return;
        try {
            Intent intent = new Intent(context, Class.forName(viewRoute.getTarget()));
            intent.putExtra(DataConstants.ARG_TITLE, viewRoute.getTitle());
            intent.putExtra(DataConstants.ARG_DATA, data);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            ToastUtils.showLong("Class not found! please config the router.");
        }
    }
}
