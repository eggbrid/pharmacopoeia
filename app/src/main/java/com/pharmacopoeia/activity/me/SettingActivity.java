package com.pharmacopoeia.activity.me;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.main.LoginActivity;
import com.pharmacopoeia.activity.main.MainActivity;
import com.pharmacopoeia.activity.main.WebActivity;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.util.FileUtil;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.share.ShareUtils;

/**
 * Created by xus on 2017/7/15.
 */

public class SettingActivity extends CommentActivity implements View.OnClickListener {


    private RelativeLayout my_collection;
    private TextView cache;
    private RelativeLayout cache_layout;
    private Button login_out;

    @Override
    public int setContentView() {
        return R.layout.me_setting_acitivity;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("设置");
        my_collection = (RelativeLayout) findViewById(R.id.my_collection);
        my_collection.setOnClickListener(this);
        cache = (TextView) findViewById(R.id.cache);
        cache.setOnClickListener(this);
        cache_layout = (RelativeLayout) findViewById(R.id.cache_layout);
        cache_layout.setOnClickListener(this);
        login_out = (Button) findViewById(R.id.login_out);
        login_out.setOnClickListener(this);
        cache.setText(FileUtil.getCacheSize());
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.my_collection:
                //分享
                ShareUtils.showShare(this, v);
                break;
            case R.id.cache_layout:
                clearCacheConfirm();
                break;
            case R.id.login_out:
                DBUtil.getInstance(this).loginOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                finish();
                break;
        }
    }


    private void clearCacheConfirm() {
        showPross("正在清除");
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileUtil.deleteCache();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cache.setText("0KB");
                        dissPross();
                        T.show(SettingActivity.this, "已成功清理");
                    }
                });
            }
        }).start();

    }


}
