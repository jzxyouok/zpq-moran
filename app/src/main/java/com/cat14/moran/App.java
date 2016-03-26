package com.cat14.moran;

import android.app.Application;
import android.content.Context;

/**
 * App类：管理全局状态信息
 */
public class App extends Application {

    private static Context context;    

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
}
