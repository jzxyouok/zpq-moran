package com.cat14.moran.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cat14.moran.App;
import com.cat14.moran.R;
import com.cat14.moran.utils.MD5Util;
import com.cat14.moran.utils.NetStatusUtil;
import com.cat14.moran.utils.StreamUtil;
import com.cat14.moran.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity {

    private static final int ERROR = 0;             // 失败的返回码
    private static final int SUCCESS = 1;           // 成功的返回码

    private static final int TIME_OUT = 5000;       // 超出时间
    private static final String PATH_REGISTER = "/user/register";

    private EditText mNickName;                     // 昵称输入框
    private EditText mEmail;                        // 邮箱输入框
    private EditText mPassword;                     // 密码输入框
    private EditText mConfirmPassword;              // 密码确认输入框
    private SharedPreferences mPref;


    /**
     * 处理返回结果
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    showMessageDialog(msg.obj.toString());
                    break;
                case ERROR:
                    showMessageDialog("提示", "昵称或邮箱已被使用", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    break;
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        mPref = getSharedPreferences("moran", MODE_PRIVATE);

    }

    /**
     * 初始化界面
     */
    private void initView() {
        mNickName = (EditText) findViewById(R.id.edit_login_nickname);
        mEmail = (EditText) findViewById(R.id.edit_login_email);
        mPassword = (EditText) findViewById(R.id.edit_login_password);
        mConfirmPassword = (EditText) findViewById(R.id.edit_login_repeat_password);
    }

    /**
     * 注册新账号
     */
    public void register(View view) {
        // 重置错误信息
        mNickName.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);
        mConfirmPassword.setError(null);

        // 获取登录信息
        final String nickname = mNickName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        // 初始化获取焦点的视图
        boolean isValid = true;
        View focusView = null;

        // 昵称验证
        if (TextUtils.isEmpty(nickname)) {
            mNickName.setError(getString(R.string.error_empty_name));
            focusView = mNickName;
            isValid = false;
        }

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
        } else if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPassword.setError(getString(R.string.error_confirm_password));
            focusView = mConfirmPassword;
            isValid = false;
        } else if (!StringUtil.isPasswordVaild(confirmPassword)) {
            mPassword.setError(getString(R.string.error_pattern_password));
            focusView = mPassword;
            isValid = false;
        } else if (!TextUtils.equals(password, confirmPassword)) {
            mPassword.setError(getString(R.string.error_difference_password));
            mConfirmPassword.setError(getString(R.string.error_difference_password));
            focusView = mPassword;
            isValid = false;
        }

        // 根据焦点判断是否进行下一步
        if (!isValid) {
            focusView.requestFocus();
        } else {
            if (NetStatusUtil.isNetworkConntected(this)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String pwd = MD5Util.getMD5(password).substring(0, 20);
                            String gbid = "GeekBand-G2015030137";
                            JSONObject user = new JSONObject();

                            user.put("username", nickname);
                            user.put("password", pwd);
                            user.put("email", email);
                            user.put("gbid", gbid);
                            String url = App.getUrl("/user/register");
                            doPostRequest(url, user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            } else {
                showMessageDialog("提示", getString(R.string.error_network_none),
                        "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        }

    }

    /**
     * POST 传输
     *
     * @param path 目标地址
     * @param data 传输数据
     */
    private void doPostRequest(String path, JSONObject data) {
        try {
            // 通过实体类进行传输
            byte[] entity = data.toString().getBytes("UTF-8");
            URL url = new URL(path);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIME_OUT);
            conn.setReadTimeout(TIME_OUT);
            conn.setUseCaches(false);                                   // 设置不允许使用缓存
            conn.setDoOutput(true);                                     // 允许对外输出
            conn.setDoInput(true);                                      // 允许对内输出
            conn.setRequestProperty("Content-Type", "application/json");// 提交格式JSON
            conn.setRequestProperty("Content-Length", String.valueOf(entity.length));

            OutputStream os = conn.getOutputStream();
            os.write(entity);
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                byte[] content = StreamUtil.readInputStream(is);
                String json = new String(content);

                Message msg = Message.obtain();
                msg.obj = json;
                msg.what = SUCCESS;
                handler.sendMessage(msg);
            } else {
                Message msg = Message.obtain();
                msg.what = ERROR;
                handler.sendMessage(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 返回登录界面
     */
    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected String getName() {
        return "register";
    }
}
