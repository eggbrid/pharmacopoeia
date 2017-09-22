package com.pharmacopoeia.activity.me;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.main.MainActivity;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.util.FileUtil;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.share.ShareUtils;

/**
 * Created by xus on 2017/7/15.
 */

public class AboutActivity extends CommentActivity implements View.OnClickListener {


    private RelativeLayout my_collection;
    private TextView cache;
    private RelativeLayout cache_layout;

    @Override
    public int setContentView() {
        return R.layout.me_about_acitivity;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("关于");
        my_collection = (RelativeLayout) findViewById(R.id.my_collection);
        my_collection.setOnClickListener(this);
        cache = (TextView) findViewById(R.id.cache);
        cache.setOnClickListener(this);
        cache_layout = (RelativeLayout) findViewById(R.id.cache_layout);
        cache_layout.setOnClickListener(this);
        cache.setText("V"+getVersion());


    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "2.0.1";
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.my_collection:
                T.show(this, "此功能暂未开通，请到应用市场检查更新");
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
                        T.show(AboutActivity.this, "已成功清理");
                    }
                });
            }
        }).start();

    }


}
