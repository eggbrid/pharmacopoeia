package com.pharmacopoeia.activity.main;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.reponse.NUllValueResponse;
import com.pharmacopoeia.util.MD5Util;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;

import java.util.Map;

/**
 * Created by xus on 2017/7/17.
 */

public class ResetPasswordActivity extends CommentActivity implements View.OnClickListener

{


    private EditText password;
    private EditText rePassword;
    private Button next;
    private String mobileString;
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String passwords = password.getText().toString() + "";
            String rePasswords = rePassword.getText().toString() + "";
            if (passwords.length() <6 || rePasswords.length() < 6) {
                next.setEnabled(false);
            } else {
                next.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    };

    @Override
    public int setContentView() {
        return R.layout.main_reset_password_activity;
    }

    @Override
    public void initView() throws Exception {

        setCommentTitleView("忘记密码");
        password = (EditText) findViewById(R.id.password);
        password.addTextChangedListener(watcher);
        rePassword = (EditText) findViewById(R.id.re_password);
        rePassword.addTextChangedListener(watcher);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        mobileString=getIntent().getStringExtra("mobile");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.next:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        showPross("正在修改");

        String passwordString = password.getText().toString().trim();
        String rePasswordString = rePassword.getText().toString().trim();

        if (!passwordString.equals(rePasswordString)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = OkHttpUtil.getFromMap(this);
        map.put("mobile",mobileString);
        map.put("npassword", MD5Util.encrypt("MD5", passwordString));
        OkHttpUtil.doPost(this, UrlUtil.RESETPWD, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dissPross();
                T.show(ResetPasswordActivity.this,"修改成功");
                Intent intents = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intents);
            }

            @Override
            public void onError(String s) {
                dissPross();
                T.show(ResetPasswordActivity.this,s);

            }
        }, NUllValueResponse.class);



    }
}
