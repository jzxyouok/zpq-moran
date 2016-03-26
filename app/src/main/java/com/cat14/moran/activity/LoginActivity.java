package com.cat14.moran.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cat14.moran.R;

/**
 * 登陆应用界面
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void login(View view) {

        showMessageDialog("标题2222222222", "s的撒范德萨范德萨范德萨房贷首付士大夫的范德萨范德萨范德萨范德萨" +
                "萨菲撒反对士大夫但是", "确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LoginActivity.this, "ss1", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


    public void register(View view) {

        showMessageDialog("标题11111111", "s的撒范德萨范德萨范德萨房贷首付士大夫的范德萨范德萨范德萨范德萨" +
                "萨菲撒反对士大夫但是", "确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LoginActivity.this, "ss2", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LoginActivity.this, "取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
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
