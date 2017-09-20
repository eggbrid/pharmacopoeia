package com.pharmacopoeia.activity.me;

import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.main.LoginActivity;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.reponse.NUllValueResponse;
import com.pharmacopoeia.bean.reponse.UserInfoResponse;
import com.pharmacopoeia.util.AreaUtil;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.dialog.CityDialog;

import java.util.Map;

/**
 * Created by xus on 2017/7/15.
 */

public class UpdateInfoActivity extends CommentActivity implements View.OnClickListener {


    private EditText nick;
    private TextView city;
    private Button save;
    private RelativeLayout city_layout;
    private String code;

    @Override
    public int setContentView() {
        return R.layout.me_update_info_acitivity;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("修改个人信息");
        nick = (EditText) findViewById(R.id.nick);
        city = (TextView) findViewById(R.id.city);
        save = (Button) findViewById(R.id.save);
        city_layout = (RelativeLayout) findViewById(R.id.city_layout);
        city_layout.setOnClickListener(this);
        save.setOnClickListener(this);
        AreaUtil.getInstance(this);
        User user = APP.getUser(this);
        nick.setText(user.getName());
        city.setText(user.getRegionName());
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.save:
                updateMeInfo();
                break;
            case R.id.city_layout:
                DisplayMetrics dm = new DisplayMetrics();
                this.getWindowManager().getDefaultDisplay().getMetrics(dm);
                int width = dm.widthPixels;
                int height = dm.heightPixels;
                CityDialog moneyDialog = new CityDialog(this, width, height, "");
                Window window = moneyDialog.getWindow();
                window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
                window.setWindowAnimations(R.style.dialogstyle); // 添加动画
                moneyDialog.setCancelable(true);
                moneyDialog.show();
                moneyDialog.setOnCitySelectListener(new CityDialog.OnCitySelectListener() {
                    @Override
                    public void onSelect(String name, String code) {
                        UpdateInfoActivity.this.code = code;
                        city.setText(name.trim());
                    }
                });
                break;

        }
    }


    public void updateMeInfo() {
        final String nicks = nick.getText().toString();
        if (TextUtils.isEmpty(nicks)) {
            T.show(this, "请填写昵称");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            T.show(this, "请选择城市");
            return;
        }
        showPross("正在保存");
        Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
        map.put("regionCode", code);
        map.put("nickName", nicks);
        OkHttpUtil.doPost(this, UrlUtil.UPDATEUSERINFO, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dissPross();
                User user = APP.getUser(UpdateInfoActivity.this);
                user.setRegionCode(code);
                user.setRegionName(city.getText().toString());
                user.setName(nicks);
                DBUtil.getInstance(UpdateInfoActivity.this).setUser(user);
                T.show(UpdateInfoActivity.this, "修改成功");
                UpdateInfoActivity.this.finish();
            }

            @Override
            public void onError(String s) {
                T.show(UpdateInfoActivity.this, s);

                dissPross();
            }
        }, NUllValueResponse.class);
    }
}
