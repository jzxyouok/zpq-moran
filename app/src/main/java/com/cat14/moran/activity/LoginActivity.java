package com.cat14.moran.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cat14.moran.R;
import com.cat14.moran.utils.ToastUtil;

/**
 * 登陆应用界面
 */
public class LoginActivity extends BaseActivity {

    private EditText mEmail;                    // 邮箱输入
    private EditText mPassword;                 // 密码输入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
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
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_pattern_email));
            focusView = mEmail;
            isValid = false;
        }

        // 密码验证
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_empty_password));
            focusView = mPassword;
            isValid = false;
        } else if (!isPasswordVaild(password)) {
            mPassword.setError(getString(R.string.error_pattern_password));
            focusView = mPassword;
            isValid = false;
        }

        // 根据焦点
        if (!isValid) {
            focusView.requestFocus();
        } else if (email.equals("test@moran.com") && password.equals("12345")) {
            ToastUtil.show(R.string.success_signin);
        } else {
            ToastUtil.show(R.string.error_invalid);
        }

    }

    /**
     * 判断邮箱是否正确
     *
     * @param email 邮箱
     * @return 判断结果
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * 判断密码格式是否正确
     *
     * @param password 密码
     * @return 判断结果
     */
    private boolean isPasswordVaild(String password) {
        return password.length() >= 6;
    }

    /**
     * 注册按钮
     */
    public void register(View view) {

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
