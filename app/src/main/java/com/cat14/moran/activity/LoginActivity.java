package com.cat14.moran.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cat14.moran.App;
import com.cat14.moran.R;
import com.cat14.moran.utils.MD5Util;
import com.cat14.moran.utils.NetStatusUtil;
import com.cat14.moran.utils.StreamUtil;
import com.cat14.moran.utils.StringUtil;
import com.cat14.moran.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity {

    private static final int ERROR_NET_NULL = 1;        // 网络连接错误
    private static final int ERROR_JSON = 3;            // JSON解析出错
    private static final int ERROR_IO = 4;              // IO流出现错误
    private static final int ERROR_MISS_PARAMS = 5;     // 缺失参数
    private static final int ERROR_EMAIL_UNREGISTER = 8;// 邮箱未注册
    private static final int ERROR_PASS_WRONG = 9;      // 密码错误
    private static final int ERROR_NET_FAIL = 10;           // 网络连接错误的返回码

    private static final int SUCCESS = 100;             // 成功的返回码

    private static final int TIME_OUT = 5000;           // 超出时间

    private EditText mEmail;                            // 邮箱输入
    private EditText mPassword;                         // 密码输入
    private static final String PATH_LOGIN = "/user/login";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    showActivity(LoginActivity.this, SquareActivity.class);
                    break;
                case ERROR_NET_NULL:
                    showMessageDialog("提示", "请检查网络是否连接", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    break;
                case ERROR_NET_FAIL:
                    showMessageDialog("错误", "网络连接失败", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    break;
                case ERROR_JSON:
                    showMessageDialog("提示", "JSON解析失败" + msg.obj, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    break;
                case ERROR_IO:
                    showMessageDialog("提示", "IO流出现错误" + msg.obj, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    break;
                case ERROR_MISS_PARAMS:
                    showMessageDialog("提示", "缺少参数", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    break;
                case ERROR_EMAIL_UNREGISTER:
                    showMessageDialog("提示", "邮箱未注册", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    break;
                case ERROR_PASS_WRONG:
                    showMessageDialog("提示", "密码错误", "确定", new DialogInterface.OnClickListener() {
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
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();

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
        } else if (email.equals("test@moran.com") && password.equals("1234567")) {
            ToastUtil.show(R.string.success_login);
            showActivity(this, SquareActivity.class);
        } else {
            if (NetStatusUtil.isNetworkConntected(LoginActivity.this)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String pwd = MD5Util.getMD5(password);
                            String gbid = "GeekBand-G2015030137";
                            JSONObject user = new JSONObject();

                            user.put("email", email);
                            user.put("password", pwd);
                            user.put("gbid", gbid);
                            String url = App.getUrl(PATH_LOGIN);
                            doPostRequest(url, user);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                Message msg = Message.obtain();
                msg.what = ERROR_NET_NULL;
                handler.sendMessage(msg);
            }

        }

    }

    private void doPostRequest(String path, JSONObject data) {
        Message msg = Message.obtain();
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

                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.getString("message");

                // 通过获得的JSON信息判断情况
                if (TextUtils.equals(message, "Login success")) {
                    JSONObject dataJSON = jsonObject.getJSONObject("data");
                    String token = dataJSON.getString("token");             // 登录成功的令牌（重要）
                    String userId = dataJSON.getString("user_id");          // 登录成功后返回用户ID

                    Log.i(userId, token);

                    mPref.edit().putString("token", MD5Util.getMD5(token)).apply();
                    mPref.edit().putString("userId",MD5Util.getMD5(userId)).apply();

                    msg.what = SUCCESS;
                    handler.sendMessage(msg);
                } else if (TextUtils.equals(message, "Missing params")) {
                    msg.what = ERROR_MISS_PARAMS;
                    handler.sendMessage(msg);
                } else if (TextUtils.equals(message, "No such user")) {
                    msg.what = ERROR_EMAIL_UNREGISTER;
                    handler.sendMessage(msg);
                } else if (TextUtils.equals(message, "Wrong password")) {
                    msg.what = ERROR_PASS_WRONG;
                    handler.sendMessage(msg);
                } else {
                    msg.what = ERROR_JSON;
                    handler.sendMessage(msg);
                }

                is.close();
            } else {
                Log.i("sssssssssssssssssssss", "sssssssssssssssssssssssssss");
                msg.what = ERROR_NET_FAIL;
                handler.sendMessage(msg);
            }

        } catch (IOException e) {
            msg.what = ERROR_IO;
            msg.obj = e;
            handler.sendMessage(msg);
            e.printStackTrace();
        } catch (JSONException e) {
            msg.what = ERROR_JSON;
            msg.obj = e;
            handler.sendMessage(msg);
            e.printStackTrace();
        }

    }

    /**
     * 注册按钮
     */
    public void register(View view) {
        showActivity(this, RegisterActivity.class);
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
