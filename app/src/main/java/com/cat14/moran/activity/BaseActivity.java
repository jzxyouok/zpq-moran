package com.cat14.moran.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.cat14.moran.view.CustomDialog;

/**
 * 基础活动类
 */
public abstract class BaseActivity extends Activity {

    private CustomDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        builder = new CustomDialog.Builder(this);
    }

    /**
     * 获得活动布局ID
     *
     * @return ID号
     */
    protected abstract int getLayoutId();

    /**
     * 获得活动名
     *
     * @return 活动名
     */
    protected abstract String getName();


    /**
     * 界面跳转方法
     *
     * @param context       当前页面
     * @param contextClass  要跳转页面的类
     */
    public void showActivity(Context context, Class<? extends Context> contextClass) {
        Intent intent = new Intent(context, contextClass);
        startActivity(intent);
    }

    /**
     * 只显示信息的提示框
     *
     * @param message   内容
     */
    public void showMessageDialog(String message) {
        builder.setMessage(message);
        builder.create().show();
    }

    /**
     * 只显示标题信息的提示框
     *
     * @param title     标题
     * @param message   内容
     */
    public void showMessageDialog(String title, String message) {
        builder.setTitle(title);
        builder.setMessage(message);
        builder.create().show();
    }

    /**
     * 显示一个按钮的提示框
     *
     * @param title     标题
     * @param message   内容
     * @param text      按钮上的文本
     * @param listener  链接按钮的监听
     */
    public void showMessageDialog(String title, String message, String
            text, DialogInterface.OnClickListener listener) {
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(text, listener);
        builder.create().show();
    }

    /**
     * 显示两个按钮的监听
     *
     * @param title     标题
     * @param message   内容
     * @param text      按钮上的文本1
     * @param listener  链接按钮的监听1
     * @param text2     按钮上的文本2
     * @param listener2 链接按钮的监听2
     */
    public void showMessageDialog(String title, String message, String text, DialogInterface.OnClickListener
            listener, String text2, DialogInterface.OnClickListener listener2) {
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(text, listener);
        builder.setNegativeButton(text2, listener2);
        builder.create().show();
    }

}
