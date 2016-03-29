package com.cat14.moran.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态工具类
 */
public class NetStatusUtil {

    /**
     * 判断网络能否使用
     *
     * @param context context
     * @return boolean
     */
    public static boolean isNetworkConntected(Context context) {
        if (context != null) {
            ConnectivityManager mConnManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
