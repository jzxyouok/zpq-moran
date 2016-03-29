package com.cat14.moran;

import android.app.Application;
import android.content.Context;

/**
 * App类：管理全局状态信息
 */
public class App extends Application {

    private static Context context;

    private static String baseUrl="http://moran.chinacloudapp.cn/moran/web";

    /**
     * 在创建时获得全局Context
     */
    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    /**
     * 获得全局Context
     *
     * @return Context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获得API地址
     *
     * @param path 对应功能的地址
     * @return String
     */
    public static String getUrl(String path) {
        return baseUrl + path;
    }
}
