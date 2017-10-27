package com.pharmacopoeia.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.Setting;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.reponse.NUllValueResponse;
import com.pharmacopoeia.bean.reponse.RegisterResponse;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.MD5Util;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.CircleImageView;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xus on 2017/7/15.
 */

public class RegisterActivity extends CommentActivity {
    private ImageButton left;
    private TextView titleText;
    private TextView right;
    private CircleImageView avatar;
    private EditText mobile;
    private EditText verification;
    private Button verificationBtn;
    private EditText password;
    private EditText nickkname;
    private Button register;
    private LinearLayout registerHtml;
    private TimerTask timerTask;
    private Timer timer;
    private long count = 60;
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String mobiles = mobile.getText().toString() + "";
            String passwords = password.getText().toString() + "";
            if (mobiles.length() < 11 || passwords.length() < 6 || TextUtils.isEmpty(verification.getText().toString()) || TextUtils.isEmpty(nickkname.getText().toString())) {
                register.setBackgroundResource(R.drawable.btn_can_not_click_shape);
                register.setTextColor(getResources().getColor(R.color.c_b3b3b3));
                register.setEnabled(false);
            } else {
                register.setBackgroundResource(R.drawable.btn_green_selector);
                register.setTextColor(getResources().getColor(R.color.white));
                register.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    };

    @Override
    public int setContentView() {
        return R.layout.main_register;
    }

    @Override
    public void initView() throws Exception {
        titleText = (TextView) findViewById(R.id.title_text);
        right = (TextView) findViewById(R.id.right);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        mobile = (EditText) findViewById(R.id.mobile);
        verification = (EditText) findViewById(R.id.verification);
        password = (EditText) findViewById(R.id.password);
        nickkname = (EditText) findViewById(R.id.nickkname);


        verificationBtn = (Button) findViewById(R.id.verification_btn);

        register = (Button) findViewById(R.id.register);
        registerHtml = (LinearLayout) findViewById(R.id.register_html);

        mobile.addTextChangedListener(watcher);
        verification.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        nickkname.addTextChangedListener(watcher);

        register.setOnClickListener(this);

        right.setOnClickListener(this);
        verificationBtn.setOnClickListener(this);
        registerHtml.setOnClickListener(this);
        long i = new Date().getTime() / 1000 - DBUtil.getInstance(this).getSetting().getVerificationTime() / 1000;
        if (i > 60) {
            verificationBtn.setEnabled(true);
            verificationBtn.setHint("获取验证码");
        } else {
            verificationBtn.setEnabled(false);
            count = (60 - i);
            setTimes();
        }
    }

    public void setTimes() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                RegisterActivity.this.runOnUiThread(run);
            }
        };
        timer.schedule(timerTask, 0, 1000);

    }

    private Runnable run = new Runnable() {

        @Override
        public void run() {
            if (count > 0) {
                verificationBtn.setText("" + count + "s");
                verificationBtn.setEnabled(false);
            } else {
                timerTask.cancel();
                timer.cancel();
                timerTask = null;
                timer = null;
                verificationBtn.setEnabled(true);
                verificationBtn.setText("");
                verificationBtn.setHint("获取验证码");

            }
            count--;
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:
                Intent intents = new Intent(this, LoginActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intents);
                break;

            case R.id.register:
                if (TextUtils.isEmpty(mobile.getText()) || mobile.getText().toString().length() < 11) {
                    Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(verification.getText())) {
                    Toast.makeText(this, "请填写验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().length()<6) {
                    Toast.makeText(this, "密码不能少于六位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nickkname.getText())) {
                    Toast.makeText(this, "请填写用户昵称", Toast.LENGTH_SHORT).show();
                    return;
                }

                register();
                break;

            case R.id.verification_btn:
//                注册T
                if (TextUtils.isEmpty(mobile.getText()) || mobile.getText().toString().length() < 11) {
                    Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                } else {
                    count = 60;
                    Setting setting = DBUtil.getInstance(this).getSetting();
                    setting.setVerificationTime(new Date().getTime());
                    LiteOrmDBUtil.getInstance(this).set(setting);
                    setTimes();
                }
                getVerification();
                break;
            case R.id.register_html:
                //注册协议
                Bundle bundle = new Bundle();
                bundle.putString("title", "注册协议");
                bundle.putString("url", "file:///android_asset/fwtk.html");

                IntentUtils.openActivity(this, WebActivity.class, bundle);

                break;
        }
    }

    public void getVerification() {
        showPross("正在获取验证码");
        final String mobiles = mobile.getText().toString();
        Map<String,String> map=OkHttpUtil.getFromMap(this);
        map.put("mobile",mobiles);


        OkHttpUtil.doPost(this, UrlUtil.GETSMSCODE, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dissPross();
                String s=(String)o;
                T.show(RegisterActivity.this,"验证码已发送到"+mobiles+"上");

            }

            @Override
            public void onError(String s) {
                dissPross();
                T.show(RegisterActivity.this,s);
            }
        },NUllValueResponse.class);
    }

    public void register() {
        showPross("正在注册");
        final  String mobiles = mobile.getText().toString();
        String verifications = verification.getText().toString();
       final String passwords = password.getText().toString();
        final String nickName = nickkname.getText().toString();

        Map<String,String> map=OkHttpUtil.getFromMap(this);
        map.put("mobile",mobiles);
        map.put("password", MD5Util.encrypt("MD5",passwords));
        map.put("audience",mobiles);
        map.put("code",verifications);
        map.put("recommendphone","0");
        map.put("nickName",nickName);
        OkHttpUtil.doPost(this, UrlUtil.REGISTER, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dissPross();
//                RegisterResponse s=(RegisterResponse)o;
                T.show(RegisterActivity.this,"注册成功");
//                User user=new User();
//                user.setMobile(Long.parseLong( mobiles));
//                user.setPassword(MD5Util.encrypt("MD5",passwords));
//                user.setUserCode(s.getUserCode());
//                DBUtil.getInstance(RegisterActivity.this).login(user);

                Intent intents = new Intent(RegisterActivity.this, LoginActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intents.putExtra("mobile",mobiles);
                intents.putExtra("password",passwords);
                startActivity(intents);

                finish();
            }

            @Override
            public void onError(String s) {
                dissPross();
                T.show(RegisterActivity.this,s);
            }
        },RegisterResponse.class);
    }



}
