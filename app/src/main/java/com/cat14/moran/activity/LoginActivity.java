package com.cat14.moran.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cat14.moran.R;
import com.cat14.moran.utils.StringUtil;
import com.cat14.moran.utils.ToastUtil;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity {

    private EditText mEmail;                    // 邮箱输入
    private EditText mPassword;                 // 密码输入
    private SharedPreferences mPref;            //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        mPref = getSharedPreferences("moran", MODE_PRIVATE);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mEmail = (EditText) findViewById(R.id.edit_login_email);
        mPassword = (EditText) findViewById(R.id.edit_login_password);
    }

    /**
     * 登录按钮
     */
    public void login(View view) {
        // 重置错误信息
        mEmail.setError(null);
        mPassword.setError(null);

        // 获取登录数据
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        // 初始化获取焦点的视图
        boolean isValid = true;
        View focusView = null;

        // 邮箱验证
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_empty_email));
            focusView = mEmail;
            isValid = false;
        } else if (!StringUtil.isEmail(email)) {
            mEmail.setError(getString(R.string.error_pattern_email));
            focusView = mEmail;
            isValid = false;
        }

        // 密码验证
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_empty_password));
            focusView = mPassword;
            isValid = false;
        } else if (!StringUtil.isPasswordVaild(password)) {
            mPassword.setError(getString(R.string.error_pattern_password));
            focusView = mPassword;
            isValid = false;
        }

        // 根据焦点判断是否进行下一步
        if (!isValid) {
            focusView.requestFocus();
        } else if (email.equals("test@moran.com") && password.equals("12345")) {
            ToastUtil.show(R.string.success_login);
        } else {
            ToastUtil.show(R.string.error_invalid);
        }

    }

    /**
     * 注册按钮
     */
    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getName() {
        return "login";
    }
}
