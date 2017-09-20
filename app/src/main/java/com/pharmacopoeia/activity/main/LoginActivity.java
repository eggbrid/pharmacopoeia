package com.pharmacopoeia.activity.main;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pharmacopoeia.R;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.reponse.LoginResponse;
import com.pharmacopoeia.bean.reponse.RegisterResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.MD5Util;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.CircleImageView;

import java.util.Map;

/**
 * Created by xus on 2017/7/13.
 */

public class LoginActivity extends CommentActivity {
    protected EditText mobile;
    protected EditText password;
    protected TextView forgotPassword;
    protected Button login;
    private CircleImageView avatar;
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String mobiles = mobile.getText().toString() + "";
            String passwords = password.getText().toString() + "";
            if (mobiles.length() < 11 || passwords.length() < 6) {
                login.setBackgroundResource(R.drawable.btn_can_not_click_shape);
                login.setTextColor(getResources().getColor(R.color.c_b3b3b3));
                login.setEnabled(false);
            } else {
                login.setBackgroundResource(R.drawable.btn_green_selector);
                login.setTextColor(getResources().getColor(R.color.white));
                login.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    };
    private TextWatcher mobileWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String mobiles = mobile.getText().toString() + "";
            if (mobiles.length() == 11) {
                User user = LiteOrmDBUtil.getInstance(LoginActivity.this).getQueryFirstByWhere(User.class, "mobile", mobiles);
                if (user != null) {
                    ImageLoaderUtil.getInstance().loadNomalImage(user.getAvatar(), avatar, R.drawable.default_avatar);
                    password.setText(user.getPassword());
                }
            } else {
                password.setText("");
                avatar.setImageResource(R.drawable.default_avatar);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    };

    @Override
    public int setContentView() {
        return R.layout.main_login;
    }

    @Override
    public void initView() throws Exception {
        avatar = (CircleImageView) findViewById(R.id.avatar);
        mobile = (EditText) findViewById(R.id.mobile);
        password = (EditText) findViewById(R.id.password);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(this);
        TextView right = (TextView) findViewById(R.id.right);
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(this);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        mobile.addTextChangedListener(watcher);
        mobile.addTextChangedListener(mobileWatcher);
        password.addTextChangedListener(watcher);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgot_password:
                IntentUtils.openActivity(this, ForgetPasswordActivity.class);
                break;
            case R.id.right:
//                注册
                Intent intents = new Intent(this, RegisterActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intents);
                break;
            case R.id.login:
//                登录
                User user = new User();
                user.setAvatar("http://cdn.duitang.com/uploads/item/201507/22/20150722145119_hJnyP.jpeg");
                user.setMobile(Long.parseLong(mobile.getText().toString()));
                user.setPassword(password.getText().toString());


                if (TextUtils.isEmpty(mobile.getText()) || mobile.getText().toString().length() < 11) {
                    Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().length() < 6) {
                    Toast.makeText(this, "密码不能少于六位", Toast.LENGTH_SHORT).show();
                    return;
                }
                login();

                break;


        }
    }

    public void login() {
        showPross("正在登陆");
        final String mobiles = mobile.getText().toString();
        final String passwords = password.getText().toString();
        Map<String, String> map = OkHttpUtil.getFromMap(this);
        map.put("mobile", mobiles);
        map.put("password", MD5Util.encrypt("MD5", passwords));

        OkHttpUtil.doPost(this, UrlUtil.LOGIN, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dissPross();
                LoginResponse s = (LoginResponse) o;
                User user = new User();
                user.setMobile(Long.parseLong(mobiles));
                user.setPassword(passwords);
                user.setUserCode(s.getCode());
                user.setAuthToken(s.getToken());
                user.setUserId(s.getUserId());
                DBUtil.getInstance(LoginActivity.this).login(user);
                LoginActivity.this.finish();
            }

            @Override
            public void onError(String s) {
                dissPross();
                T.show(LoginActivity.this, s);
            }
        }, LoginResponse.class);
    }

}
