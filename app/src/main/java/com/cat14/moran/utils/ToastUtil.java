package com.cat14.moran.utils;

import android.content.Context;
import android.widget.Toast;

import com.cat14.moran.App;

/**
 * 弹出提示工具
 */
public class ToastUtil {

    /**
     * 只填写输出信息的
     *
     * @param message 输出信息
     */
    public static void show(String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 防止内存泄露的弹出方法1
     *
     * @param context context
     * @param message 输出信息
     */
    public static void show(Context context, String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 只填写字符串ID的
     *
     * @param stringId 字符串ID
     */
    public static void show(int stringId) {
        Toast.makeText(App.getContext(),
                App.getContext().getString(stringId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 防止内存泄露的弹出方法2
     *
     * @param context context
     * @param stringId 字符串ID
     */
    public static void show(Context context, int stringId) {
        Toast.makeText(context, App.getContext().
                getString(stringId), Toast.LENGTH_SHORT).show();
    }
}
