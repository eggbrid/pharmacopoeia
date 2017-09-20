package com.pharmacopoeia.activity.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.reponse.NUllValueResponse;
import com.pharmacopoeia.bean.reponse.UserInfoResponse;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xus on 2017/7/17.
 */

public class ForgetPasswordActivity extends CommentActivity implements View.OnClickListener

{


    private ImageButton left;
    private TextView title_text;
    private ImageButton right;
    private LinearLayout title;
    private EditText mobile;
    private ImageView icon_1;
    private EditText verification;
    private RelativeLayout verification_layout;
    private TextView verification_btn;
    private Button next;
    private long count = 60;
    private TimerTask timerTask;
    private Timer timer;
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String mobiles = mobile.getText().toString() + "";
            String passwords = verification.getText().toString() + "";
            if (mobiles.length() == 11) {
                verification_btn.setEnabled(true);
            } else {
                verification_btn.setEnabled(false);
            }
            if (mobiles.length() < 11 || verification.length() < 4) {
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
        return R.layout.main_forget_password_activity;
    }

    @Override
    public void initView() throws Exception {

        setCommentTitleView("忘记密码");
        mobile = (EditText) findViewById(R.id.mobile);
        mobile.addTextChangedListener(watcher);
        verification = (EditText) findViewById(R.id.verification);
        verification.addTextChangedListener(watcher);
        verification_btn = (TextView) findViewById(R.id.verification_btn);
        verification_btn.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        long i = new Date().getTime() / 1000 - DBUtil.getInstance(this).getSetting().getVerificationTime() / 1000;
        if (i > 60) {
            verification_btn.setText("获取验证码");
        } else {
            count = (60 - i);
            setTimes();
        }
    }

    public void setTimes() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                ForgetPasswordActivity.this.runOnUiThread(run);
            }
        };
        timer.schedule(timerTask, 0, 1000);
        getVerification();

    }

    private Runnable run = new Runnable() {

        @Override
        public void run() {
            if (count > 0) {
                verification_btn.setText("" + count + "s");
                verification_btn.setEnabled(false);
            } else {
                timerTask.cancel();
                timer.cancel();
                timerTask = null;
                timer = null;
                verification_btn.setEnabled(true);
                verification_btn.setText("获取验证码");
            }
            count--;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.verification_btn:
                setTimes();
                break;

            case R.id.next:
                submit();
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
                T.show(ForgetPasswordActivity.this,"验证码已发送到"+mobiles+"上");

            }

            @Override
            public void onError(String s) {
                dissPross();
                T.show(ForgetPasswordActivity.this,s);
            }
        },NUllValueResponse.class);
    }

    private void submit() {
        // validate

        final String mobileString = mobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobileString)) {
            Toast.makeText(this, "请输入您已验证的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String verificationString = verification.getText().toString().trim();
        if (TextUtils.isEmpty(verificationString)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        showPross("正在验证");

        // TODO validate success, do something
        Map<String, String> map = OkHttpUtil.getFromMap(this);
        map.put("mobile", mobileString);
        map.put("code", verificationString);

        OkHttpUtil.doPost(this, UrlUtil.CHECKMOBILE, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dissPross();

                Bundle bundle=new Bundle();
                bundle.putString("mobile",mobileString);
                IntentUtils.openActivity(ForgetPasswordActivity.this, ResetPasswordActivity.class,bundle);
            }

            @Override
            public void onError(String s) {
                dissPross();
                T.show(ForgetPasswordActivity.this,s);

            }
        }, NUllValueResponse.class);

    }

}
